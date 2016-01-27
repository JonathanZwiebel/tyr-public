package com.palyrobotics.subsystem.accumulator;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.components.ui.FlightStick;

import com.palyrobotics.robot.InputSystems;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

//TODO: perhaps rename class to teleopControl or something
public class AccumulatorTeleop extends Command{
	AccumulatorController controller;
	SwitchReactor reactor;
	InputSystems input;
	
	public AccumulatorTeleop (AccumulatorController controller, InputSystems input) {
		//Constructs the command using the super constructor
		super (controller);
		this.controller = controller;
		reactor = Strongback.switchReactor();
		this.input = input;
	}
	@Override
	public boolean execute() {
		//Accumulates the ball if the button is pressed
		reactor.onTriggered(input.getOperatorStick().getButton(ACCUMULATE_BUTTON),()->Strongback.submit(new IntakeBall(controller, input)));
		
		//Expels the ball when the expel button is pressed
		reactor.onTriggered(input.getOperatorStick().getButton(EXPEL_BUTTON),()->Strongback.submit(new ExpelBall(controller)));
				
		//TODO: whenever the expel button is not pressed, even if the accumulate button is pressed it will not do much/anything, as stop is last
		//TODO: and as such overrides the others. perhaps look into reworking button press scheme.
		//TODO: also this command never finishes, it always returns false. it does end when another command is called that requires controller however.
		
		return false;
	}

}
