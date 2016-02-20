package com.palyrobotics.robot;

import edu.wpi.first.wpilibj.SerialPort;
public class RobotConstants {
	public final static String NAME = "Tyr";
	public final static float LENGTH_INCHES = 32.0f;
	public final static float WIDTH_INCHES = 20.0f;
	
	public final static int MILLISECONDS_PER_UPDATE = 20;
	public final static float UPDATES_PER_SECOND = 1000 / (float) MILLISECONDS_PER_UPDATE;
	public final static int NANOSECONDS_PER_MILLISECOND = 1000000;

	public final static int CYCLE_COUNT_FOR_REPETITION_UNIT_TESTS = 1;
	public final static int CYCLE_COUNT_FOR_STATE_CHANGE_UNIT_TESTS = 5;
	public final static int CYCLE_COUNT_FOR_BASIC_UNIT_TESTS = 50;
	
	public static final int BAUDRATE = 9600;
	public static final SerialPort.Port PORT = SerialPort.Port.kMXP; 
	
}
