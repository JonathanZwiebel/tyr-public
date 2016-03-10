package com.palyrobotics.subsystem.drivetrain;

public class DrivetrainConstants {
	public static final double MAX_TELEOP_ACCELERATION = 1.0;
	
	// The teleoperated orientation, with 1.0 meaning shooter forwards, and -1.0 meaning breacher forwards
	public static double TELEOP_ORIENTATION = 1.0;
	
	public static final float PRECISION_SCALING = 0.5f;

	// TODO: Assign PD values.
	// PD values for driving distance
	public static final double RIGHT_P_VALUE = 0.09;
	public static final double LEFT_P_VALUE = -0.1;
	public static final double RIGHT_D_VALUE = 0.003;
	public static final double LEFT_D_VALUE = -0.003;

	// PD values for turning angle.
	public static final double RIGHT_ANGLE_P_VALUE = 0.09;
	public static final double LEFT_ANGLE_P_VALUE = 0.1;
	public static final double RIGHT_ANGLE_D_VALUE = 0.003;
	public static final double LEFT_ANGLE_D_VALUE = 0.003;

	// PD values for shooter allign.
	public static final double RIGHT_SHOOTER_P_VALUE = 0.0;
	public static final double LEFT_SHOOTER_P_VALUE = 0.0;
	public static final double RIGHT_SHOOTER_D_VALUE = 0.0;
	public static final double LEFT_SHOOTER_D_VALUE = 0.0;

	public static final double ACCEPTABLE_DISTANCE_ERROR = 1.0;
	public static final double ACCEPTABLE_ANGLE_ERROR = 3.0;
	public static final double ACCEPTABLE_PIXEL_ERROR = 5.0;

	public static final double STANDARD_DRIVE_DISTANCE = 50.0;
	public static final double STANDARD_TURN_ANGLE = 90.0;

	public static final float ULTRASONIC_VOLTS_TO_INCHES = 0.1f;
}
