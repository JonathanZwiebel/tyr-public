package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.breacher.BreacherController;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class SetArm extends Command {

	private BreacherController breacher;
	
	private double target;
	
	/**
	 * Moves the arm to the specified angle.
	 * 
	 * @param breacher the breacher controller used
	 * @param target the target angle
	 */
	public SetArm(BreacherController breacher, double target) {
		this.breacher = breacher;
		this.target = target;
	}

	@Override
	public boolean execute() {
		//If the arm has reached its target angle, stop the command.
		if(Math.abs(breacher.getInput().getBreacherPotentiometer().getAngle() - target) < ACCEPTABLE_POTENTIOMETER_ERROR) {
			breacher.getBreacher().getMotor().setSpeed(0);
			return true;
		}
		
		//If destination not reached, continue.
		else {
			//If the arm is below the target angle, raise the arm.
			if(breacher.getInput().getBreacherPotentiometer().getAngle() < target) {
				breacher.getBreacher().getMotor().setSpeed(RAISE_SPEED);
			}
			
			//If the arm is above the target angle, lower the arm.
			else {
				breacher.getBreacher().getMotor().setSpeed(LOWER_SPEED);
			}
			
			//Tell StrongBack that the command is not finished.
			return false;
		}
	}

}
