package com.palyrobotics.subsystem.breacher;

/**
 * Non-port constants for the breacher subystem
 * 
 * @author Nihar
 *
 */
public class BreacherConstants {
	public static final double MAX_SPEED = 0.5;
	
	public static final double PROPORTIONAL = 0.1;
	public static final double INTEGRAL = 1;
	public static final double DERIVATIVE = 0.1;
	
	public static final double UPDATES_PER_SECOND = 50;
	
	public static final double RAISE_SPEED = .1;
	public static final double LOWER_SPEED = -.1;
	
	public static final int OPEN_TIME = 30;
	public static final int CLOSE_TIME = 30;
	
	public static final double CLOSE_BREACHER_ANGLE = -10;
	public static final double OPEN_BREACHER_ANGLE = 10;
	
	public static final double MAX_POTENTIOMETER_ANGLE = 90;
	public static final double MIN_POTENTIOMETER_ANGLE = 0;
	
	public static final double BOUNCE_SPEED = 0.3;
	
	public static final double ACCEPTABLE_POTENTIOMETER_ERROR = 1;
	public static final double ACCEPTABLE_DERIVATIVE_ERROR = 0.2;
	
	public static final double FULL_VOLTAGE_RANGE_TO_DEGREES = 24;
			
	public static final int CANCEL_BUTTON = 10;
	
	public static final int RAISE_BUTTON = 1;
	public static final int LOWER_BUTTON = 2;
	
	public static final int BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = 720;
}