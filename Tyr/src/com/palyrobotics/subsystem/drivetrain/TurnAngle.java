package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.State;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

public class TurnAngle extends Command {

	private DrivetrainController drivetrain;
	private double angle;
	private double previousError;
	
	/**
	 * Starts the command running, passing the drivetrain for input and
	 * output as well as the desired angle to turn to.
	 * @param drivetrain
	 * @param angle
	 */
	public TurnAngle(DrivetrainController drivetrain, double angle) {
		super(drivetrain.getRequirements());
		this.drivetrain = drivetrain;
		this.angle = angle;
		this.previousError = angle;
	}
	
	/**
<<<<<<< HEAD
	 * Called at the beginning of the command, and sets state to turning as well as zeros the encoders.	
=======
	 * Called at the beginning of the command, and sets state to turning.
	 * Zeros encoders.
>>>>>>> da9ef1b... Zeroed encoders.
	 */
	@Override
	public void initialize() {
		drivetrain.setState(State.TURNING_ANGLE);
		drivetrain.input.getLeftDriveEncoder().zero(); 
		drivetrain.input.getRightDriveEncoder().zero();
	}
	
	/**
	 * Executes 200 times a second, as long as the command is running.
	 * PID values are computed and the speed of the composed motors are set.
	 * Returns true when completed, false to run again.
	 */
	@Override
	public boolean execute() {
		//calculate error by using angle to distance 
		double error =  angle - drivetrain.input.getGyroscope().getAngle();
		
		//finds derivative with 1/200 update rate
		double derivative = (error - previousError) / UPDATE_RATE; 
		
		previousError = error;
		
		//min and max restrict the speeds between -0.5 and 0.5
		double leftSpeed = Math.max(Math.min(LEFT_ANGLE_P_VALUE * error + LEFT_ANGLE_D_VALUE * derivative, 0.5), -0.5); 
		double rightSpeed = Math.max(Math.min(RIGHT_ANGLE_P_VALUE * error + RIGHT_ANGLE_D_VALUE * derivative, 0.5), -0.5);
		
		drivetrain.output.getLeftMotor().setSpeed(leftSpeed);
		drivetrain.output.getRightMotor().setSpeed(rightSpeed);
		
		//stops robot when target is reached and robot has slowed within tolerance range
		if(derivative == 0.0 && Math.abs(error) < ACCEPTABLE_ANGLE_ERROR) { 
			drivetrain.output.getLeftMotor().setSpeed(0.0);
			drivetrain.output.getRightMotor().setSpeed(0.0);
			drivetrain.setState(State.IDLE);
			return true;
		}
		return false;
	}
	
	/**
	 * Called when command is interrupted. 
	 * Stops the motors and sets the State to idle.
	 */
	@Override
	public void interrupted() {
		drivetrain.output.getLeftMotor().setSpeed(0.0);
		drivetrain.output.getRightMotor().setSpeed(0.0);
		drivetrain.setState(State.IDLE);
	}
	
	/**
	 * Called when the command has finished. 
	 * Stops the motors and sets the State to idle.
	 */
	@Override
	public void end() {
		drivetrain.output.getLeftMotor().setSpeed(0.0);
		drivetrain.output.getRightMotor().setSpeed(0.0);
		drivetrain.setState(State.IDLE);
	}
}
