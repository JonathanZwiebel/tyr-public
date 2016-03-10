package com.palyrobotics.subsystem.shooter.commands;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.robot.InputSystems;
import org.strongback.command.Command;
import org.strongback.command.Requirable;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;

import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcontrollers.*;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorExtendCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorRetractCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorLockCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorUnlockCommand;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController.ShooterArmState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLoadingActuatorController.ShooterLoadingActuatorState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;

/**
 * This is the base command for teleop movement and should only be called once when teleop is entered
 * This command should only be called once
 * 
 * @author Paly Robotics Programming Red Module
 * TODO[Major]: Allow for breakout into FullShooterFireCommand and FullShooterLoadCOmmand
 */
public class FullShooterTeleopCommand extends Command implements Requirable {
	ShooterController controller;
	SwitchReactor reactor;
	InputSystems input;
	
	/**
	 * The Constructor for ShooterTeleopMovement
	 * @param control the ShooterController Object
	 */
	public FullShooterTeleopCommand(ShooterController controller) {
		super(controller);
		this.controller = controller;
		input = controller.input;
		reactor = Strongback.switchReactor();
	}

	/**
	 * Initializes the command by setting the arm controller state to TELEOP
	 */
	@Override
	public void initialize() {
		controller.armController.setState(ShooterArmController.ShooterArmState.TELEOP);
	}
	
	/**
	 * Executes on loop and will run until interrupted. Sets up all of the switch reactors which will call lambdas
	 * for the commands
	 */
	@Override
	public boolean execute() {
		if(controller.armController.state == ShooterArmState.IDLE) {
			controller.armController.setState(ShooterArmState.TELEOP);
		}
		if(input.getShooterStick().getButton(Buttons.SHOOTER_ARM_HOLD_OPERATOR_STICK_BUTTON).isTriggered()) {
			controller.armController.setState(ShooterArmState.HOLD);
		}
		
		if(input.getShooterStick().getButton(Buttons.LOADING_ACTUATOR_EXTEND_OPERATOR_STICK_BUTTON).isTriggered()) {
			controller.loadingActuatorController.setState(ShooterLoadingActuatorState.EXTEND);
		}
		else if(input.getShooterStick().getButton(Buttons.LOADING_ACTUATOR_RETRACT_OPERATOR_STICK_BUTTON).isTriggered()) {
			controller.loadingActuatorController.setState(ShooterLoadingActuatorState.RETRACT);
		}
		if(input.getShooterStick().getButton(Buttons.LOCKING_ACTUATOR_LOCK_OPERATOR_STICK_BUTTON).isTriggered()) {
			controller.lockingActuatorController.setState(ShooterLockingActuatorState.LOCK);
		}
		else if(input.getShooterStick().getButton(Buttons.LOCKING_ACTUATOR_UNLOCK_OPERATOR_STICK_BUTTON).isTriggered()) {
			controller.lockingActuatorController.setState(ShooterLockingActuatorState.UNLOCK);
		}
 		return false;
	}
}
