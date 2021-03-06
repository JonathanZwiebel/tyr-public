package com.palyrobotics.subsystem.shooter.subcommands;

import org.strongback.command.Command;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;

public class ShooterArmHoldCommand extends Command {
	private ShooterController controller;
	private InputSystems input;
	private double previousError;
	private double previousAngle;
	
	public ShooterArmHoldCommand(ShooterController controller) {
		super(controller.armController);
		this.controller = controller;
		this.input = controller.input;
	}

	public void init() {
		this.previousAngle = input.getBreacherPotentiometer().getAngle();
		this.previousError = 0;
	}
	
	@Override
	public boolean execute() {
		double error  = input.getShooterArmPotentiometer().getAngle() - previousAngle;
		double derivative = (error - previousError) / RobotConstants.UPDATES_PER_SECOND;
		
		double rawSpeed = ShooterConstants.ARM_kP * error + ShooterConstants.ARM_kD * derivative;
		double limitedSpeed = Math.min(Math.max(rawSpeed, -ShooterConstants.SHOOTER_ARM_SET_ANGLE_SPEED_LIMIT), ShooterConstants.SHOOTER_ARM_SET_ANGLE_SPEED_LIMIT);
		
		controller.systems.getArmMotor().setSpeed(limitedSpeed);
		this.previousError = error;
	
		return false;
	}
}