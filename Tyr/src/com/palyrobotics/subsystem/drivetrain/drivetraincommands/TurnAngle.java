package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

public class TurnAngle extends Command {

	private DrivetrainController drivetrain;
	private double angle;
	private double previousError;

	/**
	 * Starts the command running, passing the drivetrain for input and output
	 * as well as the desired angle to turn to.
	 */
	public TurnAngle(DrivetrainController drivetrain, double angle) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.angle = angle;
		this.previousError = angle;
	}

	/**
	 * Called at the beginning of the command, and sets DrivetrainState to
	 * turning as well as zeros the gyroscope. 
	 */
	@Override
	public void initialize() {
		drivetrain.setDrivetrainState(DrivetrainState.TURNING_ANGLE);
		drivetrain.getInput().getGyroscope().zero();
	}

	/**
	 * Executes 50 times a second, as long as the command is running. PID values
	 * are computed and the speed of the composed motors are set. Returns true
	 * when completed, false to run again.
	 */
	@Override
	public boolean execute() {
		// calculate error by using angle to distance
		double error = angle - drivetrain.getInput().getGyroscope().getAngle();

		// finds derivative with 50 updates/second update rate
		double derivative = (error - previousError) / UPDATE_RATE;

		previousError = error;

		// min and max restrict the speeds between -0.5 and 0.5
		double leftSpeed = Math.max(Math.min(LEFT_ANGLE_P_VALUE * error + LEFT_ANGLE_D_VALUE * derivative, 0.5), -0.5);
		double rightSpeed = Math.max(Math.min(RIGHT_ANGLE_P_VALUE * error + RIGHT_ANGLE_D_VALUE * derivative, 0.5), -0.5);

		drivetrain.getOutput().getLeftMotor().setSpeed(leftSpeed);
		drivetrain.getOutput().getRightMotor().setSpeed(rightSpeed);

		// stops robot when target is reached and robot has slowed within tolerance range
		if (derivative == 0.0 && Math.abs(error) < ACCEPTABLE_ANGLE_ERROR) {
			drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
			drivetrain.getOutput().getRightMotor().setSpeed(0.0);
			drivetrain.setDrivetrainState(DrivetrainState.IDLE);
			return true;
		}
		if (drivetrain.getInput().getDriveStick().getTrigger().isTriggered()) {
			interrupted();
			return true;
		}
		return false;
	}

	/**
	 * Called when command is interrupted. Sets the DrivetrainState to idle to
	 * allow smooth transition back into teleop.
	 */
	@Override
	public void interrupted() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}

	/**
	 * Called when the command has finished. Stops the motors and sets the
	 * DrivetrainState to idle.
	 */
	@Override
	public void end() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}
}
