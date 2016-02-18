package com.palyrobotics.subsystem.accumulator;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import org.strongback.Strongback;
import org.strongback.command.Command;

import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;

/**
 * Command to expel the ball
 * Runs the motors outward for EXPEL_TIME number of ms
 */
public class ExpelBall extends Command {
	private AccumulatorController accumulatorController;
	private double begin;
	
	/**
	 *Constructor for the ExpelBall command
	 *@param accumulatorController the AccumulatorController object 
	 */
	public ExpelBall(AccumulatorController accumulatorController) {
		// Constructs the command using the super constructor
		super(accumulatorController);
		this.accumulatorController = accumulatorController;
	}
	
	/**
	 * Initializes the ExpelBall command
	 * Sets begin to the system time and the state to ejecting
	 * @see org.strongback.command.Command#initialize()
	 */
	@Override
	public void initialize() {
		this.begin = System.currentTimeMillis();
		accumulatorController.setState(AccumulatorState.EJECTING);
	}
	/*
	 *Runs the motors for EXPEL_TIME ms
	 *Executes every 20 ms until EXPEL_TIME ms has passed
	 */
	@Override
	public boolean execute() {
		// Runs the motors so they expel the ball for EXPEL_TIME seconds
		if (System.currentTimeMillis() - begin < EXPEL_TIME) {
			accumulatorController.systems.getAccumulatorMotors().setSpeed(-ACCUMULATOR_POWER);
			return false;
		}
		return true;
	}
	
	/**
	 * Called when the command is interrupted
	 * Sets the speed to 0 and the state to idle
	 * @see org.strongback.command.Command#interrupted()
	 */
	public void interrupted() {
		accumulatorController.systems.getAccumulatorMotors().setSpeed(0);
		accumulatorController.setState(AccumulatorState.IDLE);
	}
	
	public void end() {
		accumulatorController.systems.getAccumulatorMotors().setSpeed(0);
		accumulatorController.setState(AccumulatorState.IDLE);
	}
	
}