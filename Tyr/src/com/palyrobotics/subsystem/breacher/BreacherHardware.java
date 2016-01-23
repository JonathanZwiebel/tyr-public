package com.palyrobotics.subsystem.breacher;

import static com.palyrobotics.robot.Ports.*;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;
/**
 * All the output hardware for the breacher
 * @author Nihar
 */
public class BreacherHardware extends BreacherSystems {
	public BreacherHardware() {
		setLeftMotor(Hardware.Motors.talon(-1));
		setRightMotor(Hardware.Motors.talon(-1));
	}
}
