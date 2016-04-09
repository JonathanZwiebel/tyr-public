package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;

public class LowGear extends Command {

	private DrivetrainController drivetrain;

	/**
	 * Initializes command with DrivetrainController
	 */
	public LowGear(DrivetrainController drivetrain) {
		this.drivetrain = drivetrain;
	}

	@Override
	public void initialize() {
		Logger.getLogger("Central").log(Level.INFO, "LowGear command initialized.");
	}

	/**
	 * Basic gear shifting function 
	 * @return whether or not the command is completed (always true here)
	 */
	@Override
	public boolean execute() {
		Logger.getLogger("Central").log(Level.FINE, "LowGear command execute method called.");
		drivetrain.getOutput().getSolenoid().retract();
		return true;
	}

	@Override
	public void interrupted() {
		Logger.getLogger("Central").log(Level.INFO, "LowGear command interrupted.");
	}

	@Override
	public void end() {
		Logger.getLogger("Central").log(Level.INFO, "LowGear command ended.");
	}
}