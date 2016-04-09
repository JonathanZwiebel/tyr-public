package com.palyrobotics.subsystem.accumulator;

import org.strongback.command.Command;
import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;

/**
 * Command that accumulates the ball by running the motors inward for a constant amount of time
 */
public class IntakeBallTime extends Command{
	
	private AccumulatorController controller;
	private double begin;
	
	/**
	 * Constructor for the IntakeBallTime command
	 * @param controller the AccumulatorController object
	 */
	public IntakeBallTime(AccumulatorController controller) {
		super(controller);
		this.controller = controller;
	}
	
	/**
	 * Initializes the IntakeBallTime command by seting the state to ejecting and setting begin to the current time
	 * @see org.strongback.command.Command#initialize()
	 */
	@Override
	public void initialize() {
		this.begin = System.currentTimeMillis();
		controller.setState(AccumulatorState.ACCUMULATING);
    	Logger.getLogger("Central").log(Level.INFO, "IntakeBallTime initalized.");
	}
	
	/**
	 * Sets the motor power to expel the ball
	 * Continues to execute until EXPEL_TIME has passed since initialize was called
	 * Once EXPEL_TIME has passed, it terminates
	 * @see org.strongback.command.Command#execute()
	 */
	@Override
	public boolean execute() {
		//Runs the motors so they expel the ball for EXPEL_TIME seconds
		if (System.currentTimeMillis() - begin < EXPEL_TIME){
			controller.systems.getAccumulatorMotors().setSpeed(-ACCUMULATOR_POWER);
	    	Logger.getLogger("Central").log(Level.FINE, "IntakeBallTime is continuing.");
			return false;
		}
    	Logger.getLogger("Central").log(Level.INFO, "IntakeBallTime is ending.");
		return true;
	}
	
	/**
	 * Called when the command is interrupted
	 * Sets the speed of the accumulator motors to 0 and the state of the AccumulatorController to idle
	 * @see org.strongback.command.Command#interrupted()
	 */
	public void interrupted() {
		controller.systems.getAccumulatorMotors().setSpeed(0);
		controller.setState(AccumulatorState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "IntakeBallTime ended.");
	}
	
	public void end() {
		controller.systems.getAccumulatorMotors().setSpeed(0);
		controller.setState(AccumulatorState.IDLE);
	}
}
