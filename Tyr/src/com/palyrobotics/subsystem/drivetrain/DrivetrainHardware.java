package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.*;
import org.strongback.components.Solenoid.Direction;
import org.strongback.hardware.Hardware;

public class DrivetrainHardware implements DrivetrainSystems {
	private Motor leftFrontMotor = Hardware.Motors.talonSRX(1);
	private Motor leftBackMotor = Hardware.Motors.talonSRX(3);
	
	private Motor rightFrontMotor = Hardware.Motors.talonSRX(2);
	private Motor rightBackMotor = Hardware.Motors.talonSRX(4);
	
	public Motor leftMotor = Motor.compose(leftFrontMotor, leftBackMotor);
	public Motor rightMotor = Motor.compose(rightFrontMotor, rightBackMotor);
	
	public Solenoid solenoid = Hardware.Solenoids.doubleSolenoid(0, 1, Direction.STOPPED);
	public PneumaticsModule compressor = Hardware.pneumaticsModule(1);
		
	@Override
	public Motor getLeftMotor() {
		return leftMotor;
	}
	
	@Override
	public Motor getRightMotor() {
		return rightMotor;
	}
	
	@Override
	public PneumaticsModule getCompressor() {
		return compressor;
	}
	
	@Override
	public Solenoid getSolenoid() {
		return solenoid;
	}
}
