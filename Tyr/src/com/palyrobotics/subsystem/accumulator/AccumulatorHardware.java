package com.palyrobotics.subsystem.accumulator;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

public class AccumulatorHardware implements AccumulatorSystems {
	private static final Integer CHANNEL = (Integer) null;
	
	private Motor leftMotor = Hardware.Motors.talon(CHANNEL);
	private Motor rightMotor = Hardware.Motors.talon(CHANNEL);
	
	//TODO: may need to be the other way around depending on hardware.
	private Motor accumulatorMotors = Motor.compose(leftMotor.invert(), rightMotor);

	@Override
	public Motor getAccumulatorMotors() {
		return accumulatorMotors;
	}
}