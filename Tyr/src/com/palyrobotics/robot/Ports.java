package com.palyrobotics.robot;

import edu.wpi.first.wpilibj.SPI.Port;

public class Ports {
	// Universal Serial Bus - Drivestick
	public static final int DRIVE_STICK_PORT = 0;
	public static final int TURN_STICK_PORT = 1;
	public static final int SHOOTER_STICK_PORT = 2;
	public static final int SECONDARY_STICK_PORT = 3;
	
	// Digital Input Output - roboRIO
	public static final int DRIVE_LEFT_ENCODER_A = 0;
	public static final int DRIVE_LEFT_ENCODER_B = 1;
	public static final int DRIVE_RIGHT_ENCODER_A = 2;
	public static final int DRIVE_RIGHT_ENCODER_B = 3;
	
	// Bus - Serial Peripheral Interface
	public static final Port GYROSCOPE_BUS = Port.kOnboardCS0;
	
	// Device ID - Controller Area Network
	public static final int LEFT_FRONT_TALON_CHANNEL = 1;
	public static final int LEFT_BACK_TALON_CHANNEL = 3;
	public static final int RIGHT_FRONT_TALON_CHANNEL = 2;
	public static final int RIGHT_BACK_TALON_CHANNEL = 4;
	public static final int SHOOTER_ARM_TALON_CHANNEL = 7;
	public static final int BREACHER_TALON_CHANNEL = 8;
	public static final int ACCUMULATOR_LEFT_TALON_CHANNEL = 5;
	public static final int ACCUMULATOR_RIGHT_TALON_CHANNEL = 6;
	
	// Analog Output - roboRIO
	public static final int BREACHER_POTENTIOMETER_CHANNEL = 0;
	public static final int SHOOTER_ARM_POTENTIOMETER_CHANNEL = 1;
	
	// Airport - Pneumatics Control Module
	public static final int GEAR_ACTUATOR_EXTEND_CHANNEL = 0;
	public static final int GEAR_ACTUATOR_RETRACT_CHANNEL = 1;
	public static final int SHOOTER_LOADING_ACTUATOR_EXTEND_CHANNEL = 4;
	public static final int SHOOTER_LOADING_ACTUATOR_RETRACT_CHANNEL = 5;
	public static final int SHOOTER_LOCKING_ACTUATOR_EXTEND_CHANNEL = 2;
	public static final int SHOOTER_LOCKING_ACTUATOR_RETRACT_CHANNEL = 3;
	
	public static final int LEFT_RANGEFINDER_PORT = 3;
	public static final int RIGHT_RANGEFINDER_PORT = 2;
}
