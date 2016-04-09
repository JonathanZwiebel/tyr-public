package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;

public class ToggleGears extends Command {

	private DrivetrainController drivetrain;

	/**
	 * Initializes command with DrivetrainController reference, as well as what
	 * the current state of the gear shift is.
	 */
	public ToggleGears(DrivetrainController drivetrain) {
		this.drivetrain = drivetrain;
	}

	@Override
	public void initialize() {
		Logger.getLogger("Central").log(Level.INFO, "ToggleGears command initialized.");
	}

	/**
	 * Basic gear shifting function for a toggle on/off gearshift control
	 * system.
	 * 
	 * @return whether or not the command is completed (always true here)
	 */
	@Override
	public boolean execute() {
		Logger.getLogger("Central").log(Level.FINE, "ToggleGears command execute method running.");
		if (drivetrain.getOutput().getSolenoid().isRetracting()) {
			drivetrain.getOutput().getSolenoid().extend();
		} else {
			drivetrain.getOutput().getSolenoid().retract();
		}
		return true;
	}

	@Override
	public void interrupted() {
		Logger.getLogger("Central").log(Level.INFO, "ToggleGears command interrupted.");
	}

	@Override
	public void end() {
		Logger.getLogger("Central").log(Level.INFO, "ToggleGears command ended.");
	}
}
