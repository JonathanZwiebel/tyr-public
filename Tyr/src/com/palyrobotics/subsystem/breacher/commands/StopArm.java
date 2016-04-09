package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StopArm extends Command {

	private BreacherController controller;

	public StopArm(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		controller.setMicroState(MicroBreacherState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "StopArm initalized.");
	}

	@Override
	public boolean execute() {
		controller.getBreacher().getMotor().setSpeed(0);
    	Logger.getLogger("Central").log(Level.INFO, "StopArm is ending.");
		return true;
	}
	
	@Override
	public void end() {
		controller.setMicroState(MicroBreacherState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "StopArm ended.");
	}
	
	@Override
	public void interrupted() {
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
    	Logger.getLogger("Central").log(Level.INFO, "StopArm interrupted.");
	}

}