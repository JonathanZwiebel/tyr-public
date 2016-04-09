package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.palyrobotics.robot.RobotConstants.*;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class OriginalDriveDistance extends Command {

	private DrivetrainController drivetrain;
	private double distance;
	private double previousLeftError;
	private double previousRightError;

	public OriginalDriveDistance(DrivetrainController drivetrain, double distance) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.distance = distance;
		this.previousLeftError = distance;
		this.previousRightError = distance;
	}

	/**
	 * Initializes state to driving distance and zeros the encoders.
	 */
	@Override
	public void initialize() {
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_DISTANCE);
		drivetrain.getInput().getLeftDriveEncoder().zero();
		drivetrain.getInput().getRightDriveEncoder().zero();
		Logger.getLogger("Central").log(Level.INFO, "OriginalDriveDistance command initialized.");
	}

	/**
	 * Executes 50 times a second as long as the command is running. Takes the
	 * distance, computes the speeds needed, and sets the speeds of the motors.
	 * Returns true when finished, and false to keep executing. Has manual
	 * override to stop command with the DriveStick trigger.
	 */
	@Override
	public boolean execute() {

		Logger.getLogger("Central").log(Level.FINE, "OriginalDriveDistance execute method called.");
		double leftError = distance - drivetrain.getInput().getLeftDriveEncoder().getAngle();
		double rightError = distance - drivetrain.getInput().getRightDriveEncoder().getAngle();

		// Derivative computed using the 50 updates / second update rate and the
		// change in error.
		double leftDerivative = (leftError - previousLeftError) * UPDATES_PER_SECOND;
		double rightDerivative = (rightError - previousRightError) * UPDATES_PER_SECOND;

		previousRightError = rightError;
		previousLeftError = leftError;

		// Calculates target speed and limits it from -0.5 to 0.5
		double leftSpeed = Math.max(Math.min(LEFT_P_VALUE * leftError + LEFT_D_VALUE * leftDerivative, 0.5), -0.5);
		double rightSpeed = Math.max(Math.min(RIGHT_P_VALUE * rightError + RIGHT_D_VALUE * rightDerivative, 0.5), -0.5);

		drivetrain.getOutput().getLeftMotor().setSpeed(leftSpeed);
		drivetrain.getOutput().getRightMotor().setSpeed(rightSpeed);

		if (drivetrain.getInput().getDriveStick().getTrigger().isTriggered()) {
			Logger.getLogger("Central").log(Level.INFO, "OriginalDriveDistance breakout switch triggered.");
			return true;
		}

		if (leftDerivative == 0.0 && rightDerivative == 0.0 && Math.abs(leftError) < ACCEPTABLE_DISTANCE_ERROR
				&& Math.abs(rightError) < ACCEPTABLE_DISTANCE_ERROR) {
			Logger.getLogger("Central").log(Level.INFO, "OriginalDriveDistance stop conditions met.");
			return true;
		}
		return false;
	}

	/**
	 * Sets the drivetrain state to idle to allow for smooth transition back to
	 * teleop.
	 */
	@Override
	public void interrupted() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
		Logger.getLogger("Central").log(Level.INFO, "OriginalDriveDistance command interrupted.");
	}

	/**
	 * Sets motor speed to 0 at the end of the command and switches the state to
	 * idle for a return to teleop.
	 */
	@Override
	public void end() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
		Logger.getLogger("Central").log(Level.INFO, "OriginalDriveDistance command ended.");
	}

}
