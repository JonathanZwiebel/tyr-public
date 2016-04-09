package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import static com.palyrobotics.robot.RobotConstants.*;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import com.sun.javafx.runtime.SystemProperties;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class MoveToGoal extends Command {

	private DrivetrainController drivetrain;
	private NetworkTable table;
	private double previousLeftError;
	private double previousRightError;
	private double moveDistance;

	/**
	 * Moves the robot to the standard shooting distance. Called ONLY AFTER
	 * previously aligned to the goal, as this command will only drive the robot
	 * forward a set distance.
	 * 
	 * @param the
	 *            drivetrain to receive input and output to
	 */
	public MoveToGoal(DrivetrainController drivetrain) {
		super(drivetrain);
		this.drivetrain = drivetrain;
	}

	/**
	 * Finds how much the robot needs to move in order to be at the desired
	 * standard shooting distance.
	 */
	@Override
	public void initialize() {
		Logger.getLogger("Central").log(Level.INFO, "MoveToGoal command initialized.");
		this.table = NetworkTable.getTable("AutoAlign");
		this.moveDistance = table.getNumber("Distance", this.moveDistance);
		drivetrain.setDrivetrainState(DrivetrainState.MOVING_TO_GOAL);
		drivetrain.getInput().getLeftDriveEncoder().zero();
		drivetrain.getInput().getRightDriveEncoder().zero();
	}

	/**
	 * Move the robot to the standard shooting distance, which takes in an
	 * updating value for the distance away from the desired distance.
	 */
	@Override
	public boolean execute() {
		Logger.getLogger("Central").log(Level.FINE, "MoveToGoal command execute method called.");
		// Calculates error based on the target INCHES_TO_SHOOT and how far away we are from 
		// the goal currently.
		double leftError = moveDistance - INCHES_TO_SHOOT; 
		double rightError = moveDistance - INCHES_TO_SHOOT;

		double leftDerivative = (leftError - previousLeftError) * UPDATES_PER_SECOND;
		double rightDerivative = (rightError - previousRightError) * UPDATES_PER_SECOND;

		previousLeftError = leftError;
		previousRightError = rightError;

		double leftSpeed = Math.max(Math.min(LEFT_P_VALUE * leftError + LEFT_D_VALUE * leftDerivative, -0.5), 0.5);
		double rightSpeed = Math.max(Math.min(RIGHT_P_VALUE * rightError + RIGHT_D_VALUE * rightDerivative, -0.5), 0.5);

		System.out.println("Leftspeed: " + leftSpeed);
		System.out.println("Rightspeed: " + rightSpeed);
		drivetrain.getOutput().getLeftMotor().setSpeed(-leftSpeed);
		drivetrain.getOutput().getRightMotor().setSpeed(rightSpeed);

		if (drivetrain.getInput().getDriveStick().getTrigger().isTriggered()) {
			Logger.getLogger("Central").log(Level.INFO, "MoveToGoal breakout switch triggered.");
			return true;
		}

		if (Math.abs(leftError) < ACCEPTABLE_DISTANCE_ERROR && Math.abs(rightError) < ACCEPTABLE_DISTANCE_ERROR
				&& leftDerivative == 0.0 && rightDerivative == 0.0) {
			Logger.getLogger("Central").log(Level.INFO, "MoveToGoal conditions met.");
			return true;
		}
		return false;
	}

	/**
	 * Sets the state to idle.
	 */
	@Override
	public void interrupted() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
		Logger.getLogger("Central").log(Level.INFO, "MoveToGoal command interrupted.");
	}

	/**
	 * Sets the state to idle and stops the robot.
	 */
	@Override
	public void end() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
		drivetrain.getOutput().getLeftMotor().stop();
		drivetrain.getOutput().getRightMotor().stop();
		Logger.getLogger("Central").log(Level.INFO, "MoveToGoal command ended.");
	}

}