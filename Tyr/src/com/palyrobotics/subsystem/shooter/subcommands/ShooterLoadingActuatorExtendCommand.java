package com.palyrobotics.subsystem.shooter.subcommands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLoadingActuatorController.ShooterLoadingActuatorState;

/**
 * Extends the loading actuator to the shooting state
 * @author Paly Robotics Programming Red Module
 */
public class ShooterLoadingActuatorExtendCommand extends Command {
	private ShooterController controller;
	private Solenoid piston;

	public ShooterLoadingActuatorExtendCommand(ShooterController controller) {
		super(controller.loadingActuatorController);
		this.controller = controller;
		this.piston = controller.systems.getLoadingActuator();
	}
	
	@Override
	public void initialize() {
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLoadingActuatorExtendCommand initalized.");
	}
	
	/**
	 * If the loading actuator is extended end the command, otherwise extend
	 */
	@Override
	public boolean execute() {				
		piston.extend();
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLoadingActuatorExtendCommand is ending.");
		return true;
	}
	
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	@Override
	public void end() {
		controller.loadingActuatorController.setState(ShooterLoadingActuatorState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLoadingActuatorExtendCommand ended.");
	}
	
	@Override
	public void interrupted() {
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLoadingActuatorExtendCommand interrupted.");
	}
}