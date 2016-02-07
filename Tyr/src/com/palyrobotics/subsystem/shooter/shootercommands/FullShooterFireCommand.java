package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController.ShooterState;

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
	/**
	 * Will create a queue of states that should be set relating to shooter items
	 */
	public boolean execute() {
		CommandGroup fire = CommandGroup.runSequentially(new ShooterLockingActuatorUnlockCommand(controller));
		Strongback.submit(fire);
		return true;
	}
	
	@Override
	public void end() {
		controller.setState(ShooterState.IDLE);
	}
}
