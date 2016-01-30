package com.palyrobotics.subsystem.accumulator;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

<<<<<<< Upstream, based on yellow-master
public class AccumulatorHardware implements AccumulatorSystems {
	private static final Integer CHANNEL = (Integer) null;
	
	private Motor leftMotor = Hardware.Motors.talon(CHANNEL);
	private Motor rightMotor = Hardware.Motors.talon(CHANNEL);
	
=======
public class AccumulatorHardware implements AccumulatorSystems{
	private Motor leftMotor = Hardware.Motors.talon(6);
	private Motor rightMotor = Hardware.Motors.talon(7);
>>>>>>> 8076b8a Fixed accumulator formatting
	//TODO: may need to be the other way around depending on hardware.
	private Motor acculumatorMotors = Motor.compose(leftMotor.invert(), rightMotor);
	
	@Override
	public Motor getAccumulatorMotors() {
		return acculumatorMotors;
	}
}