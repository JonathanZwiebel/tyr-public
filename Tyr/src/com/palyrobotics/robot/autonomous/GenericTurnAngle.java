package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;

import static com.palyrobotics.robot.RobotConstants.*;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class GenericTurnAngle extends Command {
	
	//The drivetrain to turn
	private DrivetrainController drivetrain;

	//The desired angle to turn
	private double angle;
	
	//The maximum speed to go
	private double maxSpeed;
	
	//The maximum running time in seconds
	private double maxTime;
	
	//The starting positions of each side
	private double startLeftPosition;
	private double startRightPosition;
	
	//The target positions of each side
	private double targetLeftPosition;
	private double targetRightPosition;
	
	//The time at which this command began
	private double startTime;
	
	//The errors of the encoders
	private double leftError;
	private double rightError;
	
	//The last errors of the encoders, used for derivative calculations
	private double lastLeftError;
	private double lastRightError;
	
	/**
	 * Creates a GenericTurnAngle command that turns a specified angle with no maximum speed or running time.
	 * Works best with angles between -180 and 180 degrees.
	 * 
	 * @param drivetrain the drivetrain that is turning
	 * @param angle the desired angle to turn. Turning clockwise is considered to be a positive angle.
	 */
	public GenericTurnAngle(DrivetrainController drivetrain, double angle) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.angle = angle;
		this.maxSpeed = Double.MAX_VALUE;
		this.maxTime = Double.MAX_VALUE;
	}
	
	/**
	 * Creates a GenericTurnAngle command that turns a specified angle, with a maximum speed and maximum running time.
	 * Works best with angles between -180 and 180 degrees.
	 * 
	 * @param drivetrain the drivetrain that is turning
	 * @param angle the desired angle to turn. Turning right is considered positive angle.
	 * @param maxSpeed the maximum allowed speed, should be between 0 and 1.
	 * @param maxTime the maximum running time in seconds.
	 */
	public GenericTurnAngle(DrivetrainController drivetrain, double angle, double maxSpeed, double maxTime) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.angle = angle;
		this.maxSpeed = maxSpeed;
		this.maxTime = maxTime;
	}
	
	/**
	 * Initializes the starting time, starting positions and target positions for turning angle.
	 * Also sets the initial previous errors for use in the PD loop in execute().
	 */
	@Override
	public void initialize() {
		//Set the drivetrain state to TURNING_ANGLE
		drivetrain.setDrivetrainState(DrivetrainState.TURNING_ANGLE);
		
		//Set the start positions of the encoders
		startLeftPosition = drivetrain.getInput().getLeftDriveEncoder().getAngle();
		startRightPosition = drivetrain.getInput().getRightDriveEncoder().getAngle();
		
		//Calculate the target positions of each encoder in order to turn the desired angle
		targetLeftPosition = startLeftPosition + angle * DEGREE_TO_DISTANCE;
		targetRightPosition = startRightPosition - angle * DEGREE_TO_DISTANCE;
		
		//Set the last error for use in the first cycle of execute()
		lastLeftError = targetLeftPosition - startLeftPosition;
		lastRightError = targetRightPosition - startRightPosition;
		
		//Set the start time to use for the maximum time limit
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Runs a PD loop to control the movement of each motor in order to turn the desired angle.
	 * Upon reaching the target angle at a slow enough speed, this method returns true to end the command.
	 * Also returns true when the maximum running time has been reached.
	 */
	@Override
	public boolean execute() {
		
		//If the max time has been reached, end the command
		if((System.currentTimeMillis() - startTime)/1000 > maxTime) {
			return true;
		}

		//Calculate the proportionals to be used in the PD loop
		leftError = targetLeftPosition - drivetrain.getInput().getLeftDriveEncoder().getAngle();
		rightError = targetRightPosition - drivetrain.getInput().getRightDriveEncoder().getAngle();
		
		//Calculate the derivatives to be used in the PD loop
		double leftDerivative = (leftError - lastLeftError) * UPDATES_PER_SECOND;
		double rightDerivative = (rightError - lastRightError) * UPDATES_PER_SECOND;
		
		//If the drivetrain has reached the desired angle and is moving slowly, exit the command
		if(Math.abs(targetLeftPosition - drivetrain.getInput().getLeftDriveEncoder().getAngle()) / DEGREE_TO_DISTANCE < ACCEPTABLE_ANGLE_ERROR
				&& (Math.abs(targetRightPosition - drivetrain.getInput().getRightDriveEncoder().getAngle()) / DEGREE_TO_DISTANCE) < ACCEPTABLE_ANGLE_ERROR
				&& Math.abs(leftDerivative) < ACCEPTABLE_DERIVATIVE && Math.abs(rightDerivative) < ACCEPTABLE_DERIVATIVE) {
			return true;
		}
		
		//Turn the drivetrain with values calculated with the above PD loop
		drivetrain.getOutput().getLeftMotor().setSpeed(Math.max(-maxSpeed, Math.min(maxSpeed, LEFT_ANGLE_P_VALUE * leftError + LEFT_ANGLE_D_VALUE * leftDerivative)));
		drivetrain.getOutput().getRightMotor().setSpeed(Math.max(-maxSpeed, Math.min(maxSpeed, RIGHT_ANGLE_P_VALUE * rightError + RIGHT_ANGLE_D_VALUE * rightDerivative)));

		//Set the last errors to be used for derivative calculations
		lastLeftError = leftError;
		lastRightError = rightError;
		
		//return false so that this method gets called again
		return false;
	}
	
	
	/**
	 * Sets the speed of both motors to 0 and the drivetrain state to IDLE upon ending this command.
	 */
	@Override
	public void end() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0);
		drivetrain.getOutput().getRightMotor().setSpeed(0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}

}
