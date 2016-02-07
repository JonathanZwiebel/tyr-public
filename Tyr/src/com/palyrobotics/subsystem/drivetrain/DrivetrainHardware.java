package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

public class DrivetrainHardware implements DrivetrainSystems {
	private Motor leftFrontMotor = Hardware.Motors.talonSRX(0, ENCODER_PULSES_PER_DEGREE);
	private Motor leftBackMotor = Hardware.Motors.talonSRX(1, ENCODER_PULSES_PER_DEGREE);
	
	private Motor rightFrontMotor = Hardware.Motors.talonSRX(2, ENCODER_PULSES_PER_DEGREE);
	private Motor rightBackMotor = Hardware.Motors.talonSRX(3, ENCODER_PULSES_PER_DEGREE);
	
	public Motor leftMotor = Motor.compose(leftFrontMotor, leftBackMotor);
	public Motor rightMotor = Motor.compose(rightFrontMotor, rightBackMotor);
	
	@Override
	public Motor getLeftMotor() {
		return leftMotor;
	}
	
	@Override
	public Motor getRightMotor() {
		return rightMotor;
	}
}
