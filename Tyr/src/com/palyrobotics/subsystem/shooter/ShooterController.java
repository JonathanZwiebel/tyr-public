package com.palyrobotics.subsystem.shooter;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

public class ShooterController implements Requirable {
	public ShooterSystems systems;
	// This will be either a ShooterSystems object or a MockShooterSystems object
	
	public enum ShooterState {
		IDLE,
		UNLOCKED,
		SHOOTSEQUENCE,
		DISABLED
	}
	
	public ShooterState state;
	
	public ShooterController(ShooterSystems systems) {
		this.systems = systems;
	}
	
	public void init() {
		state = ShooterState.IDLE;
	}
	
	public void update() {
		if(state == ShooterState.UNLOCKED) {
			Strongback.submit(new UncheckedTeleopCommand(this));
		}
	}
	
	public void disable() {
		state = ShooterState.DISABLED;
	}
}
