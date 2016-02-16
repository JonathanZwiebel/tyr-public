package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class OriginalDriveTeleop extends Command{
	
	private DrivetrainController drivetrain;
	
	public OriginalDriveTeleop(DrivetrainController drivetrain) {
		super(drivetrain);
		this.drivetrain = drivetrain;
	}

	/**
	 * Sets the state to driving teleop. 
	 */
	@Override
	public void initialize() {
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_TELEOP);
	}
	
	/**
	 * Gets input from the joysticks and sets them to appropriate values. 
	 * This command should never end, and only be interrupted. 
	 */
	@Override
	public boolean execute() {
		double forwardSpeed = drivetrain.getInput().getDriveStick().getPitch().read();
		double turnSpeed = drivetrain.getInput().getTurnStick().getRoll().read();
		
		double targetRightSpeed = (forwardSpeed + turnSpeed);
		double targetLeftSpeed = -(forwardSpeed - turnSpeed);
		
		drivetrain.getOutput().getRightMotor().setSpeed(targetRightSpeed);
		drivetrain.getOutput().getLeftMotor().setSpeed(targetLeftSpeed);
		
		return false;
	}
	
	/**
	 * Sets the state to idle. 
	 */
	@Override
	public void interrupted() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}

}
