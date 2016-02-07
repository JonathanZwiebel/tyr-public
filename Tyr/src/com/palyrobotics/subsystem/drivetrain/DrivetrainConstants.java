package com.palyrobotics.subsystem.drivetrain;

public class DrivetrainConstants {
	public static final double LEFT_DPP = 0.18150;
	public static final double RIGHT_DPP = -0.18446;

	public static final double MAX_TELEOP_ACCELERATION = 1.0;

	public static final double ENCODER_PULSES_PER_DEGREE = 0.0;

	public static final double RIGHT_P_VALUE = 0.09;
	public static final double LEFT_P_VALUE = -0.1;
	public static final double RIGHT_D_VALUE = 0.003;
	public static final double LEFT_D_VALUE = -0.003;

	public static final double RIGHT_ANGLE_P_VALUE = 0.09;
	public static final double LEFT_ANGLE_P_VALUE = 0.1;
	public static final double RIGHT_ANGLE_D_VALUE = 0.003;
	public static final double LEFT_ANGLE_D_VALUE = 0.003;

	// TODO: Assign PD values.
	public static final double RIGHT_SHOOTER_P_VALUE = 0.0;
	public static final double LEFT_SHOOTER_P_VALUE = 0.0;
	public static final double RIGHT_SHOOTER_D_VALUE = 0.0;
	public static final double LEFT_SHOOTER_D_VALUE = 0.0;

	public static final double ANGLE_TO_DISTANCE = 0.25;
	public static final double PIXELS_TO_DISTANCE = 0.1; // TODO: Assign real value. 
	public static final double UPDATE_RATE = 0.02; 
	public static final double ACCEPTABLE_ANGLE_ERROR = 3.0;
	public static final double ACCEPTABLE_DISTANCE_ERROR = 1.0;
	public static final double ACCEPTABLE_PIXEL_ERROR = 5.0;

}
