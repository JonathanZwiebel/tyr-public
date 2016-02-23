package com.palyrobotics.subsystem.breacher;

import static com.palyrobotics.robot.Ports.*;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

/**
 * All the output hardware for the breacher
 * 
 * @author Nihar
 */
public class BreacherHardware implements BreacherSystems {
	private Motor motor = Hardware.Motors.talonSRX(BREACHER_TALON_DEVICE_ID);

	public BreacherHardware() {
	}


	@Override
	public Motor getMotor() {
		return motor;
	}
}