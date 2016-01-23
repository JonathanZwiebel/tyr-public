package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.Strongback;
import org.strongback.command.Command;

import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;

public class TeleopCommand extends Command {
	private ShooterController con;
	private double angle = con.input.getShooterPotentiometer().getAngle();
	private double pitch = con.input.getOperatorStick().getPitch().read();
	
	public TeleopCommand(ShooterController con) {
		this.con = con;
	}
	
	
	@Override
	public boolean execute() {
		if(angle < (ShooterConstants.maxAngle) && angle > (ShooterConstants.minAngle)) {
			con.systems.getMotor().setSpeed(pitch);
			return false;
		}
		else if(angle >= ShooterConstants.maxAngle){
			if(pitch > 0){
				con.systems.getMotor().setSpeed(0);
			}
			else {
				con.systems.getMotor().setSpeed(pitch);
			}
			return false;
		}
		else if(angle <= ShooterConstants.minAngle) {
			if(pitch < 0){
				con.systems.getMotor().setSpeed(0);
			}
			else {
				con.systems.getMotor().setSpeed(pitch);
			}
			return false;
		}
		return false;
	}
	
	@Override
	public void interrupted() {
		Strongback.logger().info("The command TeleopCommand was interrupted.");
	}
	
	@Override
	public void end() {
		con.systems.getMotor().setSpeed(0);
	}
}