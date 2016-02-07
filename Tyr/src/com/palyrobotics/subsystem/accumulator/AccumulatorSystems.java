package com.palyrobotics.subsystem.accumulator;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

public class AccumulatorSystems {
	private Motor leftMotor = Hardware.Motors.talon(6);
	private Motor rightMotor = Hardware.Motors.talon(7);
}
