package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.Micro;

public class LowerArmAuto extends Command {
	private BreacherController controller;
	private double begin;

	public LowerArmAuto(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	@Override
	public void initialize() {
		controller.setMicroState(Micro.CLOSING);
		begin = System.currentTimeMillis();
	}

	@Override
	/**
	 * Uses a timer system to close the breacher. A potentiometer acts as a
	 * backup to stop the breacher arm.
	 */
	public boolean execute() {
		// stops the command if the desired angle is reached
		if (controller.getInput().getBreacherPotentiometer().getAngle() < CLOSE_BREACHER_ANGLE) {
			return true;
		}

		// lowers the arm with a timer system
		if (System.currentTimeMillis() - begin < CLOSE_TIME) {
			controller.getBreacher().getMotor().setSpeed(LOWER_SPEED);
			return false;
		}

		// When the breacher has been lowered enough, stop it.
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