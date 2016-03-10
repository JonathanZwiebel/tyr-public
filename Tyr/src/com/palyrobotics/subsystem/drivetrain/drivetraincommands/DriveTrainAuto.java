package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.breacher.commands.RaiseArm;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;

public class DriveTrainAuto extends Command {
	private DrivetrainController controller;

	public DriveTrainAuto(DrivetrainController controller) {
		super(controller);
		this.controller = controller;
	}
		
	@Override
	public boolean execute() {
		//Low Bar
		Strongback.submit(new DriveDistance(controller, -230,.35));
		return true;

	}

}
