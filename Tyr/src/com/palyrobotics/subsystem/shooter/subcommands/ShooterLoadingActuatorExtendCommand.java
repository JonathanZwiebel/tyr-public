package com.palyrobotics.subsystem.shooter.subcommands;

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
	
	/**
	 * If the loading actuator is extended end the command, otherwise extend
	 */
	@Override
	public boolean execute() {				
		if (!controller.loadingActuatorController.isFullyRetracted()) {
			return true;
		}

		piston.extend();
		return false;
	}
	
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	@Override
	public void end() {
		controller.loadingActuatorController.setState(ShooterLoadingActuatorState.IDLE);
	}
}