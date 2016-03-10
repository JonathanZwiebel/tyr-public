package com.palyrobotics.robot.autonomous;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.TurnAngle;
import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveDownCommand;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorUnlockCommand;

public class AutoShoot extends CommandGroup {
	
	public AutoShoot(DrivetrainController drivetrain, ShooterController shooter, GrabberController grabber) {
		sequentially(
				new GrabberMoveDownCommand(grabber),
				new BreachLowBar(drivetrain),
				new TurnAngle(drivetrain, 45),
				simultaneously (
						Command.create(1.5, () -> {
							shooter.systems.getArmMotor().setSpeed(.3);
							
						}),
						sequentially(
								Command.pause(1),
								new ShooterLockingActuatorUnlockCommand(shooter)
						)
				)
			);
		}
}
