package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.command.Requirable;

public class ShooterLockingActuatorController implements Requirable {
	ShooterController parent;
	public ShooterLockingActuatorState state;
	
	public enum ShooterLockingActuatorState {
		IDLE,
		LOCK,
		UNLOCK,
		DISABLED
	}
	
	public ShooterLockingActuatorController(ShooterController parent) {
		this.parent = parent;
	}
	
	public void init() {
		state = ShooterLockingActuatorState.IDLE;
	}
	
	public void update() {
		
	}
	
	public void disable() {
		state = ShooterLockingActuatorState.DISABLED;
	}
	
	public void setState(ShooterLockingActuatorState state) {
		if(state != ShooterLockingActuatorState.DISABLED) {
			this.state = state;
		}
	}
	
	public boolean isLocked() {
		return parent.input.getLockDetector().isTriggered();
	}
}
 