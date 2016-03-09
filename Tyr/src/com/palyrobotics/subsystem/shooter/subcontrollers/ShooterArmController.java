package com.palyrobotics.subsystem.shooter.subcontrollers;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterArmHoldCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterArmSetAngleCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterArmTeleopCommand;

/**
 * A subcontroller for the shooter arm
 * This is the only class that should submit ShooterArm commands
 *
 * @author Paly Robotics Programming Red Module
 */
public class ShooterArmController implements Requirable {
	ShooterController parent;
	public ShooterArmState state;
	
	public enum ShooterArmState {
		IDLE, // The default state for the shooter
		TELEOP, // Standard teleop control
		SET_ANGLE, // Using PID to set the angle
		HOLD, // uisng PID to set the velocity to 0
		DISABLED // Disabled
	}
	
	public ShooterArmController(ShooterController parent) {
		this.parent = parent;
	}
	
	public void init() {
		state = ShooterArmState.IDLE;
	}
	
	public void update() {
		
	}
	
	public void disable() {
		state = ShooterArmState.DISABLED;
	}
	
	/**
	 * Sets the arm state, calling commands as appropriate
	 * @param state incoming ShooterArmState
	 * @param args any arguments associated with the command
	 * TODO: Handle illegal arguments
	 */
	public void setState(ShooterArmState state, float ... args) {
		if(state != ShooterArmState.DISABLED) {
			this.state = state;
		}
		if(state == ShooterArmState.TELEOP) {
			Strongback.submit(new ShooterArmTeleopCommand(parent));
		}
		else if(state == ShooterArmState.SET_ANGLE) {
			Strongback.submit(new ShooterArmSetAngleCommand(parent, args[0]));
		}
		else if(state == ShooterArmState.HOLD) {
			Strongback.submit(new ShooterArmHoldCommand(parent));
		}
	}
}
