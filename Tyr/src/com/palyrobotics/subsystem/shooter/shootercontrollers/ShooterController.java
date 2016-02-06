package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.Strongback;
import org.strongback.command.Requirable;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.shooter.ShooterSystems;
import com.palyrobotics.subsystem.shooter.shootercommands.FullShooterTeleopCommand;

public class ShooterController implements Requirable {
	public ShooterSystems systems;
	public InputSystems input;
	
	public ShooterArmController armController;
	public ShooterLockingActuatorController lockingActuatorController;
	public ShooterLoadingActuatorController loadingActuatorController;
	public ShooterState state;	
	
	public enum ShooterState {
		IDLE,
		UNLOCKED,
		DISABLED
	}

	public ShooterController(ShooterSystems systems, InputSystems input) {
		this.systems = systems;
		this.input = input;
		armController = new ShooterArmController(this);
		lockingActuatorController = new ShooterLockingActuatorController(this);
		loadingActuatorController = new ShooterLoadingActuatorController(this);
	}
	
	public void init() {
		state = ShooterState.IDLE;
		armController.init();
		lockingActuatorController.init();
		loadingActuatorController.init();
	}
	
	public void update() {
		if(state == ShooterState.UNLOCKED) {
			Strongback.submit(new FullShooterTeleopCommand(this));
		}
		armController.update();
		lockingActuatorController.update();
		loadingActuatorController.update();
	}
	
	public void disable() {
		state = ShooterState.DISABLED;
		armController.disable();
		lockingActuatorController.disable();
		loadingActuatorController.disable();
	}
}
