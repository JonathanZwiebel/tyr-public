package com.palyrobotics.subsystem.drivetrain;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.InputSystems;

public class DrivetrainController implements Requirable {

	protected InputSystems input;
	protected DrivetrainSystems output;

	public enum DrivetrainState {
		IDLE, 
		DRIVING_TELEOP, 
		DRIVING_DISTANCE, 
		TURNING_ANGLE, 
		SHOOTER_ALIGN
	}

	// TODO: Get switch reactors working so this can be used. 
	
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
	 * Sets the default DrivetrainStates, starts the compressor, and initializes
	 * toggle.
	 */
	public void init() {
		gearState = GearState.LOWERING_GEAR;
		drivetrainState = DrivetrainState.IDLE;
		output.getCompressor().start();
	}

	public void update() {
		if (drivetrainState == DrivetrainState.IDLE) {
			Strongback.submit(new DriveTeleop(this));
		}
		if(input.getDriveStick().getTrigger().isTriggered()) {
			Strongback.submit(new ToggleGears(this, true));
		} else {
			Strongback.submit(new ToggleGears(this, false));
		}
	}

	public void disable() {

	}

	public void setDrivetrainState(DrivetrainState drivetrainState) {
		this.drivetrainState = drivetrainState;
	}
	
	public DrivetrainState getDrivetrainState() {
		return this.drivetrainState;
	}
	
	public Requirable[] getRequirements() {
		Requirable requirements[] = {this};
		return requirements;
	}
}
