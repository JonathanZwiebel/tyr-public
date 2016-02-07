package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.State;

public class TurnAngle extends Command {

	private DrivetrainController drivetrain;
	private double angle;
	
	public TurnAngle(DrivetrainController drivetrain, double angle) {
		super(drivetrain.getRequirements());
		this.drivetrain = drivetrain;
		this.angle = angle;
	}
	
	@Override
	public void initialize() {
		drivetrain.setState(State.TURNING_ANGLE);
	}
	
	@Override
	public boolean execute() {
		
		return true;
	}
}
