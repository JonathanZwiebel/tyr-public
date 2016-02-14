package com.palyrobotics.subsystem.shooter.subcommands;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;

import com.palyrobotics.subsystem.shooter.ShooterConstants;
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
	
	/**
	 * If the loading actuator is retracted, the command will end, otherwise it will
	 * retract
	 */
	@Override
	public boolean execute() {		
		piston.retract();
		System.out.println("Loading Retract");
		return controller.input.getOperatorStick().getButton(ShooterConstants.SHOOTER_ACTUATOR_TERMINATE_COMMAND_OPERATOR_STICK_BUTTON).isTriggered();
	}
	
	/**
	 * At the end sets the loading actuator state back to IDLE
	 */
	@Override
	public void end() {
		controller.loadingActuatorController.setState(ShooterLoadingActuatorState.IDLE);
	}
}