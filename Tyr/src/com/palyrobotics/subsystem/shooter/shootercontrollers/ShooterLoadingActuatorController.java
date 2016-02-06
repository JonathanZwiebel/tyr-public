package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.command.Requirable;

public class ShooterLoadingActuatorController implements Requirable {
	ShooterController parent;
	
	public ShooterLoadingActuatorController(ShooterController parent) {
		this.parent = parent;
	}
	
	public boolean isFullyRetracted() {
		 return parent.input.getArmPistonDetector().isTriggered();		 
	}
	
	public void init() {
		
	}
	
	public void update() {
		
	}
	
	public void disable() {
		
	}
}
