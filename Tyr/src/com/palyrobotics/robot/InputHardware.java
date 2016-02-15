package com.palyrobotics.robot;

import org.strongback.components.AngleSensor;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.Switch;
import org.strongback.hardware.Hardware;

import com.palyrobotics.subsystem.shooter.ShooterConstants;

import static com.palyrobotics.robot.Ports.*;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;


// None of the modules should modify this class
public class InputHardware implements InputSystems {
	public static final FlightStick driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(DRIVE_STICK_PORT);
	public static final FlightStick turnStick = Hardware.HumanInterfaceDevices.logitechAttack3D(TURN_STICK_PORT);
	public static final FlightStick operatorStick = Hardware.HumanInterfaceDevices.logitechAttack3D(OPERATOR_STICK_PORT);

	// When build delivers we will define these, until then, do not touch
	
	public static final AngleSensor leftDriveEncoder = Hardware.AngleSensors.encoder(DRIVE_LEFT_ENCODER_A, DRIVE_LEFT_ENCODER_B, LEFT_DPP);
	public static final AngleSensor rightDriveEncoder = Hardware.AngleSensors.encoder(DRIVE_RIGHT_ENCODER_A, DRIVE_RIGHT_ENCODER_B, RIGHT_DPP);
	public static final Gyroscope gyroscope = Hardware.AngleSensors.gyroscope(GYROSCOPE_ANALOG);
	public static final ThreeAxisAccelerometer accelerometer = null;
	public static final DistanceSensor leftInfrared = null;
	public static final DistanceSensor rightInfrared = null;
	
	
	public static final AngleSensor accumulatorPotentiometer = null;
	public static final Switch accumulatorFilledLimitSensor = null; // not yet determined if switch or digital HFX
	
	public ContinuousRange visionInput = null;
	public static final AngleSensor shooterArmAngleSensor = Hardware.AngleSensors.potentiometer(SHOOTER_ARM_POTENTIOMETER_CHANNEL, ShooterConstants.SHOOTER_ARM_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES);
	public static final Switch shooterArmMaximumAngleLimitSensor = null;
	public static final Switch shooterArmMinimumAngleLimitSensor = null;
	
	@Override
	public FlightStick getDriveStick() {
		return driveStick;
	}
	@Override
	public FlightStick getTurnStick() {
		return turnStick;
	}
	@Override
	public FlightStick getOperatorStick() {
		return operatorStick;
	}
	@Override
	public AngleSensor getLeftDriveEncoder() {
		return leftDriveEncoder;
	}
	@Override
	public AngleSensor getRightDriveEncoder() {
		return rightDriveEncoder;
	}
	@Override
	public Gyroscope getGyroscope() {
		return gyroscope;
	}
	@Override
	public ThreeAxisAccelerometer getAccelerometer() {
		return accelerometer;
	}
	@Override
	public DistanceSensor getLeftInfrared() {
		return leftInfrared;
	}
	@Override
	public DistanceSensor getRightInfrared() {
		return rightInfrared;
	}
	@Override
	public AngleSensor getAccumulatorPotentiometer() {
		return accumulatorPotentiometer;
	}
	@Override
	public Switch getAccumulatorFilledLimitSensor() {
		return accumulatorFilledLimitSensor;
	}
	@Override
	public ContinuousRange getVisionInput() {
		return visionInput;
	}
	@Override
	public AngleSensor getShooterArmAngleSensor() {
		return shooterArmAngleSensor;
	}
	@Override
	public Switch getShooterArmMaximumAngleLimitSensor() {
		return shooterArmMaximumAngleLimitSensor;
	}
	@Override
	public Switch getShooterArmMinimumAngleLimitSensor() {
		return shooterArmMinimumAngleLimitSensor;
	}
}
