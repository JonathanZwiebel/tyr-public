package com.palyrobotics.robot;

import org.strongback.components.ui.*;
import org.strongback.hardware.Hardware;

import static com.palyrobotics.robot.Ports.*;

public class RobotInput {
	public static final FlightStick driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(DRIVE_STICK_PORT);
	public static final FlightStick turnStick = Hardware.HumanInterfaceDevices.logitechAttack3D(TURN_STICK_PORT);
	public static final FlightStick operatorStick = Hardware.HumanInterfaceDevices.logitechAttack3D(OPERATOR_STICK_PORT);

	
}
