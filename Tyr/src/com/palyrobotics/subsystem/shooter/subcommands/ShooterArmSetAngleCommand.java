package com.palyrobotics.subsystem.shooter.subcommands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.command.Command;
import org.strongback.components.AngleSensor;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController;

/**
 * Uses PID to set the arm angle to an input value
 * @author Paly Robotics Programming Red Module
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
		armEncoder = input.getShooterArmPotentiometer();
	}

	/**
	 * Initializes this command by setting the previous error to the current error
	 */
	@Override
	public void initialize() {
		previousError = targetAngle - armEncoder.getAngle();
    	Logger.getLogger("Central").log(Level.INFO, "ShooterArmSetAngleCommand initalized.");
	}
	
	/**
	 * Executes the command through a PD loop that will end when the error is within the ARM_PROPORTIONAL_ME
	 * range and the derivative is within ARM_DERIVATE_ME of zero.
	 */
	@Override
	public boolean execute() {
		double angle = armEncoder.getAngle();
		double error = targetAngle - angle;
		double derivative = (error - previousError) / RobotConstants.UPDATES_PER_SECOND;

		Logger.getLogger("Central").log(Level.INFO, "Angle: " + angle);
		
		if(Math.abs(error) < ShooterConstants.ARM_PROPORTIONAL_ME && Math.abs(derivative) < ShooterConstants.ARM_DERIVATIVE_ME) {
	    	Logger.getLogger("Central").log(Level.INFO, "ShooterArmSetAngleCommand is ending.");
			return true;
		} 
		else {
			double rawSpeed = ShooterConstants.ARM_kP * error + ShooterConstants.ARM_kD * derivative;
			double limitedSpeed = Math.min(Math.max(rawSpeed, -ShooterConstants.SHOOTER_ARM_SET_ANGLE_SPEED_LIMIT), ShooterConstants.SHOOTER_ARM_SET_ANGLE_SPEED_LIMIT);
			controller.systems.getArmMotor().setSpeed(limitedSpeed);
			previousError = error;
	    	Logger.getLogger("Central").log(Level.FINE, "ShooterArmSetAngleCommand is continuing.");
			return false;
		}
	}
	
	@Override
	public void interrupted() { 
    	Logger.getLogger("Central").log(Level.INFO, "ShooterSetArmAngleCommand interrupted");
	}
	
	/**
	 * At the end of the PD loops, sets the state back to IDLE
	 */
	@Override
	public void end() {
		controller.armController.setState(ShooterArmController.ShooterArmState.IDLE);
    	Logger.getLogger("Central").log(Level.INFO, "ShooterArmSetAngleCommand ended.");
	}
}
