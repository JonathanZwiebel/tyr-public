package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.Drivetrain.*;

public class DriveDistance extends Command {

	private Drivetrain drivetrain;
	private double distance;
	
	public DriveDistance(Drivetrain drivetrain, double distance) {
		this.drivetrain = drivetrain;
		this.distance = distance;
	}
	
	@Override
	public boolean execute() {
		drivetrain.setState(State.DRIVING_DISTANCE);
		
		return false;
	}
}
