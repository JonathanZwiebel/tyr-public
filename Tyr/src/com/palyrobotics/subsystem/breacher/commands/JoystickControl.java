package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

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
			System.err.println("No Breacher Potentiometer");
		}
		current = idlePoint;
		previous = current;
	}
	
	@Override
	public void initialize() {
		controller.setMicroState(MicroBreacherState.JOYSTICK_CONTROL);
		this.holding = false;
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
				System.err.println("No breacher potentiometer");
			}
 			return false;
		}
		
		else {
			controller.getBreacher().getMotor().setSpeed(input.getSecondaryStick().getPitch().read());
			try {
				idlePoint = input.getBreacherPotentiometer().getAngle();
			} catch(Exception e) {
				System.err.println("No breacher potentiometer");
			}
			return false;
		}
	}
	
	@Override 
	public void end() {
		controller.setMicroState(MicroBreacherState.JOYSTICK_CONTROL);
	}
}