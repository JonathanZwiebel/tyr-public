package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.*;

public class OpenBreacher extends Command {
	private BreacherController breacherController;
	private double targetDistance;
	public OpenBreacher(BreacherController controller, double targetDistance) {
		super(controller);
		this.breacherController = controller;
		this.targetDistance = targetDistance;
	}
	
	@Override
	/**
	 * Sets the breacher state to opening
	 */
	public void initialize() {
		breacherController.setState(BreacherState.OPENING);
		breacherController.getPIDController().withTarget(targetDistance);
		breacherController.getPIDController().enable();
	}
	
	@Override
	public boolean execute() {
		if(breacherController.getPIDController().checkTolerance(targetDistance)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void interrupted() {
		breacherController.getPIDController().disable();
	}
	
	@Override
	public void end() {
		breacherController.getPIDController().disable();
		breacherController.setState(BreacherState.IDLE);
	}
}
