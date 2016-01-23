package com.palyrobotics.subsystem.breacher;

import static com.palyrobotics.robot.Ports.*;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;
/**
 * All the output hardware for the breacher
 * @author Nihar
 *
 */
public class BreacherHardware implements BreacherSystems {
	public Motor leftMotor = Hardware.Motors.talon(-1);
	public Motor rightMotor = Hardware.Motors.talon(-1);
}
