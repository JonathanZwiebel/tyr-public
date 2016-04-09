package com.palyrobotics.subsystem.accumulator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.Strongback;
import org.strongback.command.Requirable;
import com.palyrobotics.robot.InputSystems;

public class AccumulatorController implements Requirable {

	public enum AccumulatorState {
		IDLE, ACCUMULATING, EJECTING, HOLDING, RELEASING, DISABLED
	}

	private AccumulatorState state;

	private InputSystems robotInput;
	public AccumulatorSystems systems;

	public AccumulatorController(AccumulatorSystems accumulatorSystems, InputSystems robotInput) {
		this.systems = accumulatorSystems;
		this.robotInput = robotInput;
	}

	public void init() {
		if (state != null || state != AccumulatorState.DISABLED) {
			Logger.getLogger("Central").log(Level.SEVERE,
					"AccumulatorState is not null or disabled un init, but is: " + state.toString());
		}
		state = AccumulatorState.IDLE;
		Strongback.submit(new AccumulatorTeleop(this, robotInput));
		Logger.getLogger("Central").log(Level.INFO, "AccumulatorController initalized.");
	}

	public void update() {
		Logger.getLogger("Central").log(Level.FINE, "AccumulatorController updated.");
	}

	public void disable() {
		Strongback.submit(new StopAccumulator(this));
		Logger.getLogger("Central").log(Level.INFO, "AccumulatorController ended.");
	}

	public void setState(AccumulatorState state) {
		this.state = state;
	}

	public AccumulatorState getState() {
		return state;
	}
}
