package com.palyrobotics.subsystem.shooter.subcommands;

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

	/**
	 * If the locking actuator is unlocked, the command will end, otherwise it will
	 * attempt the lock by retracting
	 */
	@Override
	public boolean execute() {
		if (!controller.lockingActuatorController.isLocked()) {
			return true;
		}
		
		latch.extend();
		return false;
	}
	
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	@Override
	public void end() {
		controller.lockingActuatorController.setState(ShooterLockingActuatorState.IDLE);
	}
}