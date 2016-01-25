package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.AngleSensor;

import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.robot.RobotController;
import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;


public class UncheckedSetArmAngleCommand extends Command {
	private ShooterController controller;
	private AngleSensor armEncoder;

	private Double targetAngle;
	private Double previousError;
	
	
	public UncheckedSetArmAngleCommand(ShooterController controller, double angle) {
		controller.state = ShooterController.ShooterState.UNLOCKED;
		this.targetAngle = angle;
	}

	@Override
	public void initialize() {
		this.armEncoder = controller.systems.getArmEncoder();
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
			controller.systems.getMotor().setSpeed(ShooterConstants.ARM_kD * error + ShooterConstants.ARM_kP * derivative);
			previousError = error;
			return false;
		}
	}
	
	@Override
	public void interrupted() { 
		System.out.println("SetArmAngleCommand interuppted");
	}
	

}
