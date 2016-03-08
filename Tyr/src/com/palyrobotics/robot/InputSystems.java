package com.palyrobotics.robot;

import org.strongback.components.AngleSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;

import com.palyrobotics.xbox.XBoxController;

public interface InputSystems {
	public FlightStick getDriveStick();
	public FlightStick getTurnStick();
	public FlightStick getShooterStick();
	public FlightStick getSecondaryStick();
	
	public XBoxController getXBox();
	
	public AngleSensor getLeftDriveEncoder();
	public AngleSensor getRightDriveEncoder();
	
	public Gyroscope getGyroscope();
	public AngleSensor getBreacherPotentiometer();
	public AngleSensor getShooterArmPotentiometer();
	public ThreeAxisAccelerometer getAccelerometer();
	public int[] getShooterDisplacement();
}
