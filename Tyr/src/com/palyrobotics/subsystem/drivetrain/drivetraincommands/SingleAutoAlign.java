package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;
import static com.palyrobotics.robot.RobotConstants.*;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SingleAutoAlign extends Command {

	private double speedLimit;
	private double xDisplacement;
	private double previousError;
	private NetworkTable table;
	private DrivetrainController drivetrain;

	/**
	 * This command creates turn angle commands that are called repeatedly until
	 * the desired xDisplacement is met.
	 * 
	 * @param drivetrain
	 *            to receive input and send output to.
	 */
	public SingleAutoAlign(DrivetrainController drivetrain) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.speedLimit = 1.0;
	}

	/**
	 * Overloaded constructor to allow for a speed limit while turning.
	 * 
	 * @param drivetrain
	 *            to receive input and send output to.
	 * @param speed
	 *            limit of the drivetrain.
	 */
	public SingleAutoAlign(DrivetrainController drivetrain, double speedLimit) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.speedLimit = speedLimit;
	}

	@Override
	public void initialize() {
		this.table = NetworkTable.getTable("AutoAlign");
		this.xDisplacement = table.getNumber("xDisplacement", this.xDisplacement);
		this.previousError = 0.0;
		drivetrain.setDrivetrainState(DrivetrainState.ALIGN_TO_GOAL);
		drivetrain.getInput().getLeftDriveEncoder().zero();
		drivetrain.getInput().getRightDriveEncoder().zero();
	}

	@Override
	public boolean execute() {
		if (drivetrain.getInput().getDriveStick().getTrigger().isTriggered()) { // Hard
																				// breakout
			table.putBoolean("Reset", true);
			return true;
		}
		double encoderDisplacement = PIXELS_PER_DISTANCE * (drivetrain.getInput().getLeftDriveEncoder().getAngle());

		double error = xDisplacement - encoderDisplacement;
		double derivative = (error - previousError) * UPDATES_PER_SECOND;
		previousError = error;

		double leftSpeed = Math.min(
				Math.max(LEFT_SHOOTER_P_VALUE * error + LEFT_SHOOTER_D_VALUE * derivative, -speedLimit), speedLimit);
		double rightSpeed = Math.min(
				Math.max(RIGHT_SHOOTER_P_VALUE * error + RIGHT_SHOOTER_D_VALUE * derivative, -speedLimit), speedLimit);

		drivetrain.getOutput().getLeftMotor().setSpeed(leftSpeed);
		drivetrain.getOutput().getRightMotor().setSpeed(rightSpeed);

		if (Math.abs(error) > ACCEPTABLE_PIXEL_ERROR) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Overrides the default Strongback interrupted method. Called when the
	 * command is interrupted unexpectedly by something else.
	 */
	@Override
	public void interrupted() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}

	/**
	 * Overrides the default Strongback end method. Called when the command ends
	 * by us returning true in the execute method.
	 */
	@Override
	public void end() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
	}
}