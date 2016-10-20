package com.palyrobotics.subsystem.drivetrain;

public class DrivetrainConstants {
	public static final double MAX_TELEOP_ACCELERATION = 1.0;
	
	// The teleoperated orientation, with 1.0 meaning shooter forwards, and -1.0 meaning breacher forwards
	public static double TELEOP_ORIENTATION = 1.0;

	// TODO: Assign PD values.
	// PD values for driving distance

	public static final double RIGHT_P_VALUE = 0.09;
	public static final double LEFT_P_VALUE = 0.1;
	public static final double RIGHT_D_VALUE = 0.003;
	public static final double LEFT_D_VALUE = 0.003;

	// PD values for turning angle.
	public static final double RIGHT_ANGLE_P_VALUE = -0.45;
	public static final double LEFT_ANGLE_P_VALUE = 0.45;
	public static final double RIGHT_ANGLE_D_VALUE = -0.0175;
	public static final double LEFT_ANGLE_D_VALUE = 0.0175;

	// PD values for shooter allign.
	public static final double RIGHT_SHOOTER_P_VALUE = 0.045;
	public static final double LEFT_SHOOTER_P_VALUE = 0.045;
	public static final double RIGHT_SHOOTER_D_VALUE = 0.00;
	public static final double LEFT_SHOOTER_D_VALUE = 0.00;
	
	//Acceptable errors
	public static final double ACCEPTABLE_DISTANCE_ERROR = 1.0;
	public static final double ACCEPTABLE_ANGLE_ERROR = 5;
	public static final double ACCEPTABLE_DERIVATIVE = 0.0;
	public static final double ACCEPTABLE_PIXEL_ERROR = 5.0;

	public static final double STANDARD_DRIVE_DISTANCE = 50.0;
	public static final double STANDARD_TURN_ANGLE = 90.0;
	
	public static final float PRECISION_TURNING_SCALING_FACTOR = 0.3f;
	public static final float THROTTLE_FORWARD_SCALING_FACTOR = 10.0f;

	//Constants for turning angle with encoders
	public static double DEGREE_TO_DISTANCE;
	public static final double DERIC_DEGREE_TO_DISTANCE = 0.20145115;
	public static final double TYR_DEGREE_TO_DISTANCE = 0.20145115;
	public static final double PIXELS_PER_DEGREE = 1.0; 
	public static final double PIXELS_PER_DISTANCE = 48.394 - 6;
	public static final double PIXEL_DISTANCE_ADJUSTMENT = 25;
	public static final int SUCCESSIVE_GAP_TIME = 60;
	
	public static final double INCHES_TO_SHOOT = 200.0;

	public static final double LEFT_DRIVE_P = 0.1;
	public static final double RIGHT_DRIVE_P = 0.1;

	public static final double LEFT_DRIVE_D = 0.01;
	public static final double RIGHT_DRIVE_D = 0.01;

}
