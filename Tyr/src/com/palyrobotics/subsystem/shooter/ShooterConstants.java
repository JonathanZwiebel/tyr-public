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
	public static final double MAX_ARM_ANGLE = Double.MAX_VALUE / 2;
	
	/**
	 * Minimum physical angle of the shooter arm
	 */
	public static final double MIN_ARM_ANGLE = -Double.MAX_VALUE / 2;

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
	public static final int SHOOTER_LOADING_BUTTON = BLANK_INTEGER;
	
	/**
	 * Button on operator stick to toggle locking actuator
	 */
	public static final int SHOOTER_LOCKING_BUTTON = BLANK_INTEGER;
	
	/**
	 * Button on operator stick to call fire sequence
	 */
	public static final int SHOOTER_FIRE_SEQUENCE_BUTTON = BLANK_INTEGER;
	
	/**
	 * Button on operator stick to call load sequence
	 */
	public static final int SHOOTER_LOAD_SEQUENCE_BUTTON = BLANK_INTEGER;
}
