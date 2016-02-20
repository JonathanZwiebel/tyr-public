package com.palyrobotics.subsystem.accumulator;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import com.palyrobotics.robot.Ports;

public class AccumulatorHardware implements AccumulatorSystems {
	private Motor leftMotor = Hardware.Motors.talonSRX(Ports.ACCUMULATOR_LEFT_TALON_CHANNEL);
	private Motor rightMotor = Hardware.Motors.talonSRX(Ports.ACCUMULATOR_RIGHT_TALON_CHANNEL);
	
	//TODO: may need to be the other way around depending on hardware.
	private Motor accumulatorMotors = Motor.compose(leftMotor.invert(), rightMotor);

	@Override
	public Motor getAccumulatorMotors() {
		return accumulatorMotors;
	}
}