package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;

import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.subsystem.drivetrain.DrivetrainConstants;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class GenericPIDDrive extends Command {

	private double targetDistance = 0.0;
	private double maxOutput = 1.0;
	private double previousLeftError = 0;
	private double previousRightError = 0;
	private DrivetrainController drivetrain;
	
	/**
	 * @param drivetrain
	 * @param targetDistance 
	 * @param maxOutput fast we can go 
	 */
	public GenericPIDDrive(DrivetrainController drivetrain, double targetDistance, double maxOutput) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);
		
		// Zero the Encoders
		this.targetDistance = targetDistance;
		this.maxOutput = maxOutput;
	}
	
	@Override
	public void initialize() {
		drivetrain.getInput().getLeftDriveEncoder().zero();
		drivetrain.getInput().getRightDriveEncoder().zero();
	}
	
	@Override
	public boolean execute() {
		// Calculate the Errors
		double leftError = targetDistance - drivetrain.getInput().getLeftDriveEncoder().getAngle();
		double rightError = targetDistance - drivetrain.getInput().getRightDriveEncoder().getAngle();
		
		// Calculate the Derivatives
		double leftD = (leftError - previousLeftError) * RobotConstants.UPDATES_PER_SECOND;
		double rightD = (rightError - previousRightError) * RobotConstants.UPDATES_PER_SECOND;
		
		// Calculate and Limit the speed
		double leftSpeed = Math.min(Math.max(leftError * DrivetrainConstants.LEFT_DRIVE_P + leftD * DrivetrainConstants.LEFT_DRIVE_D, -maxOutput), maxOutput);
		double rightSpeed = Math.min(Math.max(rightError * DrivetrainConstants.RIGHT_DRIVE_P + rightD * DrivetrainConstants.RIGHT_DRIVE_D, -maxOutput), maxOutput);
		
		// Set the Previous Errors for Derivative
		this.previousLeftError = leftError;
		this.previousRightError = rightError;
		
		// Check if we're going slowly
		if (Math.abs(leftError) < DrivetrainConstants.ACCEPTABLE_DISTANCE_ERROR && Math.abs(rightError) < DrivetrainConstants.ACCEPTABLE_DISTANCE_ERROR) {
			// Check if we've stopped
			if (Math.abs(leftD) == 0 && Math.abs(rightD) == 0) {
				System.out.println("Exit 2");

				return true;				
			}
		}		
		
		// Set the Speed
		drivetrain.getOutput().getLeftMotor().setSpeed(leftSpeed);
		drivetrain.getOutput().getRightMotor().setSpeed(-rightSpeed);
		
		return false;
	}
	
	@Override
	public void end() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}
}
