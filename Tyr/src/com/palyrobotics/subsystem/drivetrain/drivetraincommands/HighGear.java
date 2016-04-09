package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import java.util.logging.Level;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import java.util.logging.Logger;

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
		Logger.getLogger("Central").log(Level.INFO, "HighGear command initialized.");
	}

	/**
	 * Basic gear shifting function 
	 * @return whether or not the command is completed (always true here)
	 */
	@Override
	public boolean execute() {
		drivetrain.getOutput().getSolenoid().extend();
		Logger.getLogger("Central").log(Level.INFO, "Solenoid extended.");
		return true;
	}

	@Override
	public void interrupted() {
		Logger.getLogger("Central").log(Level.INFO, "HighGear command interrupted.");
	}

	@Override
	public void end() {
		Logger.getLogger("Central").log(Level.INFO, "HighGear command ended. ");
	}
}
