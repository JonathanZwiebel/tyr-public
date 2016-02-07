package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;

/**
 * @author Paly Robotics Programming Red Module
 * 
 * Puts the latch on the arm
 * Makes sure the latch is put on
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
	/**
	 * If the locking actuator is locked, the command will end, otherwise it will
	 * attempt the lock by extending
	 */
	public boolean execute() {
		if (controller.lockingActuatorController.isLocked()) {
			return true;
		}
		
		latch.extend();
		return false;
	}
	
	@Override
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	public void end() {
		controller.lockingActuatorController.setState(ShooterLockingActuatorState.IDLE);
	}
}