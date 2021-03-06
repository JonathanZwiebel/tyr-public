package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;

public class HighGear extends Command {

	private DrivetrainController drivetrain;

	/**
	 * Initializes command with DrivetrainController
	 */
	public HighGear(DrivetrainController drivetrain) {
		this.drivetrain = drivetrain;
	}

	@Override
	public void initialize() {

	}

	/**
	 * Basic gear shifting function 
	 * @return whether or not the command is completed (always true here)
	 */
	@Override
	public boolean execute() {
		drivetrain.getOutput().getSolenoid().extend();
		return true;
	}

	@Override
	public void interrupted() {

	}

	@Override
	public void end() {

	}
}
