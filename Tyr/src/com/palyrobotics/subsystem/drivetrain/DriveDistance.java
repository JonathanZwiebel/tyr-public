package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.*;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

public class DriveDistance extends Command {
	
	private DrivetrainController drivetrain;
	private double distance;
	private double previousRightError;
	private double previousLeftError;
	
	/**
	 * Initiates the command, passing in the drivetrain for input and output
	 * and passing the target distance to travel.
	 * @param drivetrain
	 * @param distance
	 */
	public DriveDistance(DrivetrainController drivetrain, double distance) {
		super(drivetrain.getRequirements());
		this.drivetrain = drivetrain;
		this.distance = distance;
		this.previousLeftError = distance;
		this.previousRightError = distance;
		
	}
	
	/**
	 * Called at the start of the command. 
	 * Zeros the encoders as well as sets the state to driving distance.
	 */
	@Override
	public void initialize() {
		drivetrain.setState(State.DRIVING_DISTANCE);
		drivetrain.input.getLeftDriveEncoder().zero();
		drivetrain.input.getRightDriveEncoder().zero();
		
	}
	
	/**
	 * Executes once every 1/200 seconds as long as the command is running.
	 * Takes the distance, computes the speeds needed, and sets the speeds
	 * of the motors. Returns true when finished, and false to keep
	 * executing. 
	 */
	@Override
	public boolean execute() {
		//calculates error based on target distance and distance already traveled.
		double leftError = distance - drivetrain.input.getLeftDriveEncoder().getAngle();
		double rightError = distance - drivetrain.input.getRightDriveEncoder().getAngle();
		
		//Derivative computed using the 1/200 update rate and the change in error.
		double leftDerivative = (leftError - previousLeftError)/UPDATE_RATE; 
		double rightDerivative = (rightError - previousRightError)/UPDATE_RATE;
		
		previousRightError = rightError;
		previousLeftError = leftError;
		
		//calculates target speed and limits it from -0.5 to 0.5
		double leftSpeed = Math.max(Math.min(LEFT_P_VALUE * leftError + LEFT_D_VALUE * leftDerivative, 0.5), -0.5);
		double rightSpeed = Math.max(Math.min(RIGHT_P_VALUE * rightError + RIGHT_D_VALUE * rightDerivative, 0.5), -0.5);
		
		drivetrain.output.getLeftMotor().setSpeed(leftSpeed);
		drivetrain.output.getRightMotor().setSpeed(rightSpeed);

		//stops the command if the robot is slowed down within a limit, signaling the arrival at the target.
		if(leftDerivative == 0.0 && rightDerivative == 0.0 && 
				Math.abs(leftError) < ACCEPTABLE_DISTANCE_ERROR && Math.abs(rightError) < ACCEPTABLE_DISTANCE_ERROR) {
			drivetrain.output.getLeftMotor().setSpeed(0.0);
			drivetrain.output.getRightMotor().setSpeed(0.0);
			drivetrain.setState(State.IDLE);
			return true;
		}
		return false;
	}
	
	/**
	 * Called when command is interrupted.
	 * Sets motors to 0 and sets State to idle.
	 */
	@Override
	public void interrupted() {
		drivetrain.output.getLeftMotor().setSpeed(0.0);
		drivetrain.output.getRightMotor().setSpeed(0.0);
		drivetrain.setState(State.IDLE);
	}
	
	/**
	 * Called at the end of the command.
	 * Sets motors to 0 and sets State to idle.
	 */
	@Override
	public void end() {
		drivetrain.output.getLeftMotor().setSpeed(0.0);
		drivetrain.output.getRightMotor().setSpeed(0.0);
		drivetrain.setState(State.IDLE);
	}
}
