package com.palyrobotics.subsystem.grabber.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.grabber.GrabberConstants;

public class GrabberMoveDownCommand extends Command{
	
	private GrabberController grabber;
	
	public GrabberMoveDownCommand(GrabberController grabber) {
		super(grabber);
		this.grabber = grabber;
	}
	
	@Override
	public boolean execute() {
		Logger.getLogger("Central").log(Level.INFO, "Grabber moving down.");
		grabber.getOutput().getGrabber().extend();
		return true;
	}
}
