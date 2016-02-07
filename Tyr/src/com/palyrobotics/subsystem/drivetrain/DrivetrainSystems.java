package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.Motor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public interface DrivetrainSystems {
	public abstract Motor getLeftMotor();
	public abstract Motor getRightMotor();
	public abstract Compressor getCompressor();
	public abstract DoubleSolenoid getSolenoid();
}
