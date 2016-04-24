package com.palyrobotics.subsystem.drivetrain;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.*;

import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

public class DrivetrainController implements Requirable {

	private InputSystems input;
	private DrivetrainSystems output;
	private SwitchReactor reactor;

	public enum DrivetrainState {
		IDLE, 
		DRIVING_TELEOP, 
		DRIVING_DISTANCE, 
		TURNING_ANGLE, 
		SHOOTER_ALIGN, 
		ALIGN_TO_GOAL,
		MOVING_TO_GOAL,
		DISABLED
	}

	private DrivetrainState drivetrainState;

	public DrivetrainController(DrivetrainSystems output, InputSystems input) {
		this.setInput(input);
		this.setOutput(output);
		this.reactor = Strongback.switchReactor();
	}

	/**
	 * Sets the default DrivetrainStates, starts the compressor, and initializes
	 * the switch reactors for different commands.
	 * 
	 * TODO: Add align with new vision input that is passed in array form
	 */
	public void init() {
		drivetrainState = DrivetrainState.IDLE;

		reactor.onTriggered(input.getDriveStick().getButton(Buttons.DRIVETRAIN_HIGH_GEAR_BUTTON),
				() -> Strongback.submit(new HighGear(this)));
		reactor.onTriggered(input.getDriveStick().getButton(Buttons.DRIVETRAIN_LOW_GEAR_BUTTON),
				() -> Strongback.submit(new LowGear(this)));
		reactor.onTriggered(input.getDriveStick().getButton(Buttons.DRIVETRAIN_FORWARD_ORIENTATION_BUTTON),
				() -> TELEOP_ORIENTATION = 1.0);
		reactor.onTriggered(input.getDriveStick().getButton(Buttons.DRIVETRAIN_BREACHER_ORIENTATION_BUTTON),
				() -> TELEOP_ORIENTATION = -1.0);
		reactor.onTriggered(input.getDriveStick().getButton(Buttons.DRIVETRAIN_AUTO_ALIGN_BUTTON), 
				() -> Strongback.submit(new SingleAutoAlign(this, 0.4)));
		reactor.onTriggered(input.getDriveStick().getButton(10),
				() -> Strongback.submit(new DriveTeleop(this, 1.0f)));
	}

	public void update() {
		System.out.println(drivetrainState);
		if (drivetrainState == DrivetrainState.IDLE) {
			if(input.getTurnStick().getButton(Buttons.DRIVETRAIN_PRECISION_TURNING_BUTTON).isTriggered()) {
				Strongback.submit(new DriveTeleop(this, DrivetrainConstants.PRECISION_TURNING_SCALING_FACTOR));
			}
			else if(input.getDriveStick().getButton(Buttons.DRIVETRAIN_THROTTLE_FORWARD_BUTTON).isTriggered()) {
				Strongback.submit(new DriveTeleop(this, DrivetrainConstants.THROTTLE_FORWARD_SCALING_FACTOR));
			}
			Strongback.submit(new DriveTeleop(this, 1.0f));
		}
	}

	public void disable() {
		Strongback.submit(new DrivetrainDisable(this));
	}

	public void setDrivetrainState(DrivetrainState drivetrainState) {
		this.drivetrainState = drivetrainState;
	}

	public DrivetrainState getDrivetrainState() {
		return this.drivetrainState;
	}

	public InputSystems getInput() {
		return input;
	}

	public void setInput(InputSystems input) {
		this.input = input;
	}

	public DrivetrainSystems getOutput() {
		return output;
	}

	public void setOutput(DrivetrainSystems output) {
		this.output = output;
	}
}
