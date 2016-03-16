package com.palyrobotics.subsystem.grabber.commands;

import org.strongback.command.Command;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.grabber.GrabberConstants;
import com.palyrobotics.subsystem.grabber.GrabberController;

public class GrabberTeleop extends Command {

	GrabberController grabber;
	InputSystems input;

	public GrabberTeleop(GrabberController grabber, InputSystems input) {
		super(grabber);
		this.grabber = grabber;
		this.input = input;
	}

	@Override
	public boolean execute() {
		grabber.getOutput().getRightServo().set(GrabberConstants.LOWER_POSITION_VOLTAGE
				+ GrabberConstants.UPPER_POSITION_VOLTAGE * input.getShooterStick().getYaw().read());
		grabber.getOutput().getLeftServo().set(GrabberConstants.UPPER_POSITION_VOLTAGE
				+ GrabberConstants.UPPER_POSITION_VOLTAGE * input.getShooterStick().getYaw().read());
		return false;
	}

}