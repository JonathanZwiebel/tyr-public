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
	 */
	public static final double ARM_kP = BLANK_DOUBLE;
	
	/**
	 * Derivative constant for PID loop
	 */
	public static final double ARM_kD = BLANK_DOUBLE;

	/**
	 * Button on operator stick to toggle loading actuator
	 */
	public static final int LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON = 3;
	
	/**
	 * Button on operator stick to toggle locking actuator
	 */
	public static final int LOCKING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON = 4;
	
	/**
	 * Button on operator stick to call fire sequence
	 */
	public static final int FIRE_SEQUENCE_START_OPERATOR_STICK_BUTTON = 5;
	
	/**
	 * Button on operator stick to call load sequence
	 */
	public static final int LOAD_SEQUENCE_START_OPERATOR_STICK_BUTTON = 6;
	
	/**
	 * FVRtD value for the arm potentiometer
	 */
	public static final double SHOOTER_ARM_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = BLANK_DOUBLE;
}
