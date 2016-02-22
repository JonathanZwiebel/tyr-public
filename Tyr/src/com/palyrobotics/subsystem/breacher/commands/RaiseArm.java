package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class RaiseArm extends Command {

	private BreacherController controller;

	public RaiseArm(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		controller.setMicroState(MicroBreacherState.OPENING);
	}

	@Override
	/**
	 * Raises the arm slightly. The RaiseArm command is meant to be called
	 * repeatedly.
	 */
	public boolean execute() {
		//Safety feature
		if(controller.getInput().getShooterStick().getButton(CANCEL_BUTTON).isTriggered()) {
			controller.getBreacher().getMotor().setSpeed(0);
			return true;
		}
		
		controller.getBreacher().getMotor().setSpeed(RAISE_SPEED);
		return true;
	}
	
	@Override
	public void end() {
		controller.setMicroState(MicroBreacherState.IDLE);
	}
	
	@Override
	public void interrupted() {
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
	}

}