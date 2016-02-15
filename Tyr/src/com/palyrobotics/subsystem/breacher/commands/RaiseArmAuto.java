
package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherConstants;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.BreacherState;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class RaiseArmAuto extends Command {
	private BreacherController controller;
	private double startTime;

	public RaiseArmAuto(BreacherController controller) {
		super(controller);
		this.controller = controller;
	}

	public void intialize() {
		startTime = System.currentTimeMillis();
		controller.setState(BreacherState.OPENING);
	}

	@Override
	/**
	 * Uses a timer system to raise the arm.
	 */
	public boolean execute() {
		// uses a timer system to raise the arm for a certain period of time. we should use the potentiometer when ready
		if (System.currentTimeMillis() - startTime < BreacherConstants.OPEN_TIME) {
			controller.getBreacher().getMotor().setSpeed(RAISE_SPEED);
			return false;
		}
		controller.getBreacher().getMotor().setSpeed(0);
		return true;
	}

	public void end() {
		controller.setState(BreacherState.IDLE);
	}

}