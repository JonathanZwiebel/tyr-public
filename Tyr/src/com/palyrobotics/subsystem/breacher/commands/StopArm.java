package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;
import org.strongback.command.Requirable;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.Micro;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class StopArm extends Command {

	private BreacherController controller;

	public StopArm(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		controller.setMicroState(Micro.IDLE);
	}

	@Override
	public boolean execute() {
		controller.getBreacher().getMotor().setSpeed(0);
		return true;
	}
	
	@Override
	public void end() {
		controller.setMicroState(Micro.IDLE);
	}
	
	@Override
	public void interrupted() {
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
	}

}