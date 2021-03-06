package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.SuccessiveAutoAlign;
import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorExtendCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorUnlockCommand;

public class CompetitionTwentyPointAuto extends CommandGroup{
	public CompetitionTwentyPointAuto(DrivetrainController drive, ShooterController shooter,
			GrabberController grabber, AccumulatorController intake) {
		
		sequentially(
				new ShooterDownHold(shooter),
				new CompetitionTwentyPointAuto.WaitFor(500),
				new ShooterDeadHold(shooter),
				new GenericDriveAuto(drive,true, 2000, 0.45f),
				new GenericDriveAutoDifferential(drive,true, 2000, 0.5f, 0.2f),
				new SuccessiveAutoAlign(drive, 0.33f, 7000),
				new ShooterUpHold(shooter),
				new CompetitionTwentyPointAuto.WaitFor(100),
				new IntakeInHold(intake),
				new CompetitionTwentyPointAuto.WaitFor(500),
				new IntakeDeadHold(intake),
				new ShooterLoadingActuatorExtendCommand(shooter),
				new CompetitionTwentyPointAuto.WaitFor(750),
				new GrabberUpInterior(grabber), 
				new CompetitionTwentyPointAuto.WaitFor(750), 
				new ShooterLockingActuatorUnlockCommand(shooter),
				new CompetitionTwentyPointAuto.WaitFor(2000),
				new ShooterDeadHold(shooter)
		);
		
	}
	
	public class ShooterUpHold extends Command {
		final double UP_SPEED = 0.971 * 254 / 330;
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
	
	public class ShooterDownHold extends Command {
		final double UP_SPEED = 0.971 * 254 / 330;
		private ShooterController shooter;
		
		public ShooterDownHold(ShooterController shooter) {
			this.shooter = shooter;
		}

		@Override
		public boolean execute() {			
			shooter.systems.getArmMotor().setSpeed(-UP_SPEED);
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
	
	public class GrabberUpInterior extends Command {
		private GrabberController grabber;
		
		public GrabberUpInterior(GrabberController grabber) {
			this.grabber = grabber;
		}

		@Override
		public boolean execute() {
			grabber.getOutput().getGrabber().retract();
			return true;
		}
	}
	
	public class WaitFor extends Command {
		private double driveTime;
		private double endTime;
		
		@Override
		public void initialize() {
			endTime = System.currentTimeMillis() + driveTime;
		}
		
		public WaitFor(float driveTime) {
			this.driveTime = driveTime;
		}
		
		@Override
		public boolean execute() {
			return endTime < System.currentTimeMillis();			
		}		
	}
}
