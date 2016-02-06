package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

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
		latch = controller.systems.getLatch();
	}

	@Override
	public boolean execute() {
		if (controller.lockingActuatorController.isLocked()) {
			return true;
		}
		
		if (latch.isStopped()) {
			latch.extend();
		}
		
		return false;
	}
}