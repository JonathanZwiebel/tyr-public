package com.palyrobotics.subsystem.shooter;

/**
 * Shooter constants
 * 
 * @author Paly Robotics Programming Red Module
 */
public class ShooterConstants {
	/**
	 * Placeholder value for integer constants
	 */
	public static final int BLANK_INTEGER = 0;
	
	/**
	 * Placeholder value for double constants
	 */
	public static final double BLANK_DOUBLE = 0.0f;
	
	/**
	 * Maximum physical angle of the shooter arm
	 */
	public static final double MAX_ARM_ANGLE = 45.0f;
	
	/**
	 * Minimum physical angle of the shooter arm
	 */
	public static final double MIN_ARM_ANGLE = -10.0f;

	/**
	 * Margin of error for proportion during PID loops
	 */
	public static final double ARM_PROPORTIONAL_ME = 0.05f;
	
	/**
	 * Margin of error for derivative during PID loops
	 */
	public static final double ARM_DERIVATIVE_ME = 0.005f;
	
	/**
	 * Proportion constant for PID loop
	 * TODO: Calibrate this value
	 */
	public static final double ARM_kP = BLANK_DOUBLE;
	
	/**
	 * Derivative constant for PID loop
	 * TODO: Calibrate this value
	 */
	public static final double ARM_kD = BLANK_DOUBLE;

	/**
	 * Button on operator stick to terminate actuator commands
	 */
	public static final int SHOOTER_ACTUATOR_TERMINATE_COMMAND_OPERATOR_STICK_BUTTON = 2;
	
	/**
	 * Button on operator stick to extend loading actuator
	 */
	public static final int LOADING_ACTUATOR_EXTEND_OPERATOR_STICK_BUTTON = 3;
	
	/**
	 * Button on operator stick to retract loading actuator
	 */
	public static final int LOADING_ACUTATOR_RETRACT_OPERATOR_STICK_BUTTON = 5;
	
	/**
	 * Button on operator stick to lock the locking latch
	 */
	public static final int LOCKING_ACUTATOR_LOCK_OPERATOR_STICK_BUTTON = 4;
	
	/**
	 * Button on operator stick to unlock the locking latch
	 */
	public static final int LOCKING_ACUTATOR_UNLOCK_OPERATOR_STICK_BUTTON = 6;
	
	/**
	 * FVRtD value for the arm potentiometer
	 * TODO: Measure this value
	 */
	public static final double SHOOTER_ARM_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = 0.25f;
	
	public static final double SHOOTER_ARM_SET_ANGLE_SPEED_LIMIT = 0.4f;
}
