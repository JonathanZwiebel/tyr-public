package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JoystickControl extends Command {
	private BreacherController controller;
	private InputSystems input;
	
	private double idlePoint;
	private double error;
	private double current;
	private double previous;
	
	private boolean holding;
	private boolean justTriggered;
	
	public JoystickControl(BreacherController controller, InputSystems input) {
		super(controller);
		this.controller = controller;
		this.input = input;
		try {
			idlePoint = input.getBreacherPotentiometer().getAngle();
		}
		catch(Exception e) {
	    	Logger.getLogger("Central").log(Level.WARNING, "No Breacher Potentiometer. " + e);
		}
		current = idlePoint;
		previous = current;
	}
	
	@Override
	public void initialize() {
		controller.setMicroState(MicroBreacherState.JOYSTICK_CONTROL);
		this.holding = false;
    	Logger.getLogger("Central").log(Level.INFO, "JoystickControl initalized.");
	}
	
	@Override
	/**
	 * This command is meant to be called repeatedly.
	 * 
	 * @return true, this command stops immediately
	 */
	public boolean execute() {
		// Checks if breacher should hold position
		if(input.getSecondaryStick().getButton(Buttons.BREACHER_HOLD_BUTTON).isTriggered()) {
			 if(this.justTriggered == false) {
				 this.holding = !holding;
				 this.justTriggered = true;
			 }
		} 
		else {
			 this.justTriggered = false;
		}
		
		// PD in place if holding position
		if(holding) {
			// Checks that breacher pot exists
			try {
				error = idlePoint - input.getBreacherPotentiometer().getAngle();
				current = input.getBreacherPotentiometer().getAngle();
				double derivative = (error - previous) * UPDATES_PER_SECOND;
	 			double speed = Math.max(Math.min((PROPORTIONAL * error + DERIVATIVE * derivative), 0.3), -0.3);
	 			controller.getBreacher().getMotor().setSpeed(speed);
	 			previous = error;
			} catch(Exception e) {
				this.holding = false;
		    	Logger.getLogger("Central").log(Level.WARNING, "No Breacher Potentiometer. " + e);
			}
	    	Logger.getLogger("Central").log(Level.FINE, "JoystickControl is continuing.");
 			return false;
		}
		
		else {
			controller.getBreacher().getMotor().setSpeed(input.getSecondaryStick().getPitch().read());
			try {
				idlePoint = input.getBreacherPotentiometer().getAngle();
			} catch(Exception e) {
				System.err.println("No breacher potentiometer");
			}
	    	Logger.getLogger("Central").log(Level.FINE, "JoystickControl is continuing.");
			return false;
		}
	}
	
	@Override
	public void interrupted() {
    	Logger.getLogger("Central").log(Level.INFO, "JoystickControl interrupted.");
	}
	
	@Override 
	public void end() {
		controller.setMicroState(MicroBreacherState.JOYSTICK_CONTROL);
    	Logger.getLogger("Central").log(Level.INFO, "JoystickControl ended.");
	}
}