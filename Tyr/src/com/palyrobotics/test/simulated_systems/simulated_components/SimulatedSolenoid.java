package com.palyrobotics.test.simulated_systems.simulated_components;

import org.strongback.components.Solenoid;

public class SimulatedSolenoid implements Solenoid {
	
	private Direction direction;
	
	public SimulatedSolenoid(Direction initialDirection) {
		this.direction = initialDirection;
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public Solenoid extend() {
		this.direction = Direction.EXTENDING;
		return this;
	}

	@Override
	public Solenoid retract() {
		this.direction = Direction.RETRACTING;
		return this;
	}
}