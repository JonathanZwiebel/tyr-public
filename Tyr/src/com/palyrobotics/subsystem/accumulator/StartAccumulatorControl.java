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
		this.controller = controller;
		reactor = Strongback.switchReactor();
		joystick = this.joystick;
	}
	@Override
	public boolean execute() {
		reactor.onTriggered(joystick.getButton(ACCUMULATE_BUTTON),()->Strongback.submit(new IntakeBall(controller)));
		reactor.onUntriggered(joystick.getButton(ACCUMULATE_BUTTON),()->Strongback.submit(new IntakeBall(controller)));
		reactor.onTriggered(joystick.getButton(EXPEL_BUTTON),()->Strongback.submit(new ExpelBall(controller)));
		reactor.onUntriggered(joystick.getButton(EXPEL_BUTTON),()->Strongback.submit(new IntakeBall(controller)));
		return false;
	}

}
