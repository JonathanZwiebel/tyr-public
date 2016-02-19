package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.Micro;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class LowerArm extends Command {

	private BreacherController controller;

	public LowerArm(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		controller.setMicroState(Micro.CLOSING);
	}
	
	@Override
	/**
	 * Lower the breacher arm for a short period of time. The LowerArm command
	 * is meant to be called repeatedly.
	 */
	public boolean execute() {
		
		//Safety feature
		if(controller.getInput().getShooterStick().getButton(CANCEL_BUTTON).isTriggered()) {
			controller.getBreacher().getMotor().setSpeed(0);
			return true;
		}
		
		controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
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