package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;

public class ShooterHardware implements ShooterSystems {
	Motor motor = null;
	Solenoid latchSolenoid = null;
	Solenoid pistonSolenoid = null;
	
	@Override
	public Motor getMotor() {
		return motor;
	}

	@Override
	public Solenoid getLatch() {
		return latchSolenoid;
	}
	
	@Override
	public Solenoid getPiston() {
		return pistonSolenoid;
	}
}
