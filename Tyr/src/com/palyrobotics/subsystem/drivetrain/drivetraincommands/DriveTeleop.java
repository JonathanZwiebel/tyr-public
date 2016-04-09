package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.*;

import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class representing the teleop driving command
 */
public class DriveTeleop extends Command {

	/** The drivetrain controller reference of this driving command **/
	private DrivetrainController drivetrain;
	
	/** 
	 * Multiplicative scaling value for drivetrain speed
	 */
	private float scaling;
	
	/**
	 * Maximum drivetrain speed
	 */

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
		Logger.getLogger("Central").log(Level.INFO, "DriveTeleop command initialized.");
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_TELEOP);
	}

	/**
	 * Executes continuously while the command is in action, computes speeds
	 * from joysticks and sends to talons
	 */
	@Override
	public boolean execute() {
		Logger.getLogger("Central").log(Level.FINE, "DriveTeleop command execute method running.");
		if (drivetrain.getDrivetrainState() != DrivetrainState.DRIVING_TELEOP) {
			return true;
		}
		double forwardSpeed = TELEOP_ORIENTATION * drivetrain.getInput().getDriveStick().getPitch().read();
		double turnSpeed = drivetrain.getInput().getTurnStick().getRoll().read();

		double leftTargetOutput = -(forwardSpeed - turnSpeed);
		double rightTargetOutput = (forwardSpeed + turnSpeed);

		drivetrain.getOutput().getLeftMotor().setSpeed(scaling * leftTargetOutput);
		drivetrain.getOutput().getRightMotor().setSpeed(scaling * rightTargetOutput);

		return false;
	}

	/**
	 * Sets the motors to zero speed and DrivetrainState to IDLE when drive
	 * teleop is interrupted
	 */
	@Override
	public void interrupted() {
		Logger.getLogger("Central").log(Level.INFO, "DriveTeleop command interrupted.");
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
	}
	
	@Override
	public void end() {
		Logger.getLogger("Central").log(Level.INFO, "DriveTeleop command ended.");
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);	
	}
}