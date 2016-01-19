package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

public class DrivetrainSystems {
	private Motor leftFrontMotor = Hardware.Motors.talon(0);
	private Motor leftBackMotor = Hardware.Motors.talon(1);
	
	private Motor rightFrontMotor = Hardware.Motors.talon(2);
	private Motor rightBackMotor = Hardware.Motors.talon(3);
	
	public Motor leftMotor = Motor.compose(leftFrontMotor, leftBackMotor);
	public Motor rightMotor = Motor.compose(rightFrontMotor, rightBackMotor);
}
