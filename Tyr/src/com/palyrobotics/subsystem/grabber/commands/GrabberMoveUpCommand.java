package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.grabber.GrabberController;

public class GrabberMoveUpCommand extends Command{

	private GrabberController controller;
	
	public GrabberMoveUpCommand(GrabberController control) {
		super(control);
		this.controller = control;
	}
	
	@Override
	public boolean execute() {

		//going up
		controller.getOutput().getServo().set(0.6);
		return true;
	}

}
