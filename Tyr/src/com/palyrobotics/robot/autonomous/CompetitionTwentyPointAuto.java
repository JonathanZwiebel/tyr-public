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
				new GenericDriveAuto(drive,true, 3, 0.5f),
				new GenericDriveAutoDifferential(drive,true, 2, Integer.MAX_VALUE, 0.7f, 0.2f),
				new CompetitionTwentyPointAuto.WaitFor(0.25f),
				new GenericTurnAngle(drive, 45, 1.0f, 2.0f),
				new GenericDriveAuto(drive,true, 1, 0.5f),
				new SuccessiveAutoAlign(drive, 0.33f),
				simultaneously(
						sequentially(
								new GrabberMoveUpCommand(grabber), 
								new ShooterLoadingActuatorExtendCommand(shooter),
								new CompetitionTwentyPointAuto.WaitFor(2.0f),
								new ShooterLockingActuatorUnlockCommand(shooter)		
								)
						),
						new ShooterUp(shooter,TIME_TO_SHOOT)
				);
		
	}
	
	public class ShooterUp extends Command {
		
		final double UP_SPEED = .6;
		private ShooterController shooter;
		private double endTime = 0.0;
		final double seconds;
		
		public ShooterUp(ShooterController shooter, float seconds) {
			this.shooter = shooter;
			this.seconds = seconds;
		}
		
		@Override
		public void initialize() {
			endTime = System.currentTimeMillis() + seconds * 1000;				
		}

		@Override
		public boolean execute() {
			if (endTime < System.currentTimeMillis()) return true;
			
			shooter.systems.getArmMotor().setSpeed(UP_SPEED);
			
			return false;
		}
		
		@Override
		public void end() {
			shooter.systems.getArmMotor().setSpeed(0.0);
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
