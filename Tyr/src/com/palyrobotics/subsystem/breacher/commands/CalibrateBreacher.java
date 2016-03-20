package com.palyrobotics.subsystem.breacher.commands;

import edu.wpi.first.wpilibj.command.Command;

public class CalibrateBreacher extends Command {

	private double breacherpotangle;
	
	public CalibrateBreacher(double breacherpotangle) {
		this.breacherpotangle = breacherpotangle;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

}
