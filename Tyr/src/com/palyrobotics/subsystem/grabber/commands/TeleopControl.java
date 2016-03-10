package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.grabber.GrabberConstants;
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
		grabber.getOutput().getServo().set(GrabberConstants.LOWER_POSITION_VOLTAGE + GrabberConstants.UPPER_POSITION_VOLTAGE * input.getShooterStick().getYaw().read());
		return false;
	}

}