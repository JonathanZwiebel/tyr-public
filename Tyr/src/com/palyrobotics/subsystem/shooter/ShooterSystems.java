package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;

public abstract class ShooterSystems {	
	protected abstract Motor getMotor();
	protected abstract void setMotor(Motor motor);
	// this will be either a talon (ShooterHardware) or a mock talon (MockShooterHardware)
}
