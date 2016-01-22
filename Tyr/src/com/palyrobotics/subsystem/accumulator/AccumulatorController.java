package com.palyrobotics.subsystem.accumulator;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;
import static com.palyrobotics.robot.RobotInput.*;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.RobotInput;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.State;

public class AccumulatorController implements Requirable {
	public enum State {
		IDLE,
		ACCUMULATING,
		EJECTING,
		HOLDING,
		RELEASING
	}
	private State state;
	
	public AccumulatorSystems systems;
	
	private StartAccumulatorControl troutAccumulator;
	
	public AccumulatorController(AccumulatorSystems accumulatorSystems) {
		this.systems = accumulatorSystems;
	
	public void init() {
		state = State.IDLE;
	}
	
	public void update() {
		Strongback.submit(new StartAccumulatorControl(this, RobotInput.operatorStick));
		switch (state){
		case ACCUMULATING:
			break;
		case EJECTING:
			break;
		case HOLDING:
			break;
		case IDLE:
			break;
		case RELEASING:
			break;
		default:
			break;
		}
	}
	
	public void disable() {
		Strongback.submit(new StopAccumulator(this));
	}
	
	public void setState(State state) {
		this.state = state;
	}

}
