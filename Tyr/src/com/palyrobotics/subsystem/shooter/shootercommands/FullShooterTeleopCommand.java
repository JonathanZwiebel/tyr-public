package com.palyrobotics.subsystem.shooter.shootercommands;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.shooter.*;
import org.strongback.command.Command;
import org.strongback.command.Requirable;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterArmController;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

/**
 * @author Paly Robotics Programming Red Module
 * 
 * This is the base command for teleop movement and should only be called once when teleop is entered
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
		reactor = Strongback.switchReactor();
		input = controller.input;
	}
	
	@Override
	public boolean execute() {
		// TODO: Break switch reactors
		if(controller.armController.state == ShooterArmController.ShooterArmState.IDLE) {
			Strongback.submit(new ShooterArmTeleopCommand(controller));
		}
		reactor.onTriggered(input.getOperatorStick().getTrigger(), ()->callFireShooter());
		reactor.onTriggered(input.getOperatorStick().getButton(ShooterConstants.SHOOTER_LOADING_BUTTON), ()->callToggleLoader());
		reactor.onTriggered(input.getOperatorStick().getButton(ShooterConstants.SHOOTER_LOCKING_BUTTON), ()->callToggleLock());
		return false;
	}
	

	public void callFireShooter() {
		Strongback.submit(new FullShooterFireCommand(controller));
	}
	

	public void callToggleLoader() {
		if (!controller.loadingActuatorController.isFullyRetracted()){
			Strongback.submit(new ShooterLoadingActuatorRetractCommand(controller));
		}
		else{
			Strongback.submit(new ShooterLoadingActuatorExtendCommand(controller));
		}
	}
	
	public void callToggleLock() {
		if (!controller.lockingActuatorController.isLocked()){
			Strongback.submit(new ShooterLockingActuatorLockCommand(controller));
		}
		else{
			Strongback.submit(new ShooterLockingActuatorUnlockCommand(this.controller));
		}
	}
}
