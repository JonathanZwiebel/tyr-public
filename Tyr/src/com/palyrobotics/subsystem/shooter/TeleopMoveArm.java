package com.palyrobotics.subsystem.shooter;

import org.strongback.command.Command;
import com.palyrobotics.subsystem.shooter.*;
import org.strongback.components.ui.ContinuousRange;

import com.palyrobotics.robot.InputSystems;


public class TeleopMoveArm extends Command {
	ShooterController controller;
	ContinuousRange angle;
	InputSystems input;


	public TeleopMoveArm(ShooterController controller) {
		this.controller = controller;
	}
	
	
	@Override
	public void initialize() {
		angle = input.getOperatorStick().getPitch();
		controller.state = ShooterController.ShooterState.UNLOCKED;	
	}
	
	
	@Override
	public boolean execute() {
		controller.systems.getMotor().setSpeed(angle.read());
		return false;
	}

	@Override
	public void interrupted() {
		System.out.println("TeleopMoveArm Command interrupted");
	}

	@Override
	public void end() {
		controller.state = ShooterController.ShooterState.IDLE;
	}
}