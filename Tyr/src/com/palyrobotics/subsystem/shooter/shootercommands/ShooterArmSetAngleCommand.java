package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.AngleSensor;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterArmController;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

/**
 * @author Paly Robotics Programming Red Module
 * 
 * Uses PID to set the arm angle
 */
public class ShooterArmSetAngleCommand extends Command {
	private ShooterController controller;
	private InputSystems input;
	private AngleSensor armEncoder;

	private Double targetAngle;
	private Double previousError;
	
	public ShooterArmSetAngleCommand(ShooterController controller, double angle) {
		super(controller.armController);
		controller.armController.setState(ShooterArmController.ShooterArmState.SETANGLE);
		this.targetAngle = angle;
	}

	@Override
	public void initialize() {
		this.armEncoder = input.getArmEncoder();
		this.previousError = 0.0;
	}
	
	@Override
	public boolean execute() {
		double angle = armEncoder.getAngle();
		double error = targetAngle - angle;
		double derivative = (error - previousError) / RobotConstants.UPDATES_PER_SECOND;

		if(Math.abs(error) < ShooterConstants.ARM_P_ERROR && Math.abs(derivative) < ShooterConstants.ARM_D_ERROR) {
			return true;
		} 
		else {
			controller.systems.getMotor().setSpeed(ShooterConstants.ARM_kP * error + ShooterConstants.ARM_kD * derivative);
			previousError = error;
			return false;
		}
	}
	
	@Override
	public void interrupted() { 
		System.out.println("SetArmAngleCommand interuppted");
	}
	
	public void end() {
		controller.armController.state = ShooterArmController.ShooterArmState.IDLE;
	}
}
