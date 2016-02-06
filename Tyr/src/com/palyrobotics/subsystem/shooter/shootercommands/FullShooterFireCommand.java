package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

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
	public boolean execute() {
		CommandGroup fire = CommandGroup.runSequentially(new ShooterLockingActuatorUnlockCommand(controller));
		Strongback.submit(fire);
		return true;
	}
}
