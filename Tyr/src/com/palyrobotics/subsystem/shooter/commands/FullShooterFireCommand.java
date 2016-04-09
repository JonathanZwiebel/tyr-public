package com.palyrobotics.subsystem.shooter.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterController.ShooterState;

/** 
 * Shoots the ball
 * 
 * @author Paly Robotics Programming Red Module
 */
public class FullShooterFireCommand extends Command {
	public ShooterController controller;
	
	public FullShooterFireCommand(ShooterController controller) {
		super(controller);
		this.controller = controller;
	}
	
	@Override
	public void initialize() {
		Logger.getLogger("Central").log(Level.INFO, "FullShooterTeleopCommand initialized.");
	}
	
	/**
	 * Will create a queue of states that should be set relating to shooter items
	 */
	@Override
	public boolean execute() {
		// TODO: Implement with queue not a command group
		return true;
	}
	
	@Override
	public void end() {
		controller.setState(ShooterState.IDLE);
		Logger.getLogger("Central").log(Level.INFO, "FullShooterTeleopCommand ended.");
	}
	
	@Override
	public void interrupted() {
		Logger.getLogger("Central").log(Level.INFO, "FullShooterTeleopCommand interrupted.");
	}
}
