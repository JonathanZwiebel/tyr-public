package com.palyrobotics.subsystem.shooter;

import static com.palyrobotics.subsystem.shooter.ShooterConstants.*;

import org.strongback.command.Requirable;

public class ShooterController implements Requirable {

	public enum State {
		IDLE,
		SHOOTING,
		RETRACTING,
		RAISING,
		LOWERING
	}
	
	private State state;
	
	public ShooterController() {
		
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
}
