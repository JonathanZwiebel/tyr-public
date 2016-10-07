package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.SuccessiveAutoAlign;
import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveDownCommand;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveUpCommand;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorExtendCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorRetractCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorLockCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorUnlockCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;


public class CompetitionTwentyPointAuto extends CommandGroup{

	public final int INITIAL_DRIVE_TIME = 5;
	public final double INITIAL_DRIVE_SPEED = .5;
	
	public final int TIME_TO_SHOOT = 4;
	
	public CompetitionTwentyPointAuto(DrivetrainController drive, ShooterController shooter,
			GrabberController grabber) {
		
		sequentially(
				new GenericDriveAuto(drive,true, 1, 0.5f),
				new GenericDriveAutoDifferential(drive,true, 3, Integer.MAX_VALUE, 0.5f, 0.2f),
				new SuccessiveAutoAlign(drive, 0.33f),
				new ShooterUp(shooter),
				new ShooterLoadingActuatorRetractCommand(shooter), // Right now this an extension
				new CompetitionTwentyPointAuto.WaitFor(0.75f),
				new GrabberMoveUpCommand(grabber), 
				new CompetitionTwentyPointAuto.WaitFor(0.75f), 
				new ShooterLockingActuatorLockCommand(shooter), // Right now this is an unlock command
				new ShooterDown(shooter)
		);
		
	}
	
	public class ShooterUp extends Command {
		
		final double UP_SPEED = 0.8;
		private ShooterController shooter;
		private double endTime = 0.0;
		
		public ShooterUp(ShooterController shooter) {
			this.shooter = shooter;
		}
		
		@Override
		public void initialize() {
			endTime = System.currentTimeMillis() + 100;				
		}

		@Override
		public boolean execute() {			
			shooter.systems.getArmMotor().setSpeed(UP_SPEED);
			return true;
		}
	}
	

	public class ShooterDown extends Command {
		
		final double UP_SPEED = 0.8;
		private ShooterController shooter;
		private double endTime = 0.0;
		
		public ShooterDown(ShooterController shooter) {
			this.shooter = shooter;
		}
		
		@Override
		public void initialize() {
			endTime = System.currentTimeMillis() + 100;				
		}

		@Override
		public boolean execute() {
			shooter.systems.getArmMotor().setSpeed(0);
			return true;
		}
	}
	
	public class WaitFor extends Command {
		private double seconds;
		private double endTime;
		
		@Override
		public void initialize() {
			endTime = Timer.getFPGATimestamp() + seconds;
		}
		
		public WaitFor(float seconds) {
			this.seconds = seconds;
		}
		
		@Override
		public boolean execute() {
			return endTime < Timer.getFPGATimestamp();			
		}
		
	}
}
