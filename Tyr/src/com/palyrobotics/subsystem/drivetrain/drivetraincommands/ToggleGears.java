package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

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

	}

	/**
	 * Basic gear shifting function for a toggle on/off gearshift control
	 * system.
	 * 
	 * @return whether or not the command is completed (always true here)
	 */
	@Override
	public boolean execute() {
		if (drivetrain.getOutput().getSolenoid().isRetracting()) {
			drivetrain.getOutput().getSolenoid().extend();
		} else {
			drivetrain.getOutput().getSolenoid().retract();
		}
		return true;
	}

	@Override
	public void interrupted() {

	}

	@Override
	public void end() {

	}

}
