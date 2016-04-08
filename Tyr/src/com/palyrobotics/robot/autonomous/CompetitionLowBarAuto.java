package com.palyrobotics.robot.autonomous;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.*;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;
import static com.palyrobotics.robot.RobotConstants.*;

public class CompetitionLowBarAuto extends Command {

	private DrivetrainController drivetrain;
	private double distance;
	private double speedLimit;
	private double previousRightError;
	private double previousLeftError;
	private double angleError;
	private double previousAngleError;
	private double startTime;
	private double endTime;

	/**
	 * Initiates the command, passing in the drivetrain for input and output and
	 * passing the target distance to travel.
	 */
	public CompetitionLowBarAuto(DrivetrainController drivetrain, double distance) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.distance = distance;
		this.previousLeftError = distance;
		this.previousRightError = distance;
		this.angleError = 0.0;
		this.previousAngleError = 0.0;
		this.speedLimit = 0.3; // Default speed limit
	}

	public CompetitionLowBarAuto(DrivetrainController drivetrain, double distance, double speedLimit) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.distance = distance;
		this.previousLeftError = distance;
		this.previousRightError = distance;
		this.angleError = 0.0;
		this.previousAngleError = 0.0;
		this.speedLimit = speedLimit;
	}
	/**
	 * Called at the start of the command. Zeros the encoders as well as sets
	 * the DrivetrainState to driving distance. It also sets the gyroscope to 0.
	 */
	@Override
	public void initialize() {
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);
		drivetrain.getInput().getLeftDriveEncoder().zero();
		drivetrain.getInput().getRightDriveEncoder().zero();
		drivetrain.getInput().getGyroscope().reset();
		startTime = System.currentTimeMillis();
	}

	/**
	 * Executes 50 times a second as long as the command is running. Takes the
	 * distance, computes the speeds needed, and sets the speeds of the motors.
	 * Returns true when finished, and false to keep executing.
	 */
	@Override
	public boolean execute() {
		endTime = System.currentTimeMillis();
		if(endTime - startTime < 9000) {
			return false;
		}
		// Calculates error based on target distance and distance already
		// traveled.

		double leftError = distance - drivetrain.getInput().getLeftDriveEncoder().getAngle();
		double rightError = distance - drivetrain.getInput().getRightDriveEncoder().getAngle();

		// Derivative computed using the 50 updates / second update rate and the
		// change in error.
		double leftDerivative = (leftError - previousLeftError) * UPDATES_PER_SECOND;
		double rightDerivative = (rightError - previousRightError) * UPDATES_PER_SECOND;

		previousRightError = rightError;
		previousLeftError = leftError;

		// Calculates target speed and limits it from -0.5 to 0.5
		double leftSpeed = Math.max(Math.min(LEFT_P_VALUE * leftError + LEFT_D_VALUE * leftDerivative, speedLimit), -speedLimit);
		double rightSpeed = Math.max(Math.min(RIGHT_P_VALUE * rightError + RIGHT_D_VALUE * rightDerivative, speedLimit), -speedLimit);

		System.out.println("Left encoder: " + drivetrain.getInput().getLeftDriveEncoder().getAngle());
		System.out.println("Right encoder: " + drivetrain.getInput().getRightDriveEncoder().getAngle());
		// Calculates angle error, trying to set it to 0.
		angleError = 0 - drivetrain.getInput().getGyroscope().getAngle();
		double angleDerivative = (angleError - previousAngleError) * UPDATES_PER_SECOND;
		previousAngleError = angleError;

		// Require two separate angle speeds because they might require
		// different pid values.
		// Calculates the Turnspeed to be added to the movespeed, if the angle
		// error is above the threshold.
		double rightAngleSpeed = 0; //RIGHT_ANGLE_P_VALUE * angleError + RIGHT_ANGLE_D_VALUE * angleDerivative;
		double leftAngleSpeed = 0; //LEFT_ANGLE_P_VALUE * angleError + LEFT_ANGLE_D_VALUE * angleDerivative;

		drivetrain.getOutput().getLeftMotor().setSpeed(leftSpeed + leftAngleSpeed);
		drivetrain.getOutput().getRightMotor().setSpeed(-rightSpeed + rightAngleSpeed);

		// Stops the command if the robot is slowed down within a limit,
		// signaling the arrival at the target.
		if (Math.abs(leftDerivative) < 0.3 && Math.abs(rightDerivative) < 0.3 && Math.abs(leftError) < ACCEPTABLE_DISTANCE_ERROR
				&& Math.abs(rightError) < ACCEPTABLE_DISTANCE_ERROR) {
			System.out.println("conditions met. ");
			return true;
		}
		if (drivetrain.getInput().getDriveStick().getTrigger().isTriggered()) {
			System.out.println("force out");
			return true;
		}
		if(endTime - startTime > 14000) {
			return true;
		}
		return false;
	}

	/**
	 * Called when command is interrupted. Sets DrivetrainState to idle to allow
	 * smooth transition back to teleop.
	 */
	@Override
	public void interrupted() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
		System.out.println("Drive distance interrupted. ");
	}

	/**
	 * Called at the end of the command. Sets motors to 0 and sets
	 * DrivetrainState to idle.
	 */
	@Override
	public void end() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}
}
