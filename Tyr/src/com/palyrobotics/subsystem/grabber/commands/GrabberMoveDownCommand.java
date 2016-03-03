package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.grabber.GrabberController;

public class GrabberMoveDownCommand extends Command{
	
	private GrabberController controller;
	
	public GrabberMoveDownCommand(GrabberController control) {
		super(control);
		this.controller = control;
	}
	
	@Override
	public boolean execute() {

		//going up
		controller.getOutput().getServo().set(0.4);
		return true;
	}
}