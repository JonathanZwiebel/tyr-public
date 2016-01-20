package com.palyrobotics.robot;

import org.strongback.components.AngleSensor;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.Switch;
import org.strongback.hardware.Hardware;
import static com.palyrobotics.robot.Ports.*;


// None of the modules should modify this class
public class RobotInput {
	public static final FlightStick driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(DRIVE_STICK_PORT);
	public static final FlightStick turnStick = Hardware.HumanInterfaceDevices.logitechAttack3D(TURN_STICK_PORT);
	public static final FlightStick operatorStick = Hardware.HumanInterfaceDevices.logitechAttack3D(OPERATOR_STICK_PORT);

	// When build delivers we will define these, until then, do not touch
	
	public static final AngleSensor leftDriveEncoder = null;
	public static final AngleSensor rightDriveEncoder = null;
	public static final Gyroscope gyroscope = null;
	public static final ThreeAxisAccelerometer accelerometer = null;
	public static final DistanceSensor leftUltrasonic = null;
	public static final DistanceSensor rightUltrasonic = null;
	
	public static final AngleSensor shooterPotentiometer = null;
	public static final Switch shooterLimitSensor = null; // not yet determined if switch or digital HFX
	
	public static final AngleSensor accumulatorPotentiometer = null;
	public static final Switch accumulatorLimitSensor = null; // not yet determined if switch or digital HFX
	
	// vision to be added later
}
