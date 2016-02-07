package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Requirable;

import com.palyrobotics.robot.InputSystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

import java.util.*;

public class DrivetrainController implements Requirable {
	
	protected InputSystems input;
	protected DrivetrainSystems output;
	
	public enum State {
		IDLE,
		DRIVING_TELEOP,
		DRIVING_DISTANCE,
		TURNING_ANGLE
	}
	
	private State state;
	
	public DrivetrainController(DrivetrainSystems output, InputSystems input) {
		this.input = input;
		this.output = output;
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
	
	public Requirable[] getRequirements() {
		Requirable requirements[] = {this};
		return requirements;
	}
}
