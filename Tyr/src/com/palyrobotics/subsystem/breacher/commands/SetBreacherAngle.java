package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.Micro;

public class SetBreacherAngle extends Command {

	private BreacherController breacher;

	private double target;

	private double error;

	private double current;

	private double previous;

	/**
	 * Moves the arm to the specified angle.
	 * 
	 * @param breacher the breacher controller used
	 * @param target the target angle
	 */
	public SetBreacherAngle(BreacherController breacher, double target) {
		this.breacher = breacher;
		this.target = target;

		current = breacher.getInput().getBreacherPotentiometer().getAngle();

		previous = current;
	}

	@Override
	public void initialize() {
		breacher.setMicroState(Micro.SETTING);
	}
	
	@Override
	/**
	 * Moves the arm to the angle specified by the constructor. Uses custom PID.
	 */
	public boolean execute() {
		
		//Safety feature
		if(breacher.getInput().getShooterStick().getButton(CANCEL_BUTTON).isTriggered()) {
			breacher.getBreacher().getMotor().setSpeed(0);
			return true;
		}

		error = target - breacher.getInput().getBreacherPotentiometer().getAngle();

		current = breacher.getInput().getBreacherPotentiometer().getAngle();

		double derivative = (current - previous) * UPDATES_PER_SECOND;

		previous = current;

		double speed = Math.max(Math.min((PROPORTIONAL * error) + (DERIVATIVE * derivative), MAX_SPEED), -MAX_SPEED);

		breacher.getBreacher().getMotor().setSpeed(speed);

		if(Math.abs(error) < ACCEPTABLE_POTENTIOMETER_ERROR && Math.abs(derivative) < ACCEPTABLE_DERIVATIVE_ERROR) {
			breacher.setMicroState(Micro.IDLE);
			return true;
		}

		else {
			return false;
		}
	}
	
	@Override
	public void end() {
		breacher.setMicroState(Micro.IDLE);
		breacher.getBreacher().getMotor().setSpeed(0);
	}
	
	@Override
	public void interrupted() {
		breacher.getBreacher().getMotor().setSpeed(0);
	}

}