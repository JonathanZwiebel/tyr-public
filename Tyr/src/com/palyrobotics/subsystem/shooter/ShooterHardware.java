package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

public class ShooterHardware extends ShooterSystems {
	Motor motor = Hardware.Motors.talon(-1);

	@Override
	protected Motor getMotor() {
		return motor;
	}

	@Override
	protected void setMotor(Motor motor) {
		this.motor = motor;
	}
}
