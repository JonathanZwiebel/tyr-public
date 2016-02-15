package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.*;

<<<<<<< HEAD
public interface DrivetrainSystems {
	public abstract Motor getLeftMotor();
	public abstract Motor getRightMotor();
	public abstract Solenoid getSolenoid();
=======
public class DrivetrainSystems {
	private Motor leftFrontMotor = Hardware.Motors.talon(0);
	private Motor leftBackMotor = Hardware.Motors.talon(1);

	private Motor rightFrontMotor = Hardware.Motors.talon(2);
	private Motor rightBackMotor = Hardware.Motors.talon(3);

	public Motor leftMotor = Motor.compose(leftFrontMotor, leftBackMotor);
	public Motor rightMotor = Motor.compose(rightFrontMotor, rightBackMotor);
>>>>>>> 6e7dc8a... reformatted code
}
