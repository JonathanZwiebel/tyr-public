package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.GearState;

import edu.wpi.first.wpilibj.DoubleSolenoid;

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
		drivetrain.setGearState(drivetrain.getGearState() == GearState.RAISING_GEAR ? GearState.LOWERING_GEAR : GearState.RAISING_GEAR);
	}

	/**
	 * Basic gear shifting function for a toggle on/off gearshift control
	 * system.
	 * 
	 * @return whether or not the command is completed (always true here)
	 */
	@Override
	public boolean execute() {
		if(drivetrain.getGearState() == GearState.RAISING_GEAR) {
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
