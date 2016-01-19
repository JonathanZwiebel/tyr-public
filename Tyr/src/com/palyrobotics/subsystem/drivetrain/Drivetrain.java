package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Requirable;

import edu.wpi.first.wpilibj.command.Subsystem;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

public class Drivetrain implements Requirable {
	
	private DrivetrainSystems systems;
	private DrivetrainTeleopInterpreter interpreter;
	
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
	
	public void setState(State state) {
		this.state = state;
	}
}
