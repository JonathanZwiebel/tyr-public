package com.palyrobotics.robot.autonomous;

import org.strongback.Strongback;
import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;

public class TimedAuto extends Command {
	private DrivetrainController con;
	private final float driveSpeed = .3f;
	private int secondsToRunWithUpdateCycle;
	
	public TimedAuto (DrivetrainController controller, int seconds) {
		this.con = controller;
		this.secondsToRunWithUpdateCycle = seconds*20;
	}
	
	public boolean execute() {
		con.getOutput().getLeftMotor().setSpeed(driveSpeed);
		con.getOutput().getRightMotor().setSpeed(driveSpeed);
		this.secondsToRunWithUpdateCycle-=20;
		
		
		if (this.secondsToRunWithUpdateCycle == 0) {
			con.getOutput().getLeftMotor().setSpeed(driveSpeed);
			con.getOutput().getRightMotor().setSpeed(driveSpeed);
			return true;
		}
		return false;
	}
}
