package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import com.sun.istack.internal.logging.Logger;

public class GenericDriveAutoDifferential extends Command{

	private final boolean forward; // set all the output to negative
	private final double maxDistance;  // max encoder distance
	private final double maxOutput; // max motor output
	private final double maxTimeRelativeToEpoch; // time when the command will end
	private final double leftOffset;
	private DrivetrainController driveTrain; //drivetrain controller
	
	/**
	 * Create a competition drive auto command.
	 * @param driveTrain	the drivetrain controller
	 * @param forward	should the motors be going forward or backward?
	 * @param maxTime	how many SECONDS should the command run for
	 * @param maxDistance	max encoder distance
	 * @param maxOutput	max motor output
	 */
	public GenericDriveAutoDifferential(DrivetrainController driveTrain,boolean forward, int maxTime, double maxDistance,
			double maxOutput, double leftOffset) {
		super(driveTrain);
		driveTrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);
		this.maxTimeRelativeToEpoch = System.currentTimeMillis() + 1000*maxTime;
		this.forward = forward;
		this.maxDistance = maxDistance;
		this.maxOutput = maxOutput;
		this.driveTrain = driveTrain;
		this.leftOffset = leftOffset;
	}
	
	/**
	 * Overloaded constructor that defaults the time, 
	 * max out, and max distance.
	 */
	public GenericDriveAutoDifferential(DrivetrainController driveTrain, boolean forward) {
		this(driveTrain, forward, Integer.MAX_VALUE, Double.MAX_VALUE, .5, 0.0f);
	}
	
	/**
	 * Same as first constructor but defaults the maxDistance
	 * to Double.MAX_VALUE.
	 */
	public GenericDriveAutoDifferential(DrivetrainController driveTrain, boolean forward, int maxTime, double maxOutput) {
		this(driveTrain, forward, maxTime, Double.MAX_VALUE, maxOutput, 0.0f);
	}
	
	/**
	 * Defalts the time and distance to max value.
	 */
	public GenericDriveAutoDifferential(DrivetrainController driveTrain, boolean forward, double maxOutput) {
		this(driveTrain, forward, Integer.MAX_VALUE, Double.MAX_VALUE, maxOutput, 0.0f);
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
		
		driveTrain.getOutput().getLeftMotor().setSpeed(forward ? maxOutput + leftOffset / 2 : -maxOutput - leftOffset / 2);
		driveTrain.getOutput().getRightMotor().setSpeed(forward ? -(maxOutput - leftOffset / 2) : -(-maxOutput + leftOffset / 2) );
		
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

