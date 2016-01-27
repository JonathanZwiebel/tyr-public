package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import com.palyrobotics.subsystem.shooter.*;
import org.strongback.components.ui.ContinuousRange;

import com.palyrobotics.robot.InputSystems;


public class UnchckedTeleopCommandSecond extends Command {
	ShooterController controller;
	ContinuousRange range;
	InputSystems input;
	ShooterSystems output;


	public UnchckedTeleopCommandSecond(ShooterController controller) {
		this.controller = controller;
		input = controller.input;
		output = controller.systems;
	}
	
	
	@Override
	public void initialize() {
		range = input.getOperatorStick().getPitch();
		controller.state = ShooterController.ShooterState.UNLOCKED;	
	}
	
	
	@Override
	public boolean execute() {
		output.getMotor().setSpeed(range.read());
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