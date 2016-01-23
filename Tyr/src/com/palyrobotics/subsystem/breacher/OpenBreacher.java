package com.palyrobotics.subsystem.breacher;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherController.*;

public class OpenBreacher extends Command {
	private BreacherController breacherController;
	private double targetDistance;
	public OpenBreacher(BreacherController controller, double targetDistance) {
		this.breacherController = controller;
		this.targetDistance = targetDistance;
	}
	
	@Override
	/**
	 * Sets the breacher state to opening
	 */
	public void initialize() {
		breacherController.setState(BreacherState.OPENING);
		breacherController.PIDController.withTarget(targetDistance);
		breacherController.PIDController.enable();
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void interrupted() {
		breacherController.PIDController.disable();
	}
	
	@Override
	public void end() {
		breacherController.PIDController.disable();
		breacherController.setState(BreacherState.IDLE);
	}
}
