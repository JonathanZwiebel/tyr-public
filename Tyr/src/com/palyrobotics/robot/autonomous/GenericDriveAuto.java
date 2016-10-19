package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class GenericDriveAuto extends Command{

	private final int driveTime;
	private final boolean forward; // set all the output to negative
	private final double maxOutput; // max motor output
	private double killTime; // time when the command will end
	private DrivetrainController driveTrain; //drivetrain controller
	
	/**
	 * Create a competition drive auto command.
	 * @param driveTrain	the drivetrain controller
	 * @param forward	should the motors be going forward or backward?
	 * @param driveTime	how many MILLISECONDS should the command run for
	 * @param maxOutput	max motor output
	 */
	public GenericDriveAuto(DrivetrainController driveTrain,boolean forward, int driveTime, double maxOutput) {
		super(driveTrain);
		driveTrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);
		this.forward = forward;
		this.maxOutput = maxOutput;
		this.driveTrain = driveTrain;
		this.driveTime = driveTime;
	}
	
	@Override
	public boolean execute() {
		if (killTime < System.currentTimeMillis()) {
			return true;
		}
		driveTrain.getOutput().getLeftMotor().setSpeed(forward ? maxOutput : -maxOutput);
		driveTrain.getOutput().getRightMotor().setSpeed(forward ? -maxOutput : maxOutput);
		
		return false;
	}
	
	@Override
	public void initialize() {
		killTime = System.currentTimeMillis() + driveTime;
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

