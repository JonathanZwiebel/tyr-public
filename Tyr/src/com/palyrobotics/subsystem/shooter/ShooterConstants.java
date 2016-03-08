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
	 * FVRtD value for the arm potentiometer
	 * TODO: Measure this value
	 */
	public static final double SHOOTER_ARM_SET_ANGLE_SPEED_LIMIT = 0.4f;
}
