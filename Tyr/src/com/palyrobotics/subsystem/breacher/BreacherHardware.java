package com.palyrobotics.subsystem.breacher;

import static com.palyrobotics.robot.Ports.*;

import org.strongback.components.AngleSensor;
import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

/**
 * All the output hardware for the breacher
 * 
 * @author Nihar
 */
public class BreacherHardware implements BreacherSystems {

	private Motor motor;

	private AngleSensor potentiometer;

	public BreacherHardware() {
		setMotor(Hardware.Motors.talonSRX(BREACHER_PORT));
		// need to set dpp thing
		setPotentiometer(Hardware.AngleSensors.potentiometer(BREACHER_POTENTIOMETER_PORT, 1));
	}

	@Override
	public void setMotor(Motor motor) {
		this.motor = motor;
	}

	@Override
	public Motor getMotor() {
		return motor;
	}

	@Override
	public void setPotentiometer(AngleSensor potentiometer) {
		this.potentiometer = potentiometer;

	}

	public AngleSensor getPotentiometer() {
		return potentiometer;
	}
}