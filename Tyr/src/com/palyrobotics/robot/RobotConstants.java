package com.palyrobotics.robot;

public class RobotConstants {
	public static String NAME;
	public final static float LENGTH_INCHES = 32.0f;
	public final static float WIDTH_INCHES = 20.0f;
	
	public static final int BAUDRATE = 9600;

	public final static int MILLISECONDS_PER_UPDATE = 20;
	public final static float UPDATES_PER_SECOND = 1000 / (float) MILLISECONDS_PER_UPDATE;
	public final static int NANOSECONDS_PER_MILLISECOND = 1000000;

	public final static int CYCLE_COUNT_FOR_REPETITION_UNIT_TESTS = 1;
	public final static int CYCLE_COUNT_FOR_STATE_CHANGE_UNIT_TESTS = 5;
	public final static int CYCLE_COUNT_FOR_BASIC_UNIT_TESTS = 50;
	
}
