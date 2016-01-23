package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;

public interface ShooterSystems {	
	public Motor getMotor();
	public void setMotor(Motor motor);
}
