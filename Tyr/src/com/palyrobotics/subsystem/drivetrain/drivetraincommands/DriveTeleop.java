package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.*;

import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

/**
 * The class representing the teleop driving command
 */
public class DriveTeleop extends Command {

	/** The drivetrain controller reference of this driving command **/
	private DrivetrainController drivetrain;
	
	private float scaling;

	/**
	 * Initializes command with the given drivetrain controller reference
	 * 
	 * @param drivetrain
	 *            the drivetrain controller operated by this command
	 */
	public DriveTeleop(DrivetrainController drivetrain, float scaling) {
		super(drivetrain);
		this.drivetrain = drivetrain;
		this.scaling = scaling;
	}

	/**
	 * Called at the beginning of the command, setting the DrivetrainState of
	 * the referenced drivetrain to teleop driving
	 */
	@Override
	public void initialize() {
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_TELEOP);
	}

	/**
	 * Executes continuously while the command is in action, computes speeds
	 * from joysticks and sends to talons
	 */
	@Override
	public boolean execute() {
		double forwardSpeed = TELEOP_ORIENTATION * drivetrain.getInput().getDriveStick().getPitch().read();
		double turnSpeed = drivetrain.getInput().getTurnStick().getRoll().read();

		double leftTargetOutput = -(forwardSpeed - turnSpeed);
		double rightTargetOutput = (forwardSpeed + turnSpeed);
		
		double leftCurrentOutput = drivetrain.getOutput().getLeftMotor().getSpeed();
		double rightCurrentOutput = drivetrain.getOutput().getRightMotor().getSpeed();

		double leftError = scaling * (leftTargetOutput - leftCurrentOutput);
		double rightError = scaling * (rightTargetOutput - rightCurrentOutput);

		double leftDiff = Math.max(Math.min(leftError, MAX_TELEOP_ACCELERATION), -MAX_TELEOP_ACCELERATION);
		double rightDiff = Math.max(Math.min(rightError, MAX_TELEOP_ACCELERATION), -MAX_TELEOP_ACCELERATION);

		drivetrain.getOutput().getLeftMotor().setSpeed(leftCurrentOutput + leftDiff);
		drivetrain.getOutput().getRightMotor().setSpeed(rightCurrentOutput + rightDiff);

		return false;
	}

	/**
	 * Sets the motors to zero speed and DrivetrainState to IDLE when drive
	 * teleop is interrupted
	 */
	@Override
	public void interrupted() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
	}
}