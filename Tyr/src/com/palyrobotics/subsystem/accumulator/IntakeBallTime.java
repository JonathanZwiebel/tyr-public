package com.palyrobotics.subsystem.accumulator;

import org.strongback.command.Command;
import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;

public class IntakeBallTime extends Command{
	
	private AccumulatorController controller;
	private double begin;
	
	public IntakeBallTime(AccumulatorController controller) {
		super(controller);
		this.controller = controller;
	}
	
	@Override
	public void initialize() {
		this.begin = System.currentTimeMillis();
		controller.setState(AccumulatorState.EJECTING);
	}
	
	@Override
	public boolean execute() {
		//Runs the motors so they expel the ball for EXPEL_TIME seconds
		if (System.currentTimeMillis() - begin < EXPEL_TIME){
			controller.systems.getAccumulatorMotors().setSpeed(-ACCUMULATOR_POWER);
			return false;
		}
		controller.systems.getAccumulatorMotors().setSpeed(0);
		controller.setState(AccumulatorState.IDLE);
		return true;
	}
	
	public void interrupted() {
		controller.systems.getAccumulatorMotors().setSpeed(0);
		controller.setState(AccumulatorState.IDLE);
	}
}
