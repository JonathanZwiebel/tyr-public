package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.command.Requirable;

public class ShooterArmController implements Requirable {
	ShooterController parent;
	public ShooterArmState state;
	
	public enum ShooterArmState {
		IDLE,
		LOCKED,
		UNLOCKED
	}
		
	public ShooterArmController(ShooterController parent) {
		this.parent = parent;
	}
	
	public void init() {
		state = ShooterArmState.IDLE;
	}
	
	public void update() {
		
	}
	
	public void disable() {
		state = ShooterArmState.IDLE;
	}
}
