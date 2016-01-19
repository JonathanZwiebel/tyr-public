package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

public class ShooterSystems {
	private Motor leftMotor = Hardware.Motors.talon(4);
	private Motor rightMotor = Hardware.Motors.talon(5);
}
