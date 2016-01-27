package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.Strongback;
import org.strongback.command.Command;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterSystems;

public class UncheckedLimitedTeleopCommand extends Command {
	private ShooterController con;
	private double angle = con.input.getShooterPotentiometer().getAngle();
	private double pitch = con.input.getOperatorStick().getPitch().read();
	private ShooterSystems output;
	private InputSystems input;
	
	public UncheckedLimitedTeleopCommand(ShooterController con) {
		this.con = con;
		output = con.systems;
		input = con.input;
	}
	
	
	@Override
	public boolean execute() {
		if(angle < (ShooterConstants.MAX_ANGLE) && angle > (ShooterConstants.MIN_ANGLE)) {
			output.getMotor().setSpeed(pitch);
			return false;
		}
		else if(angle >= ShooterConstants.MAX_ANGLE){
			if(pitch > 0){
				con.systems.getMotor().setSpeed(0);
			}
			else {
				con.systems.getMotor().setSpeed(pitch);
			}
			return false;
		}
		else if(angle <= ShooterConstants.MIN_ANGLE) {
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