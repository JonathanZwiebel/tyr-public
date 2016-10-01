package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class GenericDriveAuto extends Command{

	private final boolean forward; // set all the output to negative
	private final double maxDistance;  // max encoder distance
	private final double maxOutput; // max motor output
	private final double maxTimeRelativeToEpoch; // time when the command will end
	private DrivetrainController driveTrain; //drivetrain controller
	
	/**
	 * Create a competition drive auto command.
	 * @param driveTrain	the drivetrain controller
	 * @param forward	should the motors be going forward or backward?
	 * @param maxTime	how many SECONDS should the command run for
	 * @param maxDistance	max encoder distance
	 * @param maxOutput	max motor output
	 */
	public GenericDriveAuto(DrivetrainController driveTrain,boolean forward, int maxTime, double maxDistance,
			double maxOutput) {
		super(driveTrain);
		driveTrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);
		this.maxTimeRelativeToEpoch = System.currentTimeMillis() + 1000*maxTime;
		this.forward = forward;
		this.maxDistance = maxDistance;
		this.maxOutput = maxOutput;
		this.driveTrain = driveTrain;
		
	}
	
	/**
	 * Overloaded constructor that defaults the time, 
	 * max out, and max distance.
	 */
	public GenericDriveAuto(DrivetrainController driveTrain, boolean forward) {
		this(driveTrain, forward, Integer.MAX_VALUE, Double.MAX_VALUE, .5);
	}
	
	/**
	 * Same as first constructor but defaults the maxDistance
	 * to Double.MAX_VALUE.
	 */
	public GenericDriveAuto(DrivetrainController driveTrain, boolean forward, int maxTime, double maxOutput) {
		this(driveTrain, forward, maxTime, Double.MAX_VALUE, maxOutput);
	}
	
	/**
	 * Defalts the time and distance to max value.
	 */
	public GenericDriveAuto(DrivetrainController driveTrain, boolean forward, double maxOutput) {
		this(driveTrain, forward, Integer.MAX_VALUE, Double.MAX_VALUE, maxOutput);
	}
	
	@Override
	public boolean execute() {
		// Make sure we aren't over the time limit
		if (this.maxTimeRelativeToEpoch < System.currentTimeMillis()) {
			return true;
		}
		
		if (this.forward) {
			// encoder checks 
			if(driveTrain.getInput().getLeftDriveEncoder().getAngle() > this.maxDistance) {
				return true;
			}
			
			if(driveTrain.getInput().getRightDriveEncoder().getAngle() > this.maxDistance) {
				return true;
			}
		}
		else {
			// check in reverse if we are going backward.
			if(driveTrain.getInput().getLeftDriveEncoder().getAngle() < -this.maxDistance) {
				return true;
			}
			
			if(driveTrain.getInput().getRightDriveEncoder().getAngle() < -this.maxDistance) {
				return true;
			}
		}
		
		driveTrain.getOutput().getLeftMotor().setSpeed(forward ? maxOutput : -maxOutput);
		driveTrain.getOutput().getRightMotor().setSpeed(forward ? -maxOutput : maxOutput);
		
		return false;
	}

	@Override 
	public void interrupted() {
		driveTrain.setDrivetrainState(DrivetrainState.IDLE);
	}
	
	@Override
	public void end() {
		driveTrain.getOutput().getLeftMotor().setSpeed(0);
		driveTrain.getOutput().getRightMotor().setSpeed(0);
		driveTrain.setDrivetrainState(DrivetrainState.IDLE);
	}
}

