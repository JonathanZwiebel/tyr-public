package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.Strongback;
import org.strongback.command.Command;

import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;

public class UncheckedLimitedTeleopCommand extends Command {
	private ShooterController con;
	private double angle;
	private double pitch;

	
	public UncheckedLimitedTeleopCommand(ShooterController controller) {
		con = controller;
		angle = con.input.getShooterPotentiometer().getAngle();
		pitch = con.input.getOperatorStick().getPitch().read();
	}
	
	/**
	 * Called every 20 ms when command is active
	 * Alters hardware and states
	 */
	@Override
	public boolean execute() {
		//When shooter is within maximum bounds accept all input
		if(angle < (ShooterConstants.MAX_ANGLE) && angle > (ShooterConstants.MIN_ANGLE)) {
			con.systems.getMotor().setSpeed(pitch);
			return false;
		}
		//When shooter is on or past the max or min angle, 
		//only accept input in the opposite direction
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
	
	/**
	 * Pushes a message to the logger when interuppted by another command
	 */
	@Override
	public void interrupted() {
		Strongback.logger().info("The command UncheckedLimitedTeleopCommand was interrupted.");
	}
	
	/**
	 * Sets speed to zero when ended
	 */
	@Override
	public void end() {
		con.systems.getMotor().setSpeed(0);
	}
}