package com.palyrobotics.robot;

import org.strongback.components.AngleSensor;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.Switch;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.SerialPort;

import static com.palyrobotics.robot.Ports.*;
import static com.palyrobotics.robot.RobotConstants.*;
import static com.palyrobotics.subsystem.drivetrain.DrivetrainConstants.*;
import static com.palyrobotics.subsystem.shooter.ShooterConstants.*;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

public class InputHardware implements InputSystems {
	public final FlightStick driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(DRIVE_STICK_PORT);
	public final FlightStick turnStick = Hardware.HumanInterfaceDevices.logitechAttack3D(TURN_STICK_PORT);
	public final FlightStick secondaryStick = Hardware.HumanInterfaceDevices.logitechAttack3D(SECONDARY_STICK_PORT);
	public final FlightStick shooterStick = Hardware.HumanInterfaceDevices.logitechAttack3D(SHOOTER_STICK_PORT);

	public final AngleSensor leftDriveEncoder = Hardware.AngleSensors.encoder(DRIVE_LEFT_ENCODER_A_CHANNEL, DRIVE_LEFT_ENCODER_B_CHANNEL, LEFT_DPP);
	public final AngleSensor rightDriveEncoder = Hardware.AngleSensors.encoder(DRIVE_RIGHT_ENCODER_A_CHANNEL, DRIVE_RIGHT_ENCODER_B_CHANNEL, RIGHT_DPP);
	public final Gyroscope gyroscope = Hardware.AngleSensors.gyroscope(GYROSCOPE_PORT);
	public final ThreeAxisAccelerometer accelerometer = Hardware.Accelerometers.builtIn();
	public final DistanceSensor leftUltrasonic = Hardware.DistanceSensors.analogUltrasonic(LEFT_RANGEFINDER_CHANNEL, ULTRASONIC_VOLTS_TO_INCHES);
	public final DistanceSensor rightUltrasonic = Hardware.DistanceSensors.analogUltrasonic(RIGHT_RANGEFINDER_CHANNEL, ULTRASONIC_VOLTS_TO_INCHES);
	
	public final Switch accumulatorFilledLimitSensor = null;
	
	public final AngleSensor breacherPotentiometer = Hardware.AngleSensors.potentiometer(BREACHER_POTENTIOMETER_CHANNEL, BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES);
	
	public final AngleSensor shooterPotentiometer = Hardware.AngleSensors.potentiometer(SHOOTER_ARM_POTENTIOMETER_CHANNEL, SHOOTER_ARM_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES);
	public final Switch shooterArmMaximumAngleLimitSensor = null;
	public final Switch shooterArmMinimumAngleLimitSensor = null;
	
	public static SerialPort serialPort = new SerialPort(RobotConstants.BAUDRATE, VISION_PORT);

	@Override
	public FlightStick getDriveStick() {
		return driveStick;
	}
	@Override
	public FlightStick getTurnStick() {
		return turnStick;
	}
	@Override
	public FlightStick getShooterStick() {
		return shooterStick;
	}
	@Override 
	public FlightStick getSecondaryStick() {
		return secondaryStick;
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
	public DistanceSensor getLeftUltrasonic() {
		return leftUltrasonic;
	}
	@Override
	public DistanceSensor getRightUltrasonic() {
		return rightUltrasonic;
	}
	@Override
	public Switch getAccumulatorFilledLimitSensor() {
		return accumulatorFilledLimitSensor;
	}
	
	public AngleSensor getBreacherPotentiometer() {
		return breacherPotentiometer;
	}
	@Override
	public AngleSensor getShooterArmPotentiometer() {
		return shooterPotentiometer;
	}
	@Override
	public Switch getShooterArmMaximumAngleLimitSensor() {
		return shooterArmMaximumAngleLimitSensor;
	}
	@Override
	public Switch getShooterArmMinimumAngleLimitSensor() {
		return shooterArmMinimumAngleLimitSensor;
	}
	/**
	 * @return Returns and Interger array with element 0 being x offset, and element 1 being y offset
	 */
	@Override
	public int[] getShooterDisplacement() {
		String rawData = serialPort.readString();
		if (rawData.length() != 0) {
			try {
				String[] splitData = rawData.split("\n")[0].split("\t"); // make sure we get the most recent data
				
				int[] displacements = new int[2]; 
				displacements[0] = Integer.valueOf(splitData[0]);
				displacements[1] = Integer.valueOf(splitData[1].replace("\n",""));
				
				return displacements;  
			} catch (Exception e) { 
				System.out.println("Serial Error: Corrupted Data");
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
}
