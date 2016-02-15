package com.palyrobotics.subsystem.accumulator;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import org.strongback.command.Requirable;
import org.strongback.command.Command;
import org.strongback.components.Motor;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;

public class IntakeBall extends Command {
	private AccumulatorController accumulatorController;
	private InputSystems input;

	public IntakeBall(AccumulatorController accumulatorController, InputSystems input) {
		super(accumulatorController);
		this.accumulatorController = accumulatorController;
		this.input = input;
	}

	public void initialize() {
		accumulatorController.systems.getAccumulatorMotors().setSpeed(ACCUMULATOR_POWER);
		accumulatorController.setState(AccumulatorState.ACCUMULATING);
	}

	/**
	 * Runs the intake until the photogate is triggered Executes every 20 ms
	 * until the photogate is triggered
	 */
	@Override
	public boolean execute() {
		if (input.getAccumulatorFilledLimitSensor().isTriggered()) {
			accumulatorController.systems.getAccumulatorMotors().setSpeed(0);
			accumulatorController.setState(AccumulatorState.HOLDING);
			return true;
		}
		return false;
	}

}
