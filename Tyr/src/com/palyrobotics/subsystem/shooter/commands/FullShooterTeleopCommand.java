package com.palyrobotics.subsystem.shooter.commands;

import com.palyrobotics.robot.InputSystems;
import org.strongback.command.Command;
import org.strongback.command.Requirable;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;

import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcontrollers.*;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorExtendCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLoadingActuatorRetractCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorLockCommand;
import com.palyrobotics.subsystem.shooter.subcommands.ShooterLockingActuatorUnlockCommand;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController.ShooterArmState;

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
		if(controller.loadingActuatorController.state == ShooterLoadingActuatorController.ShooterLoadingActuatorState.IDLE) {
			if(input.getShooterStick().getButton(ShooterConstants.LOADING_ACTUATOR_EXTEND_OPERATOR_STICK_BUTTON).isTriggered()) {
				Strongback.submit(new ShooterLoadingActuatorExtendCommand(controller));
			}
			if(input.getShooterStick().getButton(ShooterConstants.LOADING_ACUTATOR_RETRACT_OPERATOR_STICK_BUTTON).isTriggered()) {
				Strongback.submit(new ShooterLoadingActuatorRetractCommand(controller));
			}
		}
		if(controller.lockingActuatorController.state == ShooterLockingActuatorController.ShooterLockingActuatorState.IDLE) {
			if(input.getShooterStick().getButton(ShooterConstants.LOCKING_ACUTATOR_LOCK_OPERATOR_STICK_BUTTON).isTriggered()) {
				Strongback.submit(new ShooterLockingActuatorLockCommand(controller));
			}
			if(input.getShooterStick().getButton(ShooterConstants.LOCKING_ACUTATOR_UNLOCK_OPERATOR_STICK_BUTTON).isTriggered()) {
				Strongback.submit(new ShooterLockingActuatorUnlockCommand(controller));
			}
		}
 		return false;
	}
}
