package com.palyrobotics.subsystem.accumulator;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.components.ui.FlightStick;

import com.palyrobotics.robot.InputSystems;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

/**
 * Command that sets up switch reactors to submit commands during teleop
 * Should be called once at the beginning of teleop
 */
public class AccumulatorTeleop extends Command {
	AccumulatorController controller;
	SwitchReactor reactor;
	InputSystems input;
	
	/**
	 * Constructor for AccumulatorTeleop
	 * @param controler the AccumulatorController object
	 * @param input the InputSystems object
	 */
	public AccumulatorTeleop (AccumulatorController controller, InputSystems input) {
		//Constructs the command using the super constructor
		super (controller);
		this.controller = controller;
		reactor = Strongback.switchReactor();
		this.input = input;
	}
	
	/**
	 * Execute method for the AccumulatorTeleop command
	 * Sets up switch reactors for teleop
	 * Completes imediately
	 * @see org.strongback.command.Command#execute()
	 */
	@Override
	public boolean execute() {
		//Accumulates the ball if the button is pressed
		reactor.onTriggered(input.getOperatorStick().getButton(ACCUMULATE_BUTTON),()->Strongback.submit(new IntakeBallAutomatic(controller, input)));
		//Expels the ball when the expel button is pressed
		reactor.onTriggered(input.getOperatorStick().getButton(EXPEL_BUTTON),()->Strongback.submit(new ExpelBall(controller)));
		reactor.onTriggered(input.getOperatorStick().getButton(STOP_BUTTON), ()->Strongback.submit(new StopAccumulator(controller)));
		return true;
	}

}
