package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

import com.palyrobotics.subsystem.shooter.shootercommands.ShooterLockingActuatorLockCommand;
import com.palyrobotics.subsystem.shooter.shootercommands.ShooterLockingActuatorUnlockCommand;

/**
 * A subcontroller for the shooter locking actuator
 * This is the only class that should submit ShooterLockingActuator commands
 *
 * @author Paly Robotics Programming Red Module
 */
public class ShooterLockingActuatorController implements Requirable {
	ShooterController parent;
	public ShooterLockingActuatorState state;
	
	public enum ShooterLockingActuatorState {
		IDLE,
		LOCK,
		UNLOCK,
		DISABLED
	}
	
	public ShooterLockingActuatorController(ShooterController parent) {
		this.parent = parent;
	}
	
	public void init() {
		state = ShooterLockingActuatorState.IDLE;
	}
	
	public void update() {
		
	}
	
	public void disable() {
		state = ShooterLockingActuatorState.DISABLED;
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
			Strongback.submit(new ShooterLockingActuatorLockCommand(parent));
		}
		else if(state == ShooterLockingActuatorState.UNLOCK) {
			Strongback.submit(new ShooterLockingActuatorUnlockCommand(parent));
		}
	}
	
	public boolean isLocked() {
		return parent.input.getLockDetector().isTriggered();
	}
}
 