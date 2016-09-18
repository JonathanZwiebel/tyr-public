package com.palyrobotics.test.simulated_systems.simulated_components;

import edu.wpi.first.wpilibj.AnalogGyro;

public class SimulatedGyroscope extends AnalogGyro {
	
	private double angle;
	
	public SimulatedGyroscope() {
		super(0);
		this.angle = 0.0;
	}

	@Override
	public double getAngle() {
		// TODO: Implement by implementing some sort of records of values from the motor and taking some sort of sum
		return angle;
	}
}
