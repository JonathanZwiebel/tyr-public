package com.palyrobotics.robot;

import org.strongback.components.AngleSensor;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.Switch;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;

public interface InputSystems {
	public FlightStick getDriveStick();
	public FlightStick getTurnStick();
	public FlightStick getShooterStick();
	public FlightStick getSecondaryStick();
	
	public AngleSensor getLeftDriveEncoder();
	public AngleSensor getRightDriveEncoder();
	
	public Gyroscope getGyroscope();
	
	public DistanceSensor getLeftUltrasonic();
	public DistanceSensor getRightUltrasonic();
	
	public AngleSensor getBreacherPotentiometer();

	public AngleSensor getShooterArmPotentiometer();

	public ThreeAxisAccelerometer getAccelerometer();
	
	public int[] getShooterDisplacement();
}
