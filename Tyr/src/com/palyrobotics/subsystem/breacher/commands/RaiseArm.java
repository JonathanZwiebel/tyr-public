package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RaiseArm extends Command {

	private BreacherController controller;

	public RaiseArm(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		controller.setMicroState(MicroBreacherState.OPENING);
    	Logger.getLogger("Central").log(Level.INFO, "RaiseArm initalized.");
	}

	@Override
	/**
	 * Raises the arm slightly. The RaiseArm command is meant to be called
	 * repeatedly.
	 */
	public boolean execute() {
		//Safety feature
		if(controller.getInput().getShooterStick().getButton(Buttons.BREACHER_CANCEL_BUTTON).isTriggered()) {
			controller.getBreacher().getMotor().setSpeed(0);
	    	Logger.getLogger("Central").log(Level.INFO, "RaiseArm is ending.");
			return true;
		}
		
		controller.getBreacher().getMotor().setSpeed(RAISE_SPEED);
    	Logger.getLogger("Central").log(Level.INFO, "RaiseArm is ending.");
		return true;
	}
	
	@Override
	public void end() {
		controller.setMicroState(MicroBreacherState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "RaiseArm ended.");
	}
	
	@Override
	public void interrupted() {
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
    	Logger.getLogger("Central").log(Level.INFO, "RaiseArm interrupted.");
	}

}