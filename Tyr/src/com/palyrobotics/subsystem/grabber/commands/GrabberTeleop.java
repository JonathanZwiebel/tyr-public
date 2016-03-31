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
	private boolean justTriggered;
	private boolean upPosition;
	
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
		//toggle the grabber
		if(input.getSecondaryStick().getButton(GRABBER_TOGGLE_BUTTON).isTriggered()) {
			 if(this.justTriggered == false) {
				 this.upPosition = !upPosition;
				 this.justTriggered = true;
			 }
		} 
		else {
			 this.justTriggered = false;
		}
		
		if(upPosition) {
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