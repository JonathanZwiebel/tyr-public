package com.palyrobotics.robot;

import static com.palyrobotics.robot.Ports.DRIVE_STICK_PORT;
import static com.palyrobotics.robot.Ports.OPERATOR_STICK_PORT;
import static com.palyrobotics.robot.Ports.TURN_STICK_PORT;

import org.strongback.components.AngleSensor;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.Switch;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;

public interface InputSystems {
	public FlightStick getDriveStick();
	public FlightStick getTurnStick();
	public FlightStick getOperatorStick();

	public AngleSensor getLeftDriveEncoder();
	public AngleSensor getRightDriveEncoder();
	public Gyroscope getGyroscope();
	public ThreeAxisAccelerometer getAccelerometer();
	public DistanceSensor getLeftInfrared();
	public DistanceSensor getRightInfrared();
	
	public AngleSensor getShooterPotentiometer();
	public Switch getShooterDrawbackLimitSensor();
	
	public AngleSensor getAccumulatorPotentiometer();
	public Switch getAccumulatorFilledLimitSensor();
	
	// vision to be added later
}
