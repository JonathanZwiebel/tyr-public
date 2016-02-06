package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.Strongback;
import org.strongback.command.Command;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

/** 
 * @author Paly Robotics Programming Red Module
 * 
 * Shoots the ball 
 */
public class FullShooterFireCommand extends Command {
	public ShooterController controller;
	
	public FullShooterFireCommand(ShooterController controller) {
		super(controller);
		this.controller = controller;
	}
	
	@Override
	// TODO: Is this just a wrapper?
	public boolean execute() {
		if (controller.lockingActuatorController.isLocked()) { 
			Strongback.submit(new ShooterLockingActuatorUnlockCommand(controller));
			return true;	
			
		}
		return false;
	}
}
