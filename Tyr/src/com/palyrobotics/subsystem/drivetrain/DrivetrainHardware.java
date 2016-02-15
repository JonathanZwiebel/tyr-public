package com.palyrobotics.subsystem.drivetrain;

import org.strongback.components.*;
import static com.palyrobotics.robot.Ports.*;
import org.strongback.components.Solenoid.Direction;
import org.strongback.hardware.Hardware;

public class DrivetrainHardware implements DrivetrainSystems {
	private Motor leftFrontMotor = Hardware.Motors.talonSRX(LEFT_FRONT_TALON_PORT);
	private Motor leftBackMotor = Hardware.Motors.talonSRX(LEFT_BACK_TALON_PORT);

	private Motor rightFrontMotor = Hardware.Motors.talonSRX(RIGHT_FRONT_TALON_PORT);
	private Motor rightBackMotor = Hardware.Motors.talonSRX(RIGHT_BACK_TALON_PORT);

	public Motor leftMotor = Motor.compose(leftFrontMotor, leftBackMotor);
	public Motor rightMotor = Motor.compose(rightFrontMotor, rightBackMotor);

	// TODO: Make sure Direction.EXTENDING is a proper initialization.
	public Solenoid solenoid = Hardware.Solenoids.doubleSolenoid(DOUBLESOLENOID_PORT_A, DOUBLESOLENOID_PORT_B,
			Direction.EXTENDING);

	@Override
	public Motor getLeftMotor() {
		return leftMotor;
	}

	@Override
	public Motor getRightMotor() {
		return rightMotor;
	}

	@Override
	public Solenoid getSolenoid() {
		return solenoid;
	}
}
