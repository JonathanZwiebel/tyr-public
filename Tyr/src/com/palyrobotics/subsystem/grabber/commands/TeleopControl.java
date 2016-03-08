package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.grabber.GrabberController;

public class TeleopControl extends Command {

	GrabberController grabber;
	InputSystems input;
	
	public TeleopControl(GrabberController grabber, InputSystems input) {
		super(grabber);
		this.grabber = grabber;
		this.input = input;
	}
	
	@Override
	public boolean execute() {
		//Moves the grabber between 0.4(up) and 0.6(down) according to joystick input
		grabber.getOutput().getServo().set(Math.max(0.4, 0.6 - input.getShooterStick().getYaw().read()));
		return true;
	}

}