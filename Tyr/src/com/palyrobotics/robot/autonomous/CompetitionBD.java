package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.shooter.ShooterController;


public class CompetitionBD extends CommandGroup{
	public CompetitionBD(DrivetrainController drive, ShooterController shooter) {
		sequentially(
				new ShooterDownHold(shooter),
				new CompetitionBD.WaitFor(500),
				new ShooterDeadHold(shooter),
				new GenericDriveAuto(drive,false, 1500, 1.0f)
		);
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
