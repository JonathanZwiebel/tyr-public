package com.palyrobotics.subsystem.shooter.subcommands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLoadingActuatorController.ShooterLoadingActuatorState;

/**
 * Retracts the loading actuator to the firing state
 * @author Paly Robotics Programming Red Module
 */
public class ShooterLoadingActuatorRetractCommand extends Command {
	private ShooterController controller;
	private Solenoid piston;
	
	public ShooterLoadingActuatorRetractCommand(ShooterController controller) {
		super(controller.loadingActuatorController);
		this.controller = controller;
		this.piston = controller.systems.getLoadingActuator();
	}
	
	@Override
	public void initialize() {
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLoadingActuatorRetractCommand initalized.");
	}
	
	/**
	 * If the loading actuator is retracted, the command will end, otherwise it will
	 * retract
	 */
	@Override
	public boolean execute() {		
		piston.retract();
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLoadingActuatorRetractCommand is ending.");
		return true;
	}
	
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	@Override
	public void end() {
		controller.loadingActuatorController.setState(ShooterLoadingActuatorState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLoadingActuatorRetractCommand ended.");
	}
	
	@Override
	public void interrupted() {
    	Logger.getLogger("Central").log(Level.INFO, "ShooterLoadingActuatorRetractCommand interrupted.");
	}
}