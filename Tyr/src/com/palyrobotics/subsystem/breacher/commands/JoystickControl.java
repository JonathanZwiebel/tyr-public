package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;

public class JoystickControl extends Command {

	private BreacherController controller;
	
	private InputSystems input;
	
	public JoystickControl(BreacherController controller, InputSystems input) {
		this.controller = controller;
		this.input = input;
	}
	
	@Override
	public void initialize() {
		controller.setMicroState(MicroBreacherState.JOYSTICK_CONTROL);
	}
	
	@Override
	/**
	 * This command is meant to be called repeatedly.
	 * 
	 * @return true, this command stops immediately
	 */
	public boolean execute() {
		controller.getBreacher().getMotor().setSpeed(input.getSecondaryStick().getPitch().read());
		return true;
	}
	
	@Override 
	public void end() {
		controller.setMicroState(MicroBreacherState.JOYSTICK_CONTROL);
	}
	
	@Override
	public void interrupted() {
	}
}
