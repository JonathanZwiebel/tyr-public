package com.palyrobotics.subsystem.shooter.subcommands;

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

	/**
	 * If the locking actuator is locked, the command will end, otherwise it will
	 * attempt the lock by extending
	 */
	@Override
	public boolean execute() {
		if (controller.lockingActuatorController.isLocked()) {
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