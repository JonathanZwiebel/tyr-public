package com.palyrobotics.subsystem.shooter.subcommands;

import org.strongback.command.Command;
import org.strongback.components.AngleSensor;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController;

/**
 * @author Paly Robotics Programming Red Module
 * 
 * Uses PID to set the arm angle to an input value
 */
public class ShooterArmSetAngleCommand extends Command {
	private ShooterController controller;
	private InputSystems input;
	private AngleSensor armEncoder;

	private Double targetAngle;
	private Double previousError;
	
	public ShooterArmSetAngleCommand(ShooterController controller, double angle) {
		super(controller.armController);
		this.controller = controller;
		input = controller.input;
		targetAngle = angle;
		armEncoder = input.getShooterArmAngleSensor();
	}

	@Override
	/**
	 * Intializes this command by setting the previous error to the current error
	 */
	public void initialize() {
		previousError = targetAngle - armEncoder.getAngle();
	}
	
	@Override
	/**
	 * Executes the command through a PD loop that will end when the error is within the ARM_PROPORTIONAL_ME
	 * range and the derivative is within ARM_DERIVATE_ME of zero.
	 */
	public boolean execute() {
		double angle = armEncoder.getAngle();
		double error = targetAngle - angle;
		double derivative = (error - previousError) / RobotConstants.UPDATES_PER_SECOND;

		System.out.println("Angle: " + angle);
		
		if(Math.abs(error) < ShooterConstants.ARM_PROPORTIONAL_ME && Math.abs(derivative) < ShooterConstants.ARM_DERIVATIVE_ME) {
			return true;
		} 
		
		else {
			controller.systems.getArmMotor().setSpeed(ShooterConstants.ARM_kP * error + ShooterConstants.ARM_kD * derivative);
			previousError = error;
			return false;
		}
	}
	
	@Override
	public void interrupted() { 
		System.out.println("ShooterSetArmAngleCommand interuppted");
	}
	
	/**
	 * At the end of the PD loops, sets the state back to IDLE
	 */
	@Override
	public void end() {
		controller.armController.setState(ShooterArmController.ShooterArmState.IDLE);
	}
}
