package com.palyrobotics.subsystem.shooter;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;

import com.palyrobotics.robot.InputHardware;

public class UncheckedTeleopCommand extends Command {
	
	ContinuousRange operator_pitch;
	ShooterController controller;
	
	public UncheckedTeleopCommand(ShooterController controller) {
		controller.state = ShooterController.ShooterState.UNLOCKED;
	}
	
	@Override
	public void initialize() {
		operator_pitch = InputHardware.operatorStick.getPitch();
	}
	
	@Override
	public boolean execute() {
		double input_value = operator_pitch.read();
		controller.systems.getMotor().setSpeed(input_value);
		return false;
	}

	@Override
	public void interrupted() {
		System.out.println("UncheckedTeleopCommand interrupted");
	}
	
	@Override
	public void end() {
		controller.state = ShooterController.ShooterState.IDLE;
	}
}
