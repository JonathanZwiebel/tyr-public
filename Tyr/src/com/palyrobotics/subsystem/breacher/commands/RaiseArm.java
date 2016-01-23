package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.BreacherState;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class RaiseArm extends Command {
	
	private BreacherController controller;
	
	public RaiseArm(BreacherController controller) {
		super (controller);
		this.controller = controller;
	}
	public void initialize() {
		controller.setState(BreacherState.OPENING);
	}
	@Override
	public boolean execute() {
		controller.getBreacher().getMotor().setSpeed(RAISE_SPEED);
		return true;
	}

}
