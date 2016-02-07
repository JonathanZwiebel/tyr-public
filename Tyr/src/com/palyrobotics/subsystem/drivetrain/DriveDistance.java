package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.*;

public class DriveDistance extends Command {

	private DrivetrainController drivetrain;
	private double distance;
	
	public DriveDistance(DrivetrainController drivetrain, double distance) {
		super(drivetrain.getRequirements());
		this.drivetrain = drivetrain;
		this.distance = distance;
	}
	
	@Override
	public void initialize() {
		drivetrain.setState(State.DRIVING_DISTANCE);
	}
	
	@Override
	public boolean execute() {
		
		return true;
	}
}
