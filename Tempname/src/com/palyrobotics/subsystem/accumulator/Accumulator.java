package com.palyrobotics.subsystem.accumulator;

public class Accumulator {

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
	
	public void accumulate() {
		
	}

	public void eject() {
		
	}
	
	public void hold() {
		
	}
	
	public void release() {
		
	}
}
