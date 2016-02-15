package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import com.palyrobotics.subsystem.breacher.BreacherController;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class SetBreacherAngle extends Command {

	private BreacherController breacher;

	private double target;

	private double error;

	private double current;

	private double previous;

	/**
	 * Moves the arm to the specified angle.
	 * 
	 * @param breacher
	 *            the breacher controller used
	 * @param target
	 *            the target angle
	 */
	public SetBreacherAngle(BreacherController breacher, double target) {
		this.breacher = breacher;
		this.target = target;

		current = breacher.getInput().getBreacherPotentiometer().getAngle();

		previous = current;

		error = target - breacher.getInput().getBreacherPotentiometer().getAngle();

	}

	@Override
	/**
	 * Moves the arm to the angle specified by the constructor. Uses custom PID.
	 */
	public boolean execute() {

		error = target - breacher.getInput().getBreacherPotentiometer().getAngle();

		current = breacher.getInput().getBreacherPotentiometer().getAngle();

		double derivative = (current - previous) / 50;

		previous = current;

		double speed = (PROPORTIONAL * error) + (DERIVATIVE * derivative);

		breacher.getBreacher().getMotor().setSpeed(speed);

		if (error < ACCEPTABLE_POTENTIOMETER_ERROR) {
			return true;
		}

		else {
			return false;
		}
	}

}