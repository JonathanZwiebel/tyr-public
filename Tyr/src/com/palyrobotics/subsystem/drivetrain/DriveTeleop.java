package com.palyrobotics.subsystem.drivetrain;

import java.util.*;

import org.strongback.command.Command;
import org.strongback.command.Requirable;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.*;

public class DriveTeleop extends Command {

	private DrivetrainController drivetrain;
	
	public DriveTeleop(DrivetrainController drivetrain) {
		super(drivetrain.getRequirements());
		this.drivetrain = drivetrain;
	}
	
	@Override
	public void initialize() {
		drivetrain.setState(State.DRIVING_TELEOP);
	}
	
	@Override
	public boolean execute() {
		
		return true;
	}
}