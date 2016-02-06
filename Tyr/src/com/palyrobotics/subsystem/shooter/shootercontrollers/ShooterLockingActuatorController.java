package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.command.Requirable;

public class ShooterLockingActuatorController implements Requirable {
	ShooterController parent;
	
	public ShooterLockingActuatorController(ShooterController parent) {
		this.parent = parent;
	}
	
	public boolean isLocked() {
		return parent.input.getLockDetector().isTriggered();
	}
	
	public void init() {
		
	}
	
	public void update() {
		
	}
	
	public void disable() {
		
	}
}
 