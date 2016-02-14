package com.palyrobotics.subsystem.shooter.subcontrollers;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorExtendCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorRetractCommand;

/**
 * A subcontroller for the shooter loading actuator
 * This is the only class that should submit ShooterLoadingActuator commands
 *
 * @author Paly Robotics Programming Red Module
 */
public class ShooterLoadingActuatorController implements Requirable {
	ShooterController parent;
	public ShooterLoadingActuatorState state;
	private boolean isRetracted;
	
	public enum ShooterLoadingActuatorState {
		IDLE, // The default state
		EXTEND, // Extending to the shooting state
		RETRACT, // Retracting to the loaded state
		DISABLED // Disabled
	}
	
	public ShooterLoadingActuatorController(ShooterController parent) {
		this.parent = parent;
		isRetracted = false;
	}
	
	public void init() {
		state = ShooterLoadingActuatorState.IDLE;
	}
	
	public void update() {
		System.out.println("Shooter Loading Actuator isRetracted: " + isRetracted);
	}
	
	public void disable() {
		state = ShooterLoadingActuatorState.DISABLED;		
	}
	
	/**
	 * Sets the loading actuator state, calling commands as appropriate
	 * @param state incoming ShooterLoadingActuatorState
	 * @param args any arguments associated with the command
	 */
	public void setState(ShooterLoadingActuatorState state, float ... args) {
		if(state != ShooterLoadingActuatorState.DISABLED) {
			this.state = state;
		}
		if(state == ShooterLoadingActuatorState.EXTEND) {
			isRetracted = false;
			Strongback.submit(new ShooterLoadingActuatorExtendCommand(parent));
		}
		else if(state == ShooterLoadingActuatorState.RETRACT) {
			isRetracted = true;
			Strongback.submit(new ShooterLoadingActuatorRetractCommand(parent));
		}
	}
	
	public boolean isFullyRetracted() {
		 return isRetracted;
	}
}
