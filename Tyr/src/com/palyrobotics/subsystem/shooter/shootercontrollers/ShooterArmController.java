package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

import com.palyrobotics.subsystem.shooter.shootercommands.ShooterArmTeleopCommand;

public class ShooterArmController implements Requirable {
	ShooterController parent;
	public ShooterArmState state;
	
	public enum ShooterArmState {
		IDLE,
		TELEOP,
		SETANGLE,
		DISABLED
	}
		
	public ShooterArmController(ShooterController parent) {
		this.parent = parent;
	}
	
	public void init() {
		state = ShooterArmState.IDLE;
	}
	
	public void update() {
		if(state == ShooterArmState.IDLE) {
			setState(ShooterArmState.TELEOP);
		}
	}
	
	public void disable() {
		state = ShooterArmState.DISABLED;
	}
	
	public void setState(ShooterArmState state) {
		if(state != ShooterArmState.DISABLED) {
			this.state = state;
		}
		if(state == ShooterArmState.TELEOP) {
			Strongback.submit(new ShooterArmTeleopCommand(parent));
		}
	}
}
