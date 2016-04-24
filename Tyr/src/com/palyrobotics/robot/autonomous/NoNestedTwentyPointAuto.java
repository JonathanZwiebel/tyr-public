package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.robot.autonomous.CompetitionTwentyPointAuto.WaitFor;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.shooter.ShooterController;

public class NoNestedTwentyPointAuto extends CommandGroup{

	public enum ShootingSequentialState {
		INITIAL_WAIT,
		GRABBER_DOWN,
		SHOOTER_EXTEND,
		WAIT,
		UNLOCK
	}
	
	ShootingSequentialState shootState = ShootingSequentialState.INITIAL_WAIT;
	
	public final int INITIAL_DRIVE_TIME = 7;
	public final double INITIAL_DRIVE_SPEED = .6;
	
	public final int TIME_TO_SHOOT = 4;
	
	public NoNestedTwentyPointAuto(DrivetrainController drive, ShooterController shooter,
			GrabberController grabber) {
		sequentially(
				new GenericDriveAuto(drive,true,INITIAL_DRIVE_TIME, INITIAL_DRIVE_SPEED),
				new WaitFor(1),
				new SequentiallyShoot(drive,shooter,grabber,7)
				);
	}
	
	public class SequentiallyShoot extends Command{
	
		DrivetrainController drive;
		ShooterController shooter;
		GrabberController grabber;
		final double END_TIME;
		int waitIncrement;
		final int WAIT_TIME = 1;
		
		int initialWaitIncrement;
		final int INITIAL_WAIT_TIME = 1;
		
		public SequentiallyShoot(DrivetrainController drive, ShooterController shooter,
				GrabberController grabber, int seconds) {
			super(drive,shooter,grabber);
			this.drive = drive;
			this.shooter = shooter;
			this.grabber = grabber;
			this.END_TIME = System.currentTimeMillis() + seconds*1000;
		}

		
		@Override
		public boolean execute() {
			// time out
			if (END_TIME < System.currentTimeMillis()) return true;
			
			if (shootState == ShootingSequentialState.INITIAL_WAIT) {
				initialWaitIncrement++;
				if (initialWaitIncrement > INITIAL_WAIT_TIME * RobotConstants.UPDATES_PER_SECOND) {
					shootState = ShootingSequentialState.GRABBER_DOWN;
				}
			}
			
			if (shootState == ShootingSequentialState.GRABBER_DOWN){
				grabber.getOutput().getGrabber().retract();
				shootState = ShootingSequentialState.SHOOTER_EXTEND;
			}
			
			if (shootState == ShootingSequentialState.SHOOTER_EXTEND){
				shooter.systems.getLoadingActuator().extend();
				shootState = ShootingSequentialState.WAIT;
			}
			
			if (shootState == ShootingSequentialState.WAIT){
				waitIncrement++;
				if (waitIncrement > WAIT_TIME * RobotConstants.UPDATES_PER_SECOND) {
					shootState = ShootingSequentialState.UNLOCK;
				}
			}
			
			if (shootState == ShootingSequentialState.UNLOCK) {
				shooter.systems.getLockingActuator().retract();
				return true;
			}
			
			// Always keep the arm up
			shooter.systems.getArmMotor().setSpeed(.6);
			
			return false;
		}
		
	}
	
	public class WaitFor extends Command {

		private final double END_TIME;
		
		public WaitFor(double millis) {
			this.END_TIME = System.currentTimeMillis() + millis;
		}
		
		public WaitFor(int seconds) {
			this.END_TIME = System.currentTimeMillis() + seconds*1000;
		}
		
		@Override
		public boolean execute() {
			return END_TIME < System.currentTimeMillis();			
		}
		
	}
	
}
