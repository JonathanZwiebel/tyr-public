package com.palyrobotics.subsystem.shooter.subcommands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;

/**
 * Puts the latch on the arm
 * Makes sure the latch is put on
 * @author Paly Robotics Programming Red Module
 */
public class ShooterLockingActuatorLockCommand extends Command {
	private ShooterController controller;
	private Solenoid latch;
	
	public ShooterLockingActuatorLockCommand(ShooterController controller) {
		super(controller.lockingActuatorController);
		this.controller = controller;
		latch = controller.systems.getLockingActuator();
	}
	
	@Override
	public void initialize() {
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorLockCommand initalized.");
	}
	
	/**
	 * If the locking actuator is locked, the command will end, otherwise it will
	 * attempt the lock by extending
	 */
	@Override
	public boolean execute() {
		latch.extend();
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorLockCommand is ending.");
		return true;
	}
	
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	@Override
	public void end() {
		controller.lockingActuatorController.setState(ShooterLockingActuatorState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorLockCommand ended.");
	}
	
	@Override
	public void interrupted() {
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorLockCommand interrupted.");
	}
}