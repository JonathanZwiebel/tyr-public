package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.components.Motor;

import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterArmController;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

/**
 * @author Paly Robotics Programming Red Module
 *
 * Moves the arm during normal teleop states
 */
public class ShooterArmTeleopCommand extends Command {
	private ShooterController controller;
	private Motor motor;
	private double angle;
	private double pitch;

	public ShooterArmTeleopCommand(ShooterController controller) {
		super(controller.armController);
		this.controller = controller;
		motor = controller.systems.getMotor();
		System.out.println("Instantiated ShooterArmTeleopCommand: " + Thread.currentThread().getStackTrace()[2].getMethodName());
	}
	
	@Override
	public boolean execute() {
		angle = controller.input.getShooterPotentiometer().getAngle();
		pitch = controller.input.getOperatorStick().getPitch().read();
		System.out.println("Read Angle: " + controller.input.getShooterPotentiometer().getAngle());
		
		if((angle >= ShooterConstants.MAX_ANGLE && pitch > 0) || (angle <= ShooterConstants.MIN_ANGLE && pitch < 0)) {
			System.out.println("Invalid motor set speed");
			motor.setSpeed(0);
		}
		else {
			System.out.println("Valid motor set speed: " + pitch);
			motor.setSpeed(pitch);
		}
		return false;
	}
	
	@Override
	public void interrupted() {
		Strongback.logger().info("The command ShooterArmTeleopCommand was interrupted.");
	}
	
	@Override
	public void end() {
		controller.systems.getMotor().setSpeed(0);
		controller.armController.state = ShooterArmController.ShooterArmState.IDLE;
	}
}