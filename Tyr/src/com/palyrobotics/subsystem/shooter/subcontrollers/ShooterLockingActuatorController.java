package com.palyrobotics.subsystem.shooter.subcontrollers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorLockCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorUnlockCommand;

/**
 * A subcontroller for the shooter locking actuator
 * This is the only class that should submit ShooterLockingActuator commands
 *
 * @author Paly Robotics Programming Red Module
 */
public class ShooterLockingActuatorController implements Requirable {
	ShooterController parent;
	public ShooterLockingActuatorState state;
	private boolean isRetracted;
	
	public enum ShooterLockingActuatorState {
		IDLE,
		LOCK,
		UNLOCK,
		DISABLED
	}
	
	public ShooterLockingActuatorController(ShooterController parent) {
		this.parent = parent;
		isRetracted = false;
	}
	
	public void init() {
		state = ShooterLockingActuatorState.IDLE;
		Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorController initialized.");
	}
	
	public void update() {
		Logger.getLogger("Central").log(Level.FINE, "ShooterLockingActuatorController updated.");
	}
	
	public void disable() {
		state = ShooterLockingActuatorState.DISABLED;
		Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorController disabled.");
	}
	
	/**
	 * Sets the locking actuator state, calling commands as appropriate
	 * @param state incoming ShooterLockingActuatorState
	 * @param args any arguments associated with the command
	 */
	public void setState(ShooterLockingActuatorState state, float ... args) {
		if(state != ShooterLockingActuatorState.DISABLED) {
			this.state = state;
		}
		if(state == ShooterLockingActuatorState.LOCK) {
			isRetracted = false;
			Strongback.submit(new ShooterLockingActuatorLockCommand(parent));
		}
		else if(state == ShooterLockingActuatorState.UNLOCK) {
			isRetracted = true;
			Strongback.submit(new ShooterLockingActuatorUnlockCommand(parent));
		}
	}
	
	public boolean isLocked() {
		return !isRetracted;
	}
	
	public ShooterLockingActuatorState getState() {
		return state;
	}
}
 