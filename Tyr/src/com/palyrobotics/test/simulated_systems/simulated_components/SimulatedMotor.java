package com.palyrobotics.test.simulated_systems.simulated_components;

import org.strongback.components.Motor;

public class SimulatedMotor implements Motor {
	
	private double speed;
	
	public SimulatedMotor() {
		this.speed = 0.0;
	}
	
	@Override
	public void stop() {
		this.speed = 0.0;
	}

	@Override
	public double getSpeed() {
		return this.speed;
	}

	@Override
	public Motor setSpeed(double speed) {
		this.speed = speed;
		return null;
	}
}