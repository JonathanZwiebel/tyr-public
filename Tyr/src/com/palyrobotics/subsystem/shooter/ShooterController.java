package com.palyrobotics.subsystem.shooter;

import org.strongback.Strongback;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.shooter.shootercommands.UncheckedLimitedTeleopCommand;
import com.palyrobotics.subsystem.shooter.shootercommands.UncheckedTeleopCommand;

public class ShooterController implements Requirable {
	public ShooterSystems systems;
	public InputSystems input;
		
	public enum ShooterState {
		IDLE,
		UNLOCKED,
		DISABLED
	}
	
	public ShooterState state;
	public ShooterController(ShooterSystems systems, InputSystems input) {
		this.systems = systems;
		this.input = input;
	}
	
	public void init() {
		state = ShooterState.IDLE;
	}
	
	public void update() {
		if(state == ShooterState.UNLOCKED) {
			Strongback.submit(new UncheckedLimitedTeleopCommand(this));
		}
	}
	
	public void disable() {
		state = ShooterState.DISABLED;
	}
}
