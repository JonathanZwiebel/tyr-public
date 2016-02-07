package com.palyrobotics.subsystem.accumulator;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import org.strongback.command.Requirable;

public class AccumulatorController implements Requirable {

	public enum State {
		IDLE,
		ACCUMULATING,
		EJECTING,
		HOLDING,
		RELEASING
	}
	
	private State state;
	
	public AccumulatorController() {
		
	}
	
	public void init() {
		
	}
	
	public void update() {
		
	}
	
	public void disable() {
		
	}
}
