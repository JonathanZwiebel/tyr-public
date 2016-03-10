package com.palyrobotics.robot.autonomous;

import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.commands.LowerArm;
import com.palyrobotics.subsystem.breacher.commands.RaiseArm;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.DriveDistance;

public class BreachChival extends CommandGroup {

	public BreachChival(BreacherController breacher, DrivetrainController drivetrain) {
		sequentially(new RaiseArm(breacher), new DriveDistance(drivetrain, -130, .4), new LowerArm(breacher),
				new DriveDistance(drivetrain, -50, .5));
	}
}
