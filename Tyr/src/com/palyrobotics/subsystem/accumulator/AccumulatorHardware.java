package com.palyrobotics.subsystem.accumulator;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

public interface AccumulatorHardware {

public class AccumulatorHardware implements AccumulatorSystems{
	private Motor leftMotor = Hardware.Motors.talon(6);
	private Motor rightMotor = Hardware.Motors.talon(7);
	
	//TODO: may need to be the other way around depending on hardware.
	private Motor acculumatorMotors = Motor.compose(leftMotor.invert(), rightMotor);
	@Override
	public Motor getAccumulatorMotors() {
		return acculumatorMotors;
	}
}
