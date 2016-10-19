package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class GenericDriveAutoDifferential extends Command{

	private final int maxTime;
	private final boolean forward; // set all the output to negative
	private final double maxOutput; // max motor output
	private double killTime; // time when the command will end
	private final double leftOffset;
	private DrivetrainController driveTrain; //drivetrain controller
	
	/**
	 * Create a competition drive auto command.
	 * @param driveTrain	the drivetrain controller
	 * @param forward	should the motors be going forward or backward?
	 * @param maxTime	how many MILLISECONDS should the command run for
	 * @param maxDistance	max encoder distance
	 * @param maxOutput	max motor output
	 */
	public GenericDriveAutoDifferential(DrivetrainController driveTrain,boolean forward, int maxTime, 
			double maxOutput, double leftOffset) {
		super(driveTrain);
		driveTrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);

		this.forward = forward;
		this.maxOutput = maxOutput;
		this.driveTrain = driveTrain;
		this.leftOffset = leftOffset;
		this.maxTime = maxTime;
	}
	
	@Override
	public void initialize() {
		this.killTime = System.currentTimeMillis() + maxTime;
	}
	
	@Override
	public boolean execute() {
		// Make sure we aren't over the time limit
		if (this.killTime < System.currentTimeMillis()) {
			return true;
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

