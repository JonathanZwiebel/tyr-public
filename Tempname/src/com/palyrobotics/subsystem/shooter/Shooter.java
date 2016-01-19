package com.palyrobotics.subsystem.shooter;

import static com.palyrobotics.subsystem.shooter.ShooterConstants.*;

public class Shooter {

	public enum State {
		IDLE,
		SHOOTING,
		RETRACTING,
		RAISING,
		LOWERING
	}
	
	private State state;
	
	public Shooter() {
		
	}
	
	public void init() {
		
	}
	
	public void update() {
		switch(state) {
		case IDLE:
			
			break;
		case SHOOTING:
			
			break;
		case RETRACTING:
			
			break;
		case RAISING:
			
			break;
		case LOWERING:
			
			break;
		}
	}
	
	public void disable() {
		
	}
	
	public void shoot() {
		
	}

	public void retract() {
		
	}
	
	public void raise() {
		
	}
	
	public void lower() {
		
	}
}
