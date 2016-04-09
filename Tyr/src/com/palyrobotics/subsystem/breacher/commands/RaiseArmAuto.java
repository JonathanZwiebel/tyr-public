
package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherConstants;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RaiseArmAuto extends Command {
	private BreacherController controller;
	private double startTime;

	public RaiseArmAuto(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		startTime = System.currentTimeMillis();
		controller.setMicroState(MicroBreacherState.OPENING);
    	Logger.getLogger("Central").log(Level.INFO, "RaiseArmAuto initalized.");
	}

	@Override
	/**
	 * Uses a timer system to raise the arm.
	 */
	public boolean execute() {
		// stops the command if the desired angle is reached
		if (controller.getInput().getBreacherPotentiometer().getAngle() > OPEN_BREACHER_ANGLE) {
	    	Logger.getLogger("Central").log(Level.INFO, "RaiseArmAuto is ending.");
			return true;
		}
		
		// uses a timer system to raise the arm for a certain period of time. we should use the potentiometer when ready
		if (System.currentTimeMillis() - startTime < BreacherConstants.OPEN_TIME) {
			controller.getBreacher().getMotor().setSpeed(RAISE_SPEED);
	    	Logger.getLogger("Central").log(Level.FINE, "RaiseArmAuto is continuing.");
			return false;
		}
		controller.getBreacher().getMotor().setSpeed(0);
    	Logger.getLogger("Central").log(Level.INFO, "RaiseArmAuto is ending.");
		return true;
	}

	@Override
	public void end() {
		controller.setMicroState(MicroBreacherState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "RaiseArmAuto ended.");
	}
	
	@Override
	public void interrupted() {
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
    	Logger.getLogger("Central").log(Level.INFO, "RaiseArmAuto interrupted.");
	}

}