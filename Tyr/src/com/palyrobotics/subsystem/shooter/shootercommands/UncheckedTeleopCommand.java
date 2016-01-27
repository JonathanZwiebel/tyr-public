package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterSystems;

public class UncheckedTeleopCommand extends Command {
	ContinuousRange operator_pitch;
	ShooterController controller;
	ShooterSystems output;
	InputSystems input;
	
	public UncheckedTeleopCommand(ShooterController controller) {
		controller.state = ShooterController.ShooterState.UNLOCKED;
		this.controller = controller;
		this.output = controller.systems;
		this.input = controller.input;
	}
	
	@Override
	public void initialize() {
		operator_pitch = input.getOperatorStick().getPitch();

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
