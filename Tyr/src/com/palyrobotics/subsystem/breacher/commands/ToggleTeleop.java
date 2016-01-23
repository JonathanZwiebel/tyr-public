package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.Strongback;
import org.strongback.command.Command;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.BreacherState;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class ToggleTeleop extends Command {

	private InputSystems input;
	private BreacherController controller;
	private boolean lastRaise;
	private boolean lastLower;
	
	public ToggleTeleop(BreacherController controller, InputSystems input) {
		super(controller);
		this.controller = controller;
		this.input = input;
	}
	public void initialize(){
		lastRaise = false;
		lastLower = false;
	}
	@Override
	public boolean execute() {
		if(input.getOperatorStick().getButton(4).isTriggered()&& !lastRaise) {
			lastRaise = true;
			if(controller.getState() == BreacherState.IDLE) {
				controller.setState(BreacherState.OPENING);
				controller.getBreacher().getMotor().setSpeed(RAISE_SPEED);
			}
			else {
				controller.setState(BreacherState.IDLE);
				controller.getBreacher().getMotor().setSpeed(0);
			}
		}
		else if(input.getOperatorStick().getButton(5).isTriggered()&& !lastLower) {
			lastLower = true;
			if(controller.getState()==BreacherState.IDLE) {
				controller.setState(BreacherState.CLOSING);
				controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
			}
			else {
				controller.setState(BreacherState.IDLE);
				controller.getBreacher().getMotor().setSpeed(0);
			}
		}
		lastLower = false;
		lastRaise = false;
		return true;
	}

}
