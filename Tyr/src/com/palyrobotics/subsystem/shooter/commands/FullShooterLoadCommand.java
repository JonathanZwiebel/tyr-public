package com.palyrobotics.subsystem.shooter.commands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterController.ShooterState;

/**
 * @author Paly Robotics Programming Red Module
 * 
 * Loads the arm when the ball is in the accumulator to shoot
 */
public class FullShooterLoadCommand extends Command {
	public ShooterController controller;
	
	public FullShooterLoadCommand(ShooterController controller) {
		super(controller);
		this.controller = controller;
	}
	
	@Override
	/**
	 * Will create a queue of states that should be set relating to shooter items
	 */
	public boolean execute() {
		// TODO: Implement with queue not a command group
		return true;
	}
	
	@Override
	public void end() {
		controller.setState(ShooterState.IDLE);
	}
}
