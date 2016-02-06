package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

/**
 * @author Paly Robotics Programming Red Module
 *
 * Retracts the loading actuator to the firing state
 */
public class ShooterLoadingActuatorRetractCommand extends Command {
	private ShooterController controller;
	private Solenoid piston;
	
	public ShooterLoadingActuatorRetractCommand(ShooterController controller) {
		super(controller.loadingActuatorController);
		this.controller = controller;
		this.piston = controller.systems.getPiston();
	}
	
	@Override
	public boolean execute() {		
		if (controller.loadingActuatorController.isFullyRetracted()) {
			return true;
		}
		
		if (piston.isStopped()) {
			piston.retract();
		}
		
		return false;
	}
}