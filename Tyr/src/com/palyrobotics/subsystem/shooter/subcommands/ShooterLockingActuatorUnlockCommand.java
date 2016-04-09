package com.palyrobotics.subsystem.shooter.subcommands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;

/**
 * This takes the latch off the arm
 * Checks to make sure the latch is taken off
 * @author Paly Robotics Programming Red Modul
 */
public class ShooterLockingActuatorUnlockCommand extends Command {
	private ShooterController controller;
	private Solenoid latch;
	
	public ShooterLockingActuatorUnlockCommand(ShooterController controller) {
		super(controller.lockingActuatorController);
		this.controller = controller;
		latch = controller.systems.getLockingActuator();
	}

	@Override
	public void initialize() {
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorUnlockCommand initalized.");
	}
	
	/**
	 * If the locking actuator is unlocked, the command will end, otherwise it will
	 * attempt the lock by retracting
	 */
	@Override
	public boolean execute() {
		latch.retract();
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorUnlockCommand is ending.");
		return true;
	}
	
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	@Override
	public void end() {
		controller.lockingActuatorController.setState(ShooterLockingActuatorState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorUnlockCommand ended.");
	}
	
	@Override
	public void interrupted() {
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLockingActuatorUnlockCommand interrupted.");
	}
}