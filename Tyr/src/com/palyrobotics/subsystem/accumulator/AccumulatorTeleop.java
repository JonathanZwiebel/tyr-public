package com.palyrobotics.subsystem.accumulator;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;

import com.palyrobotics.robot.InputSystems;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

public class AccumulatorTeleop extends Command {
	AccumulatorController controller;
	SwitchReactor reactor;
	InputSystems input;

	public AccumulatorTeleop(AccumulatorController controller, InputSystems input) {
		// Constructs the command using the super constructor
		super(controller);
		this.controller = controller;
		this.input = input;
		reactor = Strongback.switchReactor();
	}

	/**
	 * Submits commands to Strongback based on button input TODO: Turn switch
	 * reactors off when teleop is left
	 */
	@Override
	public boolean execute() {
		// Accumulates the ball if the button is pressed
		reactor.onTriggered(input.getOperatorStick().getButton(ACCUMULATE_BUTTON), () -> Strongback.submit(new IntakeBall(controller, input)));

		// Expels the ball when the expel button is pressed
		reactor.onTriggered(input.getOperatorStick().getButton(EXPEL_BUTTON), () -> Strongback.submit(new ExpelBall(controller)));
		reactor.onTriggered(input.getOperatorStick().getButton(STOP_BUTTON), () -> Strongback.submit(new StopAccumulator(controller)));
		return true;
	}

}