package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.*;

public interface DrivetrainSystems {
	public Motor getLeftMotor();
	public Motor getRightMotor();
	public Solenoid getSolenoid();
}
