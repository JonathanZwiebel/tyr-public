package com.palyrobotics.subsystem.drivetrain.drivetraincommands;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.*;

import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;

/**
* Runs a simplified Chezy Drive for drive practice purposes 
*/
public class ChezyDriveTeleop extends Command {
	private DrivetrainController drivetrain;
	private double old_wheel, quick_stop_acc;
	private final double DEADBAND = 0.02f;
	
	private final int QUICK_TURN_BUTTON = 0;

	public ChezyDriveTeleop(DrivetrainController drivetrain, float scaling) {
		super(drivetrain);
		this.drivetrain = drivetrain;
	}

	@Override
	public void initialize() {
		drivetrain.setDrivetrainState(DrivetrainState.DRIVING_C_TELEOP);
	}

	@Override
	public boolean execute() {
		if (drivetrain.getDrivetrainState() != DrivetrainState.DRIVING_C_TELEOP) {
			return true;
		}
		
		double throttle = drivetrain.getInput().getDriveStick().getPitch().read();
		double wheel = drivetrain.getInput().getTurnStick().getRoll().read();
		boolean quick_turn = drivetrain.getInput().getTurnStick().getButton(QUICK_TURN_BUTTON).isTriggered();
		
		double wheel_non_linear;
		
		if(Math.abs(wheel) < DEADBAND) {
			wheel = 0;
		}
		if(Math.abs(throttle) < DEADBAND) {
			throttle = 0;
		}
		
		double neg_inertia = wheel - old_wheel;
		old_wheel = wheel;

		wheel_non_linear = 0.6f;
		wheel = Math.sin(Math.PI / 2.0 * wheel_non_linear * wheel) / Math.sin(Math.PI / 2.0 * wheel_non_linear);
		wheel = Math.sin(Math.PI / 2.0 * wheel_non_linear * wheel) / Math.sin(Math.PI / 2.0 * wheel_non_linear);

		double left_PWM, right_PWM, over_power;
		double sensitivity;
		double angular_power;
		double linear_power;
		
		double neg_inertia_acc = 0.0;
		double neg_inertia_k;
		
		neg_inertia_k = 4.0;
		sensitivity = 0.75;
		
		double neg_inertia_power = neg_inertia * neg_inertia_k;
		neg_inertia_acc += neg_inertia_power;
		
		wheel += neg_inertia_acc;
		if(neg_inertia_acc > 1) {
			neg_inertia_acc -= 1;
		}
		else if(neg_inertia_acc < -1) {
			neg_inertia_acc += 1;
		}
		else {
			neg_inertia_acc = 0;
		}
		linear_power = throttle;
		
		if(quick_turn) {
			if(Math.abs(linear_power) < 0.2) {
				double alpha = 0.1;
				// Assumed that wheel limit is a reverse deadband 
				double wheel_limit = Math.min(Math.max(wheel, -1.0), 1.0);
				quick_stop_acc = (1 - alpha) * quick_stop_acc + alpha * wheel_limit * 5;
			}
			over_power = 1.0;
			sensitivity = 1.0;
			angular_power = wheel;
		}
		else {
			over_power = 0.0;
			angular_power = Math.abs(throttle) * wheel * sensitivity - quick_stop_acc;
			if(quick_stop_acc > 1) {
				quick_stop_acc -= 1;
			}
			else if(quick_stop_acc < -1) {
				quick_stop_acc += 1;
			}
			else {
				quick_stop_acc = 0;
			}
		}
		right_PWM = left_PWM = linear_power;
		left_PWM += angular_power;
		right_PWM -= angular_power;
		
		if(left_PWM > 1.0) {
			right_PWM -= over_power * (left_PWM - 1.0);
			left_PWM = 1.0;
		} else if (right_PWM > 1.0) {
			left_PWM -= over_power * (right_PWM - 1.0);
			right_PWM = 1.0; 
		} else if(left_PWM < -1.0) {
			right_PWM += over_power * (-1.0 - left_PWM);
			left_PWM = -1.0;
		} else if(right_PWM < -1.0) {
			left_PWM += over_power * (-1.0 - right_PWM);
			right_PWM = -1.0;
		}
		drivetrain.getOutput().getLeftMotor().setSpeed(left_PWM);
		drivetrain.getOutput().getRightMotor().setSpeed(right_PWM);
		return false;
	}

	/**
	 * Sets the motors to zero speed and DrivetrainState to IDLE when drive
	 * teleop is interrupted
	 */
	@Override
	public void interrupted() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);
	}
	
	@Override
	public void end() {
		drivetrain.getOutput().getLeftMotor().setSpeed(0.0);
		drivetrain.getOutput().getRightMotor().setSpeed(0.0);	
	}
}
