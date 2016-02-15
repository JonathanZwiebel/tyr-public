package com.palyrobotics.subsystem.accumulator;

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

	/**
	 * Sets the accumulator motor power to 0
	 * Completes imediately
	 */
	@Override
	public boolean execute() {
		controller.systems.getAccumulatorMotors().setSpeed(0);
		controller.setState(AccumulatorState.IDLE);
		return true;
	}
}
