package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;

public class ShooterHardware implements ShooterSystems {
	Motor motor = null;

	@Override
	public Motor getMotor() {
		return motor;
	}

	@Override
	public void setMotor(Motor motor) {
		this.motor = motor;
	}
}
