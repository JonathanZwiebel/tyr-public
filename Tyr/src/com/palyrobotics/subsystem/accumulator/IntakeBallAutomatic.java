package com.palyrobotics.subsystem.accumulator;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import org.strongback.command.Command;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;

/**
 * Command to accumulate the ball using the photogate
 * Runs the motors inwards while the photogate is not triggered
 * Stops the motors when the photogate is triggerd
 */
public class IntakeBallAutomatic extends Command {
	private AccumulatorController accumulatorController;
	private InputSystems input;
	
	/**
	 * Constructor for the IntakeBallAutomatic command
	 * @param accumulatorController the AccumulatorController object
	 * @param input the InputSystems object
	 */
	public IntakeBallAutomatic(AccumulatorController accumulatorController, InputSystems input) {
		super(accumulatorController);
		this.accumulatorController = accumulatorController;
		this.input = input;
	}
	/**
	 * Initializes the IntakeBallAutomatic command
	 * Sets the state to accumulating
	 * @see org.strongback.command.Command#initialize()
	 */
	public void initialize() {
		accumulatorController.setState(AccumulatorState.ACCUMULATING);
	}

	/**
	 * Runs the accumulator until the photogate is triggered
	 * Executes every 20 ms until the photogate is triggered
	 */
	@Override
	public boolean execute() {
		if (input.getAccumulatorFilledLimitSensor().isTriggered()) {
			return true;
		}
		accumulatorController.systems.getAccumulatorMotors().setSpeed(ACCUMULATOR_POWER);
		return false;
	}
	
	/*
	 * Called if the command is interrupted
	 * Sets the state to idle and the speed to 0
	 * @see org.strongback.command.Command#interrupted()
	 */
	public void interrupted() {
		accumulatorController.systems.getAccumulatorMotors().setSpeed(0);
		accumulatorController.setState(AccumulatorState.IDLE);
	}
	
	public void end() {
		accumulatorController.systems.getAccumulatorMotors().setSpeed(0);
		accumulatorController.setState(AccumulatorState.HOLDING);
	}

}
