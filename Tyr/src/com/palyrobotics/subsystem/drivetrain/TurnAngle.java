package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.State;

public class TurnAngle extends Command {

	private DrivetrainController drivetrain;
	private double angle;
	
	public TurnAngle(DrivetrainController drivetrain, double angle) {
		this.drivetrain = drivetrain;
		this.angle = angle;
	}
	
	@Override
	public boolean execute() {
		drivetrain.setState(State.TURNING_ANGLE);
		
		return false;
	}
}
