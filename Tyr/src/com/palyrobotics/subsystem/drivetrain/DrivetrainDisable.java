package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

/**
 * Disables the given drivetrain
 */
public class DrivetrainDisable extends Command {

	/** The drivetrain controller to be disabled **/
	DrivetrainController drivetrain;
	
	/**
	 * Constructs the drivetrain disabled command taking the drivetrain to be disabled
	 * 
	 * @param drivetrain the drivetrain to be disabled
	 */
	public DrivetrainDisable(DrivetrainController drivetrain) {
		super(drivetrain);
		this.drivetrain = drivetrain;
	}
	
	@Override
	public boolean execute() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
		drivetrain.output.getLeftMotor().setSpeed(0.0);
		drivetrain.output.getRightMotor().setSpeed(0.0);
		return true;
	}
}
