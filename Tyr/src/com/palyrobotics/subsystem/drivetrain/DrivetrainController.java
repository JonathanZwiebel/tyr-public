package com.palyrobotics.subsystem.drivetrain;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.InputSystems;


public class DrivetrainController implements Requirable {
	
	protected InputSystems input;
	protected DrivetrainSystems output;
	private SwitchReactor toggle;
	
	public enum DrivetrainState {
		IDLE,
		DRIVING_TELEOP,
		DRIVING_DISTANCE,
		TURNING_ANGLE
	}
	
	public enum GearState {
		LOWERING_GEAR,
		RAISING_GEAR
	}
	
	public GearState gearState;
	
	private DrivetrainState drivetrainState;
	
	public DrivetrainController(DrivetrainSystems output, InputSystems input) {
		this.input = input;
		this.output = output;
	}
	
	/**
	 * Sets the default DrivetrainStates, starts the compressor, and initializes toggle. 
	 */
	public void init() {
		gearState = GearState.LOWERING_GEAR;
		drivetrainState = DrivetrainState.IDLE;
		output.getCompressor().start();
		this.toggle = Strongback.switchReactor();
	}
	
	public void update() {
		if(drivetrainState == DrivetrainState.IDLE) {
			Strongback.submit(new DriveTeleop(this));
		}
		
		//toggle system for raising and lowering gears.
		toggle.onTriggered(input.getOperatorStick().getTrigger(), ()-> Strongback.submit(new ToggleGears(this, gearState)));
	}
	
	public void disable() {
		
	}
	
	public void setDrivetrainState(DrivetrainState drivetrainState) {
		this.drivetrainState = drivetrainState;
	}
}
