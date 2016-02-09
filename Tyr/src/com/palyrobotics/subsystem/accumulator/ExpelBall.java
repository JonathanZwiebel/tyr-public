package com.palyrobotics.subsystem.accumulator;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import org.strongback.Strongback;
import org.strongback.command.Command;

import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;

public class ExpelBall extends Command {
	private AccumulatorController accumulatorController;
	private double begin;
	
	public ExpelBall(AccumulatorController accumulatorController) {
		// Constructs the command using the super constructor
		super(accumulatorController);
		this.accumulatorController = accumulatorController;
	}

	/*
	 * Runs the motors for EXPEL_TIME ms Executes every 20 ms until EXPEL_TIME
	 * ms has passed
	 */
	@Override
	public void initialize() {
		this.begin = System.currentTimeMillis();
		accumulatorController.systems.getAccumulatorMotors().setSpeed(-ACCUMULATOR_POWER);
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
			return false;
		}
		accumulatorController.systems.getAccumulatorMotors().setSpeed(0);
		accumulatorController.setState(AccumulatorState.IDLE);
		return true;
	}
	
	public void interrupted() {
		accumulatorController.systems.getAccumulatorMotors().setSpeed(0);
		accumulatorController.setState(AccumulatorState.IDLE);
	}
	
}