package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LowerArm extends Command {

	private BreacherController controller;

	public LowerArm(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		controller.setMicroState(MicroBreacherState.CLOSING);
    	Logger.getLogger("Central").log(Level.INFO, "LowerArm initalized.");
	}
	
	@Override
	/**
	 * Lower the breacher arm for a short period of time. The LowerArm command
	 * is meant to be called repeatedly.
	 */
	public boolean execute() {
		
		//Safety feature
		if(controller.getInput().getShooterStick().getButton(Buttons.BREACHER_CANCEL_BUTTON).isTriggered()) {
			controller.getBreacher().getMotor().setSpeed(0);
	    	Logger.getLogger("Central").log(Level.INFO, "LowerArm is ending.");
			return true;
		}
		
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
    	Logger.getLogger("Central").log(Level.INFO, "LowerArm is ending.");
		return true;
	}
	
	@Override
	public void end() {
		controller.setMicroState(MicroBreacherState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "LowerArm ended.");
	}
	
	@Override
	public void interrupted() {
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
    	Logger.getLogger("Central").log(Level.INFO, "LowerArm interrupted.");
	}

}