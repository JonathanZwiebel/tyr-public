package com.palyrobotics.subsystem.drivetrain;

import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

public class ShooterAlign extends Command {

	private DrivetrainController drivetrain;
	private double previousError;
	private double cameraError = 0.0;

	public ShooterAlign(DrivetrainController drivetrain) {
		this.drivetrain = drivetrain;
		this.previousError = cameraError;
	}

	/**
	 * Zeros the gyroscope and sets the drivetrain's state to be aligning with
	 * the goal.
	 */
	@Override
	public void initialize() {
		drivetrain.setDrivetrainState(DrivetrainState.SHOOTER_ALIGN);
		this.cameraError = 0.0;
	}

	/**
	 * Takes in the pixels of error between the camera and the goal and faces
	 * the robot toward the goal for a shot.
	 * 
	 * @return true to end the command, and false to continue looping. 
	 */
	@Override
	public boolean execute() {
		// finds derivative with 50 updates/second update rate
		double derivative = (cameraError - previousError) / UPDATE_RATE;

		previousError = cameraError;

		// min and max restrict the speeds between -0.5 and 0.5
		double leftSpeed = Math.max(Math.min(LEFT_SHOOTER_P_VALUE * cameraError + LEFT_SHOOTER_D_VALUE * derivative, 0.5), -0.5);
		double rightSpeed = Math.max(Math.min(RIGHT_SHOOTER_P_VALUE * cameraError + RIGHT_SHOOTER_D_VALUE * derivative, 0.5), -0.5);

		drivetrain.output.getLeftMotor().setSpeed(leftSpeed);
		drivetrain.output.getRightMotor().setSpeed(rightSpeed);

		// stops robot when the target is reached and robot has slowed within tolerance range
		if (derivative == 0.0 && Math.abs(cameraError) < ACCEPTABLE_PIXEL_ERROR) {
			drivetrain.output.getLeftMotor().setSpeed(0.0);
			drivetrain.output.getRightMotor().setSpeed(0.0);
			drivetrain.setDrivetrainState(DrivetrainState.IDLE);
			return true;
		}
		// stops the command if the driver triggers the turnstick.
		if (drivetrain.input.getTurnStick().getTrigger().isTriggered()) {
			interrupted();
			return true;
		}
		return false;
	}

	/**
	 * Sets drivetrain state to idle to allow for smooth transition back to
	 * teleop.
	 */
	@Override
	public void interrupted() {
		drivetrain.output.getLeftMotor().setSpeed(0.0);
		drivetrain.output.getRightMotor().setSpeed(0.0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}

	/**
	 * Sets the motors to 0 to allow for a shot that would follow this command,
	 * as well as sets the state to idle to allow transition to a different
	 * command.
	 */
	@Override
	public void end() {
		drivetrain.output.getLeftMotor().setSpeed(0.0);
		drivetrain.output.getRightMotor().setSpeed(0.0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}

}
