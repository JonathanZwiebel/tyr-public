package com.palyrobotics.subsystem.accumulator;

import org.strongback.Strongback;
import org.strongback.command.Requirable;
import com.palyrobotics.robot.InputSystems;

public class AccumulatorController implements Requirable {

	public enum AccumulatorState {
		IDLE, ACCUMULATING, EJECTING, HOLDING, RELEASING
	}

	private AccumulatorState state;
	private InputSystems robotInput;
	public AccumulatorSystems systems;

	public AccumulatorController(AccumulatorSystems accumulatorSystems, InputSystems robotInput) {
		this.systems = accumulatorSystems;
		this.robotInput = robotInput;
	}

	public void init() {
		state = AccumulatorState.IDLE;
	}

	public void update() {
		Strongback.submit(new AccumulatorTeleop(this, robotInput));
	}

	public void disable() {
		Strongback.submit(new StopAccumulator(this));
	}

	public void setState(AccumulatorState state) {
		this.state = state;
	}

}
