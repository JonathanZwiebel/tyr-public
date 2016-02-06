package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;

public interface ShooterSystems {	
	public Motor getMotor();
	public Solenoid getLatch();	
	public Solenoid getPiston();
}
