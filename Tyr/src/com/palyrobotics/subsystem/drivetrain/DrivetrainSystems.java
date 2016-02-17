package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.*;

public interface DrivetrainSystems {
	public abstract Motor getLeftMotor();
	public abstract Motor getRightMotor();
	public abstract Solenoid getSolenoid();
}
