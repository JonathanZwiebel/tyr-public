package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.SuccessiveAutoAlign;
import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveUpCommand;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorRetractCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorLockCommand;

import edu.wpi.first.wpilibj.Timer;


public class CompetitionTwentyPointAuto extends CommandGroup{
	public CompetitionTwentyPointAuto(DrivetrainController drive, ShooterController shooter,
			GrabberController grabber, AccumulatorController intake) {
		
		sequentially(
				new GenericDriveAuto(drive,true, 2, 0.5f),
				new GenericDriveAutoDifferential(drive,true, 4, Integer.MAX_VALUE, 0.5f, 0.2f),
				new SuccessiveAutoAlign(drive, 0.33f),
				new IntakeInHold(intake),
				new CompetitionTwentyPointAuto.WaitFor(0.25f),
				new ShooterUpHold(shooter),
				new CompetitionTwentyPointAuto.WaitFor(0.5f),
				new IntakeDeadHold(intake),
				new ShooterLoadingActuatorRetractCommand(shooter), // Right now this an extension
				new CompetitionTwentyPointAuto.WaitFor(0.75f),
				new GrabberMoveUpCommand(grabber), 
				new CompetitionTwentyPointAuto.WaitFor(0.75f), 
				new ShooterLockingActuatorLockCommand(shooter), // Right now this is an unlock command
				new ShooterDeadHold(shooter)
		);
		
	}
	
	public class ShooterUpHold extends Command {
		final double UP_SPEED = 0.85;
		private ShooterController shooter;
		
		public ShooterUpHold(ShooterController shooter) {
			this.shooter = shooter;
		}

		@Override
		public boolean execute() {			
			shooter.systems.getArmMotor().setSpeed(UP_SPEED);
			return true;
		}
	}
	

	public class ShooterDeadHold extends Command {
		private ShooterController shooter;
		
		public ShooterDeadHold(ShooterController shooter) {
			this.shooter = shooter;
		}

		@Override
		public boolean execute() {
			shooter.systems.getArmMotor().setSpeed(0);
			return true;
		}
	}
	
	public class IntakeDeadHold extends Command {
		private AccumulatorController intake;
		
		public IntakeDeadHold(AccumulatorController intake) {
			this.intake = intake;
		}
		
		@Override
		public boolean execute() {
			intake.systems.getAccumulatorMotors().setSpeed(0.0f);
			return true;
		}
	}
	
	public class IntakeInHold extends Command {
		private AccumulatorController intake;
		
		public IntakeInHold(AccumulatorController intake) {
			this.intake = intake;
		}
		
		@Override
		public boolean execute() {
			intake.systems.getAccumulatorMotors().setSpeed(-1.0f);
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
