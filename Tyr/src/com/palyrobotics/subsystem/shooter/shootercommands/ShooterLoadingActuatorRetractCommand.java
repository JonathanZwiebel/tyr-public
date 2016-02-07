package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterLoadingActuatorController.ShooterLoadingActuatorState;

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
		this.piston = controller.systems.getLoadingActuator();
	}
	
	@Override
	/**
	 * If the loading actuator is retracted, the command will end, otherwise it will
	 * retract
	 */
	public boolean execute() {		
		if (controller.loadingActuatorController.isFullyRetracted()) {
			return true;
		}
		
		piston.retract();
		return false;
	}
	
	@Override
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	public void end() {
		controller.loadingActuatorController.setState(ShooterLoadingActuatorState.IDLE);
	}
}