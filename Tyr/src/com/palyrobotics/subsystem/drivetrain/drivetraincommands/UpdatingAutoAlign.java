package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import static com.palyrobotics.robot.RobotConstants.UPDATES_PER_SECOND;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class UpdatingAutoAlign extends Command {

	private DrivetrainController drivetrain;
	private double angle;
	private double zeroedAngle;
	private double gyroAngle;
	private double xDisplacement;
	private double previousAngle;
	private double previousError;
	private NetworkTable table;
	
	public UpdatingAutoAlign(DrivetrainController drivetrain) {
		super(drivetrain);
		this.drivetrain = drivetrain;
	}
	
	@Override
	public void initialize() {

		drivetrain.setDrivetrainState(DrivetrainState.ALIGN_TO_GOAL);
		this.table = NetworkTable.getTable("AutoAlign");
		this.angle = table.getNumber("SkewAngle", this.angle);
		this.previousAngle = angle;
		this.previousError = angle;
		drivetrain.getInput().getGyroscope().reset();
		
	}

	private void zero() {
		this.zeroedAngle = drivetrain.getInput().getGyroscope().getAngle();
	}
	
	/**
	 * Constantly updating new angle from vision instead of fixed angle target.
	 */
	@Override
	public boolean execute() {
		angle = table.getNumber("SkewAngle", this.angle);
		xDisplacement = table.getNumber("xDisplacement", this.xDisplacement);
		
		// If we can't see the goal, turn until we can
		if(angle == 1000) {
			drivetrain.getOutput().getLeftMotor().setSpeed(0.3);
			drivetrain.getOutput().getRightMotor().setSpeed(0.3);
		}
		System.out.println("skew angle: " + angle);
		// If the angle has changed, re zero the gyroscope for a fresh turning
		// loop.
		if (previousAngle != angle) {
			System.out.println("zeroed");
			zero();
		}
		
		this.gyroAngle = drivetrain.getInput().getGyroscope().getAngle() - zeroedAngle;
		
		if (Math.abs(angle - previousAngle) > .1) {
			previousAngle = angle;
		}
			
		// calculate error by using angle to distance
		double error = angle - gyroAngle;

		System.out.println("Skew angle: " + angle);
		System.out.println("Error: " + error);
		
		// finds derivative with 50 updates/second update rate
		double derivative = (error - previousError) * UPDATES_PER_SECOND;

		previousError = error;

		// min and max restrict the speeds between -0.5 and 0.5
		double leftSpeed = Math.max(Math.min(LEFT_ANGLE_P_VALUE * error + LEFT_ANGLE_D_VALUE * derivative, 0.3), -0.3);
		double rightSpeed = Math.max(Math.min(RIGHT_ANGLE_P_VALUE * error + RIGHT_ANGLE_D_VALUE * derivative, 0.3),
				-0.3);

		System.out.println("leftspeed: " + leftSpeed);
		System.out.println("rightspeed: " + rightSpeed);
		System.out.println("Gyro:" + gyroAngle);
		drivetrain.getOutput().getLeftMotor().setSpeed(-leftSpeed);
		drivetrain.getOutput().getRightMotor().setSpeed(rightSpeed);

		xDisplacement = table.getNumber("xDisplacement", this.xDisplacement);
		
		// stops robot when target is reached and robot has slowed within
		// tolerance range
		if (derivative == 0.0 && Math.abs(xDisplacement) < ACCEPTABLE_PIXEL_ERROR && Math.abs(error) < ACCEPTABLE_ANGLE_ERROR) {
			table.putBoolean("Reset", true);
			System.out.println("Conditions met to stop UpdatingAutoAlign. ");
			return true;
		}
		// Panic switch to instantly break out of the command
		if (drivetrain.getInput().getDriveStick().getTrigger().isTriggered()) {
			table.putBoolean("Reset", true);
			return true;
		}
		return false;
	}

	@Override
	public void interrupted() {
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
		this.zeroedAngle = 0.0;
	}

	@Override
	public void end() {
		System.out.println("Interupted and End Called");
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
		this.zeroedAngle = 0;
		drivetrain.setDrivetrainState(DrivetrainState.IDLE);
	}

}
