package com.palyrobotics.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain {
	
	public enum State {
		IDLE,
		DRIVING_TELEOP,
		DRIVING_DISTANCE,
		TURNING_ANGLE
	}
	
	private State state;
	
	public Drivetrain() {
		
	}
	
	public void init() {
		
	}
	
	public void update() {
		switch(state) {
		case IDLE:
			
			break;
		case DRIVING_TELEOP:
			
			break;
		case DRIVING_DISTANCE:
			
			break;
		case TURNING_ANGLE:
			
			break;
		}
	}
	
	public void disable() {
		
	}
	
	public void driveDistance(double distance) {
		
	}

	public void turnAngle(double angle) {
		
	}
}
