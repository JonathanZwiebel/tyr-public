package com.palyrobotics.subsystem.shooter;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;

public class UncheckedTeleopCommand extends Command {
	
	ContinuousRange operator_pitch;
	ShooterController controller;
	ShooterSystems output;
	
	public UncheckedTeleopCommand(ShooterController controller, ShooterSystems output) {
		controller.state = ShooterController.ShooterState.UNLOCKED;
		this.controller = controller;
		this.output = output;
	}
	
	@Override
	public void initialize() {
		operator_pitch = controller.input.getOperatorStick().getPitch();
	}
	
	@Override
	public boolean execute() {
		double input_value = operator_pitch.read();
		output.getMotor().setSpeed(input_value);
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
