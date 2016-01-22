package com.palyrobotics.subsystem.accumulator;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import org.strongback.command.Command;
import org.strongback.components.Motor;

import com.palyrobotics.subsystem.accumulator.AccumulatorController.State;

public class ExpelBall extends Command {
	private AccumulatorController accumulatorController;
	public ExpelBall(AccumulatorController accumulatorController) {
		//Constructs the command using the super constructor
		super(accumulatorController);
		this.accumulatorController = accumulatorController;
	}
	@Override
	public void initialize (){
		this.begin = System.currentTimeMillis();
		accumulatorController.systems.getAccumulatorMotors().setSpeed(-ACCUMULATOR_POWER);
		accumulatorController.setState(State.EJECTING);
	}
	/*
	 *Runs the motors for EXPEL_TIME ms
	 *Executes every 20 ms until EXPEL_TIME ms has passed
	 */
	@Override
	public boolean execute() {
		//Runs the motors so they expel the ball for EXPEL_TIME seconds
		if (System.currentTimeMillis() - begin < EXPEL_TIME){
			return false;
		}
		Strongback.submit(new StopAccumulator(accumulatorController));
		return true;
	}
	
}
