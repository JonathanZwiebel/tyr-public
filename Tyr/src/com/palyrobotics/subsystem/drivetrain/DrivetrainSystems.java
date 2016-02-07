package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.Motor;

public interface DrivetrainSystems {
	public abstract Motor getLeftMotor();
	public abstract Motor getRightMotor();
}
