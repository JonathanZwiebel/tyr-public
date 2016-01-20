package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Requirable;

import edu.wpi.first.wpilibj.command.Subsystem;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

public class DrivetrainController implements Requirable {
	
	private DrivetrainSystems systems;
	
	public enum State {
		IDLE,
		DRIVING_TELEOP,
		DRIVING_DISTANCE,
		TURNING_ANGLE
	}
	
	private State state;
	
	public DrivetrainController() {

	}
	
	public void init() {
		
	}
	
	public void update() {
		
	}
	
	public void disable() {
		
	}
	
	public void setState(State state) {
		this.state = state;
	}
}
