package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.command.Requirable;

public class ShooterLoadingActuatorController implements Requirable {
	ShooterController parent;
	public ShooterLoadingActuatorState state;
	
	public enum ShooterLoadingActuatorState {
		IDLE,
		EXTEND,
		RETRACT,
		DISABLED
	}
	
	public ShooterLoadingActuatorController(ShooterController parent) {
		this.parent = parent;
	}
	
	public void init() {
		state = ShooterLoadingActuatorState.IDLE;
	}
	
	public void update() {

	}
	
	public void disable() {
		state = ShooterLoadingActuatorState.DISABLED;		
	}
	
	public void setState(ShooterLoadingActuatorState state) {
		if(state != ShooterLoadingActuatorState.DISABLED) {
			this.state = state;
		}
	}
	
	public boolean isFullyRetracted() {
		 return parent.input.getArmPistonDetector().isTriggered();		 
	}
}
