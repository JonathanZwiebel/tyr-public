package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.grabber.GrabberConstants;
import com.palyrobotics.subsystem.grabber.GrabberController;

public class GrabberMoveUpCommand extends Command{

	private GrabberController controller;
	
	public GrabberMoveUpCommand(GrabberController control) {
		super(control);
		this.controller = control;
	}
	
	@Override
	public boolean execute() {
		controller.getOutput().getServo().set(GrabberConstants.UPPER_POSITION_VOLTAGE);
		return true;
	}

}
