package com.palyrobotics.subsystem.grabber.commands;

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
		grabber.getOutput().getLeftServo().set(GrabberConstants.LOWER_POSITION_VOLTAGE);
		grabber.getOutput().getRightServo().set(GrabberConstants.LOWER_POSITION_VOLTAGE);
		return true;
	}
}
