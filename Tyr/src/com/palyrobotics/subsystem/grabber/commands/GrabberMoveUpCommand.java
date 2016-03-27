package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.grabber.GrabberConstants;
import com.palyrobotics.subsystem.grabber.GrabberController;

public class GrabberMoveUpCommand extends Command{

	private GrabberController grabber;
	
	public GrabberMoveUpCommand(GrabberController grabber) {
		super(grabber);
		this.grabber = grabber;
	}
	
	@Override
	public boolean execute() {
		grabber.getOutput().getGrabber().retract();
		return true;
	}

}
