package com.palyrobotics.test.simulated_systems.simulated_components;

import org.strongback.components.AngleSensor;

public class SimulatedEncoder implements AngleSensor {
	
	private double angle;
	
	public SimulatedEncoder() {
		this.angle = 0.0;
	}
	
	@Override
	public AngleSensor zero() {
		// TODO: Implement if we use this anywhere
		return null;
	}

	@Override
	public double getAngle() {
		// TODO: Implement by implementing some sort of records of values from the motor and taking some sort of sum
		return angle;
	}
}
