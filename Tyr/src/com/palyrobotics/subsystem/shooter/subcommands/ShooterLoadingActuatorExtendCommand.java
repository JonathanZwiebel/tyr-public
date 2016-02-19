package com.palyrobotics.subsystem.shooter.subcommands;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.ShooterConstants;
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
		piston.extend();
		System.out.println("Loader Extend");
		return controller.input.getShooterStick().getButton(ShooterConstants.SHOOTER_ACTUATOR_TERMINATE_COMMAND_OPERATOR_STICK_BUTTON).isTriggered();
	}
	
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	@Override
	public void end() {
		controller.loadingActuatorController.setState(ShooterLoadingActuatorState.IDLE);
	}
}