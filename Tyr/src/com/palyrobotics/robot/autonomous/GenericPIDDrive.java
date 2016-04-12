package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;

import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.subsystem.drivetrain.DrivetrainConstants;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class GenericPIDDrive extends Command {

	private double maxTime = Integer.MAX_VALUE;
	private double maxDistance = Integer.MAX_VALUE;
	private double targetDistance = Integer.MAX_VALUE;
	private double maxOutput = 1.0;
	private double previousLeftError = 0;
	private double previousRightError = 0;
	
	private DrivetrainController drivetrain;
	
	/**
	 * 
	 * @param drivetraincontroller
	 * Construtor For Drivetrain if you use the setters
	 */
	public GenericPIDDrive(DrivetrainController drivetrain) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);
		drivetrain.getInput().getLeftDriveEncoder().zero();
		drivetrain.getInput().getRightDriveEncoder().zero();
		
	}
	
	/**
	 * 
	 * @param drivetrain
	 * @param max time to run - not implemented and shouldn't be trutsed
	 * @param targetDistance 
	 * @param maxDistance if we go further than this then stop
	 * @param maxOutput fast we can go 
	 */
	public GenericPIDDrive(DrivetrainController drivetrain, double time, double targetDistance, double maxDistance, double maxOutput) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);
		
		// Set the State
		
		drivetrain.getInput().getLeftDriveEncoder().zero();
		drivetrain.getInput().getRightDriveEncoder().zero();
		
		// Zero the Encoders
		this.maxTime = time;
		this.targetDistance = targetDistance;
		this.maxDistance = maxDistance;
		this.maxOutput = maxOutput;
	}
	
	public void setMaxTime(double time) {
		this.maxTime = time;
	}
	
	public void setTargetDistance(double targetDistance) {
		this.targetDistance = targetDistance;
	}

	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	public void setMaxOutput(double maxOutput) {
		this.maxOutput = maxOutput;
	}
	
	@Override
	public boolean execute() {
		double leftEncoder = drivetrain.getInput().getLeftDriveEncoder().getAngle();
		double rightEncoder = drivetrain.getInput().getRightDriveEncoder().getAngle();

		
		// Get the Encoders
		
		// Check if  we've exceded max angle 
		if ((leftEncoder > maxDistance) && (rightEncoder > maxDistance)) {
			return true;
		}
		
		
		// Calculate the Errors
		double leftError = targetDistance - drivetrain.getInput().getLeftDriveEncoder().getAngle();
		double rightError = targetDistance - drivetrain.getInput().getRightDriveEncoder().getAngle();
		
		
		// Calculate the Deravites
		double leftD = (leftError - previousLeftError) * RobotConstants.UPDATES_PER_SECOND;
		double rightD = (rightError - previousRightError) * RobotConstants.UPDATES_PER_SECOND;
		
		
		// Calualte and Limit the speed
		double leftSpeed = Math.min(Math.max(leftError * DrivetrainConstants.LEFT_DRIVE_P + leftD * DrivetrainConstants.LEFT_DRIVE_D, -maxOutput), maxOutput);
		double rightSpeed = Math.min(Math.max(rightError * DrivetrainConstants.RIGHT_DRIVE_P + rightD * DrivetrainConstants.RIGHT_DRIVE_D, -maxOutput), maxOutput);
		
		
		// Set the Previous Errors for Derative
		this.previousLeftError = leftError;
		this.previousRightError = rightError;
		
		// Check if we're going slowly
		if (Math.abs(leftError) < DrivetrainConstants.ACCEPTABLE_DISTANCE_ERROR && Math.abs(rightError) < DrivetrainConstants.ACCEPTABLE_DISTANCE_ERROR) {
			// Check if we've stoped
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
		
		// Set the Motors to zero and reset the state
	}

}
