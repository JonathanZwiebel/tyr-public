package com.palyrobotics.subsystem.drivetrain;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.InputSystems;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

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

	private GearState gearState;
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
		
		Strongback.switchReactor().onTriggered(input.getDriveStick().getTrigger(), () -> Strongback.submit(new ToggleGears(this)));
		Strongback.switchReactor().onTriggered(input.getDriveStick().getButton(DRIVING_DISTANCE_BUTTON), () -> Strongback.submit(new DriveDistance(this, 50.0)));
		Strongback.switchReactor().onTriggered(input.getDriveStick().getButton(TURNING_LEFT_BUTTON), () -> Strongback.submit(new TurnAngle(this, 10.0)));
		Strongback.switchReactor().onTriggered(input.getDriveStick().getButton(TURNING_RIGHT_BUTTON), () -> Strongback.submit(new TurnAngle(this, -10.0)));
		Strongback.switchReactor().onTriggered(input.getDriveStick().getButton(SHOOTER_ALIGN_BUTTON), () -> Strongback.submit(new ShooterAlign(this)));
	}

	public void disable() {

	}

	public void setDrivetrainState(DrivetrainState drivetrainState) {
		this.drivetrainState = drivetrainState;
	}
	
	public void setGearState(GearState gearState) {
		this.gearState = gearState;
	}
	
	public DrivetrainState getDrivetrainState() {
		return this.drivetrainState;
	}
	
	public GearState getGearState() {
		return this.gearState;
	}
}
