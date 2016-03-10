
package com.palyrobotics.robot.autonomous;

import org.strongback.Strongback;
import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.drivetraincommands.*;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;

public class BreachLowBar extends Command {
	private DrivetrainController drivetrain;

	public BreachLowBar(DrivetrainController drivetrain) {
		super(drivetrain);
		this.drivetrain = drivetrain;
	}

	@Override
	public boolean execute() {
		// Low Bar
		Strongback.submit(new DriveDistance(drivetrain, -253, .35));
		return true;

	}

}