package com.palyrobotics.subsystem.drivetrain;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.DriveDistance;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.DriveTeleop;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.DrivetrainDisable;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.ShooterAlign;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.ToggleGears;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.TurnAngle;

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

		reactor.onTriggered(input.getDriveStick().getTrigger(), () -> Strongback.submit(new ToggleGears(this)));
		reactor.onTriggered(input.getDriveStick().getButton(DRIVING_DISTANCE_BUTTON),
				() -> Strongback.submit(new DriveDistance(this, STANDARD_DRIVE_DISTANCE)));
		reactor.onTriggered(input.getDriveStick().getButton(TURNING_LEFT_BUTTON),
				() -> Strongback.submit(new TurnAngle(this, STANDARD_TURN_ANGLE)));
		reactor.onTriggered(input.getDriveStick().getButton(TURNING_RIGHT_BUTTON),
				() -> Strongback.submit(new TurnAngle(this, -STANDARD_TURN_ANGLE)));
	}

	public void update() {
		if (drivetrainState == DrivetrainState.IDLE) {
			Strongback.submit(new DriveTeleop(this));
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
