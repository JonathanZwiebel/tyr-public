package com.palyrobotics.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI.Port;

public class Ports {
	// Universal Serial Bus - Driver Station
	public static final int DRIVE_STICK_PORT = 0;
	public static final int TURN_STICK_PORT = 1;
	public static final int SHOOTER_STICK_PORT = 2;
	public static final int SECONDARY_STICK_PORT = 3;
	
	// Digital Input Output - roboRIO
	public static final int DRIVE_LEFT_ENCODER_A_CHANNEL = 0;
	public static final int DRIVE_LEFT_ENCODER_B_CHANNEL = 1;
	public static final int DRIVE_RIGHT_ENCODER_A_CHANNEL = 2;
	public static final int DRIVE_RIGHT_ENCODER_B_CHANNEL = 3;
	
	// Bus - Serial Peripheral Interface
	public static final Port GYROSCOPE_PORT = Port.kOnboardCS0;
	
	// Device ID - Controller Area Network
	public static final int LEFT_FRONT_TALON_DEVICE_ID = 1;
	public static final int LEFT_BACK_TALON_DEVICE_ID = 3;
	public static final int RIGHT_FRONT_TALON_DEVICE_ID = 2;
	public static final int RIGHT_BACK_TALON_DEVICE_ID = 4;
	public static final int SHOOTER_ARM_TALON_DEVICE_ID = 8;
	public static final int BREACHER_TALON_DEVICE_ID =5;
	
	// Pulse Width Modulation - roboRIO
	public static final int ACCUMULATOR_LEFT_VICTOR_CHANNEL = 0;
	public static final int ACCUMULATOR_RIGHT_VICTOR_CHANNEL = 1;
	
	// Analog Output - roboRIO
	public static final int BREACHER_POTENTIOMETER_CHANNEL = 0;
	public static final int SHOOTER_ARM_POTENTIOMETER_CHANNEL = 1;
	public static final int RIGHT_RANGEFINDER_CHANNEL = 2;
	public static final int LEFT_RANGEFINDER_CHANNEL = 3;
	
	// Valve - Pneumatics Control Module
	public static final int GEAR_ACTUATOR_EXTEND_VALVE = 7;
	public static final int GEAR_ACTUATOR_RETRACT_VALVE = 0;
	public static final int SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE = 5;
	public static final int SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE = 2;
	
	public static final int SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE = 6; // bad
	public static final int SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE = 1; // bad
	
	public static final int CAMERA_LIGHT_VALVE = 3; //sketchy

	// Serial
	public static final SerialPort.Port VISION_PORT = SerialPort.Port.kMXP; 
}
