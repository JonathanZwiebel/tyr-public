package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;
import org.strongback.components.AngleSensor;

public class ShooterHardware implements ShooterSystems {
	Motor motor = null;
	AngleSensor armEncoder = null;
	
	@Override
	public Motor getMotor() {
		return motor;
	}

	@Override
	public void setMotor(Motor motor) {
		this.motor = motor;
	}
	
	@Override
	public AngleSensor getArmEncoder() {
		return armEncoder;
	}
}
