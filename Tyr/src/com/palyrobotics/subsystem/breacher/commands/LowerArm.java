package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherController;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class LowerArm extends Command {
	
	private BreacherController controller;
	
	public LowerArm(BreacherController controller) {
		super (controller);
		this.controller = controller;
	}
	@Override
	public boolean execute() {
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
		return true;
	}

}
