package com.palyrobotics.subsystem.breacher;

/**
 * Non-port constants for the breacher subystem
 * 
 * @author Nihar
 *
 */
public class BreacherConstants {
	public static final double MAX_SPEED = 1;
	public static final double PROPORTIONAL = 0.1;
	public static final double INTEGRAL = 1;
	public static final double DERIVATIVE = 0.1;
	public static final double RAISE_SPEED = .1;
	public static final double LOWER_SPEED = -.1;
	public static final int OPEN_TIME = 30;
	public static final int CLOSE_TIME = 30;
	public static final double CLOSE_BREACHER_ANGLE = -10;
	public static final double OPEN_BREACHER_ANGLE = 10;
	public static final double ACCEPTABLE_POTENTIOMETER_ERROR = 1;

	public static final int BREACHER_PORT = 5;
	public static final int POTENTIOMETER_PORT = 0;
}