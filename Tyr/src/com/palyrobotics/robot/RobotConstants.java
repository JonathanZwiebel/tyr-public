package com.palyrobotics.robot;

public class RobotConstants {
	public final static String NAME = "Tyr";
	
	public final static int MILLISECONDS_PER_UPDATE = 20;
	public final static float UPDATES_PER_SECOND = 1 / (float) MILLISECONDS_PER_UPDATE;
	public final static int NANOSECONDS_PER_MILLISECOND = 1000000;

	public final static int CYCLE_COUNT_FOR_REPETITION_UNIT_TESTS = 1;
	public final static int CYCLE_COUNT_FOR_BASIC_UNIT_TESTS = 50;
}
