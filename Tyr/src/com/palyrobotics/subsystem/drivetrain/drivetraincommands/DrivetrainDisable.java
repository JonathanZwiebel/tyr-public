package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
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
	
	/**
	 * Sets drivetrain state to disabled and stops all motor movement. 
	 */
	@Override
	public boolean execute() {
		drivetrain.setDrivetrainState(DrivetrainState.DISABLED);
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
		return true;
	}
}
