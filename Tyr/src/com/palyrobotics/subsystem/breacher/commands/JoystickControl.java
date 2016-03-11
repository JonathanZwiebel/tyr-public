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
	
	public JoystickControl(BreacherController controller, InputSystems input) {
		super(controller);
		this.controller = controller;
		this.input = input;
		idlePoint = input.getBreacherPotentiometer().getAngle();
		current = idlePoint;
		previous = current;
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
		
		//As long as there is no joystick input, keep the breacher arm in position.
		if(input.getSecondaryStick().getButton(Buttons.BREACHER_HOLD_BUTTON).isTriggered()) {
			
			error = idlePoint - input.getBreacherPotentiometer().getAngle();
			
			current = input.getBreacherPotentiometer().getAngle();
			
			double derivative = (current - previous) * UPDATES_PER_SECOND;
			
			double speed = Math.max(Math.min((PROPORTIONAL * error + DERIVATIVE * derivative), 0.3), -0.3);
			
			if(Math.abs(error) < 5) {
				controller.getBreacher().getMotor().setSpeed(0);
			}
			else {
				controller.getBreacher().getMotor().setSpeed(speed);
			}
			
			previous = current;
			
			return false;
		}
		
		//Moves the breacher according to joystick input
		else {
			controller.getBreacher().getMotor().setSpeed(input.getSecondaryStick().getPitch().read());
			return false;
		}
	}
	
	@Override 
	public void end() {
		controller.setMicroState(MicroBreacherState.JOYSTICK_CONTROL);
	}
}