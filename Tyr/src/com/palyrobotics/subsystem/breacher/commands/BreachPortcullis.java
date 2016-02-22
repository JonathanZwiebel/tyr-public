package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class BreachPortcullis extends Command{
	
	private BreacherController controller;
	
	public BreachPortcullis(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}
	
	@Override
	public void initialize() {
		controller.setMicroState(MicroBreacherState.OPENING);
	}
	
	@Override
	public boolean execute() {
		if(controller.getInput().getBreacherPotentiometer().getAngle() < OPEN_BREACHER_ANGLE) {
			controller.getBreacher().getMotor().setSpeed(RAISE_SPEED);
			return false;
		}
		return true;
	}
	
	@Override
	 public void interrupted() {
		controller.setMicroState(MicroBreacherState.IDLE);
		controller.getBreacher().getMotor().setSpeed(0.0);
	}
	
	@Override
	public void end() {
		controller.setMicroState(MicroBreacherState.IDLE);
		controller.getBreacher().getMotor().setSpeed(0.0);
	}
}
