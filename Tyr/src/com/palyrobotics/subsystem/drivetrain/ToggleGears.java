package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ToggleGears extends Command {

	private DrivetrainController drivetrain;
	private boolean up;

	/**
	 * Initializes command with DrivetrainController reference, as well as what
	 * the current state of the gear shift is.
	 * 
	 * @param drivetrain
	 * @param gearState
	 */
	public ToggleGears(DrivetrainController drivetrain, boolean up) {
		this.drivetrain = drivetrain;
		this.up = up;
	}

	@Override
	public void initialize() {

	}

	/**
	 * Basic gear shifting function for a toggle on/off gearshift control
	 * system.
	 * 
	 * @return
	 */
	@Override
	public boolean execute() {
		if (up) {
			drivetrain.output.getSolenoid().set(DoubleSolenoid.Value.kForward);
		} else {
			drivetrain.output.getSolenoid().set(DoubleSolenoid.Value.kReverse);
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
