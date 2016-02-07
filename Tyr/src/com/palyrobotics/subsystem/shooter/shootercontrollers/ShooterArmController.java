package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

import com.palyrobotics.subsystem.shooter.shootercommands.ShooterArmSetAngleCommand;
import com.palyrobotics.subsystem.shooter.shootercommands.ShooterArmTeleopCommand;

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
		SETANGLE, // Using PID to set the angle
		DISABLED // Disabled
	}
	
	public ShooterArmController(ShooterController parent) {
		this.parent = parent;
	}
	
	public void init() {
		state = ShooterArmState.IDLE;
	}
	
	/**
	 * Will set the state to TELEOP if currently IDLE
	 */
	public void update() {
		if(state == ShooterArmState.IDLE) {
			setState(ShooterArmState.TELEOP);
		}
	}
	
	public void disable() {
		state = ShooterArmState.DISABLED;
	}
	
	/**
	 * Sets the arm state, calling commands as appropriate
	 * @param state incoming ShooterArmState
	 * @param args any arguments associated with the command
	 */
	public void setState(ShooterArmState state, float ... args) {
		System.out.println("Arm set state called");
		if(state != ShooterArmState.DISABLED) {
			this.state = state;
		}
		if(state == ShooterArmState.TELEOP) {
			Strongback.submit(new ShooterArmTeleopCommand(parent));
		}
		else if(state == ShooterArmState.SETANGLE) {
			Strongback.submit(new ShooterArmSetAngleCommand(parent, args[0]));
		}
	}
}
