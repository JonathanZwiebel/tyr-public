package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.robot.InputSystems;
import static com.palyrobotics.subsystem.grabber.GrabberConstants.*;
import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.grabber.GrabberController.GrabberState;
import com.palyrobotics.subsystem.grabber.GrabberController.MicroGrabberState;

import static com.palyrobotics.robot.Buttons.*;

public class GrabberTeleop extends Command {

	GrabberController grabber;
	InputSystems input;
	
	public GrabberTeleop(GrabberController grabber, InputSystems input) {
		super(grabber);
		this.grabber = grabber;
		this.input = input;
	}

	@Override
	public void initialize() {
	}
	
	@Override
	public boolean execute() {
		if(input.getSecondaryStick().getButton(GRABBER_TOGGLE_BUTTON).isTriggered()) {
			 grabber.getOutput().getGrabber().retract();
			 grabber.setMicroGrabberState(MicroGrabberState.RAISED);
		} 
		else {
			 grabber.getOutput().getGrabber().extend();
			 grabber.setMicroGrabberState(MicroGrabberState.LOWERED);
		}
		
		return false;
	}

}