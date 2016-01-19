package com.palyrobotics.subsystem.accumulator;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import org.strongback.command.Requirable;

public class Accumulator implements Requirable {

	public enum State {
		IDLE,
		ACCUMULATING,
		EJECTING,
		HOLDING,
		RELEASING
	}
	
	private State state;
	
	public Accumulator() {
		
	}
	
	public void init() {
		
	}
	
	public void update() {
		switch(state) {
		case IDLE:
			
			break;
		case ACCUMULATING:
			
			break;
		case EJECTING:
			
			break;
		case HOLDING:
			
			break;
		case RELEASING:
			
			break;
		}
	}
	
	public void disable() {
		
	}
}
