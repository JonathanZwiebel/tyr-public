package com.palyrobotics.subsystem.shooter.commands;

import com.palyrobotics.robot.InputSystems;
import org.strongback.command.Command;
import org.strongback.command.Requirable;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;

import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterController.ShooterState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController.ShooterArmState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLoadingActuatorController.ShooterLoadingActuatorState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;

/**
 * @author Paly Robotics Programming Red Module
 * 
 * This is the base command for teleop movement and should only be called once when teleop is entered
 * This command should only be called once
 */
public class FullShooterTeleopCommand extends Command implements Requirable {
	ShooterController controller;
	SwitchReactor reactor;
	InputSystems input;
	
	/**
	 * @param control the ShooterController Object
	 * The Constructor for ShooterTeleopMovement
	 */
	public FullShooterTeleopCommand(ShooterController controller) {
		super(controller);
		this.controller = controller;
		input = controller.input;
		reactor = Strongback.switchReactor();
	}
	
	@Override
	/**
	 * Initializes the command by setting the arm controller state to IDLE
	 */
	public void initialize() {
		controller.armController.setState(ShooterArmController.ShooterArmState.TELEOP);
	}
	
	@Override
	/**
	 * Executes on loop and will run until interrupted. Sets up all of the switch reactors which will call lambdas
	 * for the commands
	 */
	public boolean execute() {
		// TODO: Break switch reactors	
		if(controller.armController.state == ShooterArmState.IDLE) {
			controller.armController.setState(ShooterArmState.TELEOP);
		}
		
		reactor.onTriggered(input.getOperatorStick().getButton(ShooterConstants.SHOOTER_FIRE_SEQUENCE_BUTTON), ()->callFireSequence());
		reactor.onTriggered(input.getOperatorStick().getButton(ShooterConstants.SHOOTER_LOAD_SEQUENCE_BUTTON), ()->callLoadSequence());
		reactor.onTriggered(input.getOperatorStick().getButton(ShooterConstants.SHOOTER_LOADING_BUTTON), ()->callToggleLoader());
		reactor.onTriggered(input.getOperatorStick().getButton(ShooterConstants.SHOOTER_LOCKING_BUTTON), ()->callToggleLock());
		return false;
	}

	/**
	 * Calls the shooter fire sequence to get to the FullShooterFireCommand
	 */
	public void callFireSequence() {
		controller.setState(ShooterState.FIRE);
	}
	
	/**
	 * Calls the shooter load sequence to get to the FullShooterLoadCommand
	 */
	public void callLoadSequence() {
		controller.setState(ShooterState.LOAD);
	}

	/**
	 * Toggles the loader between loaded and extended, setting the loading actuator command to either
	 * ShooterLoadingActuatorExtendCommand or ShooterLoadingActuatorRetractCommand
	 */
	public void callToggleLoader() {
		if (controller.loadingActuatorController.isFullyRetracted()){
			controller.loadingActuatorController.setState(ShooterLoadingActuatorState.EXTEND);
		}
		else{
			controller.loadingActuatorController.setState(ShooterLoadingActuatorState.RETRACT);
		}
	}
	
	/**
	 * Toggles the locking latch between locked and unlocked, setting the locking actuator command to either
	 * ShooterLockingActuatorLockCommand or ShooterLockingActuatorUnlockCommand
	 */
	public void callToggleLock() {
		if (controller.lockingActuatorController.isLocked()){
			controller.lockingActuatorController.setState(ShooterLockingActuatorState.UNLOCK);
		}
		else{
			controller.lockingActuatorController.setState(ShooterLockingActuatorState.LOCK);

		}
	}
}
