package com.palyrobotics.subsystem.accumulator;

import org.strongback.components.Motor;

public class AccumulatorSystems {
	private Motor leftMotor = Hardware.Motors.talon(6);
	private Motor rightMotor = Hardware.Motors.talon(7);
	public Motor acculumatorMotors = Motor.compose(leftMotor.invert(), rightMotor);
}
