package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.BreacherState;

public class LowerArmAuto extends Command {
	private BreacherController controller;
	private double begin;
	
	public LowerArmAuto(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}
	
	public void initialize() {
		controller.setState(BreacherState.CLOSING);
		controller.getInput().getBreacherPotentiometer().zero();
		begin = System.currentTimeMillis();
	}
	
	@Override
	public boolean execute() {
		//stops the command if the desired angle is reached
		if(controller.getInput().getBreacherPotentiometer().getAngle() < CLOSE_BREACHER_ANGLE) {
			return true;
		}
		//lowers the arm with a timer system
		if(System.currentTimeMillis()-begin<CLOSE_TIME) {
			controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
			return false;
		}
		controller.getBreacher().getMotor().setSpeed(0);
		return true;
	}
	public void end() {
		controller.setState(BreacherState.IDLE);
	}

}
