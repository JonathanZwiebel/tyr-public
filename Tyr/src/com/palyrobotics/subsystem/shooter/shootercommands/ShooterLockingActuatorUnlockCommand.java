package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;

/**
 * @author Paly Robotics Programming Red Module
 * 
 * This takes the latch off the arm
 * Checks to make sure the latch is taken off
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
	/**
	 * If the locking actuator is unlocked, the command will end, otherwise it will
	 * attempt the lock by retracting
	 */
	public boolean execute() {
		if (!controller.lockingActuatorController.isLocked()) {
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