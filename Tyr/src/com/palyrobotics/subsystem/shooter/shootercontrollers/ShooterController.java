package com.palyrobotics.subsystem.shooter.shootercontrollers;

import org.strongback.Strongback;
import org.strongback.command.Requirable;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.shooter.ShooterSystems;
import com.palyrobotics.subsystem.shooter.shootercommands.FullShooterFireCommand;
import com.palyrobotics.subsystem.shooter.shootercommands.FullShooterLoadCommand;
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
		TELEOP,
		FIRE,
		LOAD,
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
		if(state == ShooterState.IDLE) {
			System.out.println("Shooter Controller in IDLE auto set to TELE" );
			setState(ShooterState.TELEOP);
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
	
	public void setState(ShooterState state) {
		if(state != ShooterState.DISABLED) {
			this.state = state;
		}
		if(state == ShooterState.TELEOP) {
			Strongback.submit(new FullShooterTeleopCommand(this));
		}
		else if(state == ShooterState.FIRE) {
			Strongback.submit(new FullShooterFireCommand(this));
		}
		else if(state == ShooterState.LOAD) {
			Strongback.submit(new FullShooterLoadCommand(this));
		}
	}
}
