package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

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
		latch = controller.systems.getLatch();
	}

	@Override
	public boolean execute() {
		if (!controller.lockingActuatorController.isLocked()) {
			return true;
		}
		
		if (latch.isStopped()) {
			latch.retract();
		}
		
		return false;
	}
}