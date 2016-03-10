package com.palyrobotics.subsystem.shooter.subcommands;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.components.Motor;

import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;

/**
 * Moves the arm during normal teleop states, this command will never end
 * @author Paly Robotics Programming Red Module
 */
public class ShooterArmTeleopCommand extends Command {
	private static final float DEAD_SPEED = 0.0f;
	private static final float DEAD_PITCH = 0.0f;
	
	private ShooterController controller;
	private Motor motor;
	private double angle;
	private double pitch;

	public ShooterArmTeleopCommand(ShooterController controller) {
		super(controller.armController);
		this.controller = controller;
		motor = controller.systems.getArmMotor();
	}
	
	/**
	 * Moves the arm based on the pitch read from the operator stick
	 * Will not move beyond the maximum or minimum zones
	 */
	@Override
	public boolean execute() {
		angle = controller.input.getShooterArmPotentiometer().getAngle();
		pitch = controller.input.getShooterStick().getPitch().read();
		
		motor.setSpeed(0.5*pitch);
		
		return false;
	}
	
	@Override
	public void interrupted() {
		Strongback.logger().info("The command ShooterArmTeleopCommand was interrupted");
	}
}