package com.palyrobotics.subsystem.accumulator;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.components.ui.FlightStick;
import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

public class StartAccumulatorControl extends Command{
	AccumulatorController controller;
	SwitchReactor reactor;
	FlightStick joystick;
	public StartAccumulatorControl (AccumulatorController controller, FlightStick joystick) {
		//Constructs the command using the super constructor
		super (controller);
		this.controller = controller;
		reactor = Strongback.switchReactor();
		joystick = this.joystick;
	}
	@Override
	public boolean execute() {
		//Accumulates the ball if the button is pressed
		reactor.whileTriggered(joystick.getButton(ACCUMULATE_BUTTON),()->Strongback.submit(new IntakeBall(controller)));
		reactor.whileUntriggered(joystick.getButton(ACCUMULATE_BUTTON),()->Strongback.submit(new StopAccumulator(controller)));
		//Expels the ball when the button is not pressed
		reactor.whileTriggered(joystick.getButton(EXPEL_BUTTON),()->Strongback.submit(new ExpelBall(controller)));
		reactor.whileUntriggered(joystick.getButton(EXPEL_BUTTON),()->Strongback.submit(new StopAccumulator(controller)));
		return false;
	}

}
