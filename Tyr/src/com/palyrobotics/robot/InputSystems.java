package com.palyrobotics.robot;

import org.strongback.components.AngleSensor;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;

import com.palyrobotics.xbox.XBoxController;

import edu.wpi.first.wpilibj.AnalogGyro;

public interface InputSystems {
	
	public enum ControlScheme {
		XBOX,
		JOYSTICKS
	}
	
	public FlightStick getDriveStick();
	public FlightStick getTurnStick();
	public FlightStick getShooterStick();
	public FlightStick getSecondaryStick();
	
	public XBoxController getXBox();
	
	public void setControlScheme(ControlScheme control);
	public ControlScheme getControlScheme();
	
	public AngleSensor getLeftDriveEncoder();
	public AngleSensor getRightDriveEncoder();
	
	public AnalogGyro getGyroscope();
	public AngleSensor getBreacherPotentiometer();
	public AngleSensor getShooterArmPotentiometer();
	public ThreeAxisAccelerometer getAccelerometer();
	public int[] getShooterDisplacement();
}
