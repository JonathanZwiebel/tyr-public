package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;
import org.strongback.components.AngleSensor;

public interface ShooterSystems {	
	public Motor getMotor();
	public void setMotor(Motor motor);
	public AngleSensor getArmEncoder();
}
