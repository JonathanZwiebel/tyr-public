package com.palyrobotics.subsystem.accumulator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;

/**
 * Command that stops the accumulator
 */
public class StopAccumulator extends Command{
	
	AccumulatorController controller;
	
	/**
	 * Constructor for the StopAccumulatorCommand
	 * @param controller the AccumulatorController object
	 */
	public StopAccumulator(AccumulatorController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		controller.setState(AccumulatorState.DISABLED);
    	Logger.getLogger("Central").log(Level.INFO, "StopAccumulator initalized.");
	}
	
	/**
	 * Sets the accumulator motor power to 0
	 * Completes imediately
	 */
	@Override
	public boolean execute() {
		controller.systems.getAccumulatorMotors().setSpeed(0);
		controller.setState(AccumulatorState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "StopAccumulator is ending.");
		return true;
	}
	
	@Override
	public void end() {
    	Logger.getLogger("Central").log(Level.INFO, "StopAccumulator ended.");
	}

	@Override
	public void interrupted() {
    	Logger.getLogger("Central").log(Level.INFO, "StopAccumulator interrupted.");
	}
}
