package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

public class ShooterHardware implements ShooterSystems {
	Motor motor = Hardware.Motors.talon(-1);

	@Override
	public Motor getMotor() {
		return motor;
	}

	@Override
	public void setMotor(Motor motor) {
		this.motor = motor;
	}
}
