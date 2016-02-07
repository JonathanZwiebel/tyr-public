package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.*;

import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

/**
 * The class representing the teleop driving command
 */
public class DriveTeleop extends Command {

	/** The drivetrain controller reference of this driving command **/
	private DrivetrainController drivetrain;
	
	/**
	 * Initializes command with the given drivetrain controller reference
	 * 
	 * @param drivetrain the drivetrain controller operated by this command
	 */
	public DriveTeleop(DrivetrainController drivetrain) {
		super(drivetrain);
		this.drivetrain = drivetrain;
	}
	
	/**
	 * Called at the beginning of the command, setting the DrivetrainState of the referenced drivetrain to teleop driving
	 */
	@Override
	public void initialize() {
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_TELEOP);
	}
	
	/**
	 * Executes continuously while the command is in action, computes speeds from joysticks and sends to talons
	 */
	@Override
	public boolean execute() {		
		double forwardSpeed = drivetrain.input.getDriveStick().getPitch().read();
		double turnSpeed = drivetrain.input.getTurnStick().getRoll().read();
		
		double leftTargetSpeed = -(forwardSpeed - turnSpeed);
		double rightTargetSpeed = (forwardSpeed + turnSpeed);
		
		double leftError = leftTargetSpeed - drivetrain.output.getLeftMotor().getSpeed();
		double rightError = rightTargetSpeed - drivetrain.output.getRightMotor().getSpeed();
		
		double leftDiff = Math.max(Math.min(leftError, MAX_TELEOP_ACCELERATION), -MAX_TELEOP_ACCELERATION);
		double rightDiff = Math.max(Math.min(rightError, MAX_TELEOP_ACCELERATION), -MAX_TELEOP_ACCELERATION);
		
		drivetrain.output.getLeftMotor().setSpeed(drivetrain.output.getLeftMotor().getSpeed() + leftDiff);
		drivetrain.output.getRightMotor().setSpeed(drivetrain.output.getRightMotor().getSpeed() + rightDiff);
		
		return false;
	}
	
	/**
	 * Sets the motors to zero speed and DrivetrainState to IDLE when drive teleop is interrupted
	 */
	@Override
	public void interrupted() {
		drivetrain.output.getLeftMotor().setSpeed(0.0);
		drivetrain.output.getRightMotor().setSpeed(0.0);
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}
}