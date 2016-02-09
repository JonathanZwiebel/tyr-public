package com.palyrobotics.subsystem.shooter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.Strongback;
import org.strongback.command.Requirable;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.shooter.commands.FullShooterFireCommand;
import com.palyrobotics.subsystem.shooter.commands.FullShooterLoadCommand;
import com.palyrobotics.subsystem.shooter.commands.FullShooterTeleopCommand;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLoadingActuatorController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLockingActuatorController;

/**
 * The primary controller for the shooter, divided into an arm, loading actuator, and locking actuator
 * This controller will delegate the states of the three subcontrollers as well as acting as the interface
 * with the robot controller. 
 * 
 * @author Paly Robotics Programming Red Module
 */
public class ShooterController implements Requirable {
	public ShooterSystems systems;
	public InputSystems input;
	
	private ShooterState state;	
	
	public ShooterArmController armController;
	public ShooterLockingActuatorController lockingActuatorController;
	public ShooterLoadingActuatorController loadingActuatorController;
	
	public enum ShooterState {
		IDLE, // The initial state of the shooter
		TELEOP, // Standard teleop controls
		FIRE, // A fire sequence that will go from loaded to fired
		LOAD, // A loading sequence that will retract the arm and fill the loading actuator
		DISABLED // The disabled state
	}

	public ShooterController(ShooterSystems systems, InputSystems input) {
		this.systems = systems;
		this.input = input;
		armController = new ShooterArmController(this);
		lockingActuatorController = new ShooterLockingActuatorController(this);
		loadingActuatorController = new ShooterLoadingActuatorController(this);
	}
	
	public void init() {
		state = ShooterState.IDLE;
		armController.init();
		lockingActuatorController.init();
		loadingActuatorController.init();   	
		Logger.getLogger("Central").log(Level.INFO, "The ShooterController was initialized.");
	}
	
	/**
	 * Calls all of the subcontroller updates and will set the state to TELEOP if currently IDLE
	 */
	public void update() {
		if(state == ShooterState.IDLE) {
			setState(ShooterState.TELEOP);
		}
		
		armController.update();
		lockingActuatorController.update();
		loadingActuatorController.update();
	}
	
	public void disable() {
		state = ShooterState.DISABLED;
		armController.disable();
		lockingActuatorController.disable();
		loadingActuatorController.disable();
		Logger.getLogger("Central").log(Level.INFO, "The ShooterController was disabled.");
	}
	
	/**
	 * Sets the shooter state, calling commands as appropriate
	 * @param state incoing ShooterState
	 */
	public void setState(ShooterState state, float ... args) {
		if(state != ShooterState.DISABLED) {
			this.state = state;
		}
		if(state == ShooterState.TELEOP) {
			Strongback.submit(new FullShooterTeleopCommand(this));
		}
		else if(state == ShooterState.FIRE) {
			Strongback.submit(new FullShooterFireCommand(this));
		}
		else if(state == ShooterState.LOAD) {
			Strongback.submit(new FullShooterLoadCommand(this));
		}
	}
	
	public ShooterState getState() {
		return state;
	}
}
