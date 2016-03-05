package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.grabber.GrabberConstants;

public class GrabberMoveDownCommand extends Command{
	
	private GrabberController controller;
	
	public GrabberMoveDownCommand(GrabberController control) {
		super(control);
		this.controller = control;
	}
	
	@Override
	public boolean execute() {
		controller.getOutput().getServo().set(GrabberConstants.LOWER_POSITION_VOLTAGE);
		return true;
	}
}
