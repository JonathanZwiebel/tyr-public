package com.palyrobotics.robot.autonomous;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.TurnAngle;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorUnlockCommand;

public class AutoShoot extends Command {
	private DrivetrainController drivetrain;
	private ShooterController shooter;
	
	public AutoShoot(DrivetrainController drive, ShooterController shooter) {
		this.drivetrain = drive;
		this.shooter = shooter;
	}

	@Override
	public boolean execute() {
		Strongback.submit(CommandGroup.runSequentially(new BreachLowBar(drivetrain), new TurnAngle(drivetrain, 45), new ShooterLockingActuatorUnlockCommand(shooter)));
		return true;
	}

}
