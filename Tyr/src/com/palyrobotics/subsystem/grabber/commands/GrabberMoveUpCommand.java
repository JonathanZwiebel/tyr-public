package com.palyrobotics.subsystem.grabber.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

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
		Logger.getLogger("Central").log(Level.INFO, "Grabber moving up.");
		grabber.getOutput().getGrabber().retract();
		return true;
	}

}
