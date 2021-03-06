
package com.palyrobotics.robot;

import org.strongback.components.AngleSensor;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;

import com.palyrobotics.xbox.MockFlightStick;
import com.palyrobotics.xbox.XBox;
import com.palyrobotics.xbox.XBoxController;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SerialPort;

import static com.palyrobotics.robot.Ports.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.palyrobotics.robot.SensorConstants.*;

public class InputHardware implements InputSystems {
	
	private ControlScheme control;
	
	public final FlightStick driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(DRIVE_STICK_PORT);
	public final FlightStick turnStick = Hardware.HumanInterfaceDevices.logitechAttack3D(TURN_STICK_PORT);
	public final FlightStick secondaryStick = Hardware.HumanInterfaceDevices.logitechAttack3D(SECONDARY_STICK_PORT);
	public final FlightStick shooterStick = Hardware.HumanInterfaceDevices.logitechAttack3D(SHOOTER_STICK_PORT);

	public final AngleSensor leftDriveEncoder = Hardware.AngleSensors.encoder(DRIVE_LEFT_ENCODER_A_CHANNEL, DRIVE_LEFT_ENCODER_B_CHANNEL, SensorConstants.LEFT_DRIVETRAIN_DPP);
	public final AngleSensor rightDriveEncoder = Hardware.AngleSensors.encoder(DRIVE_RIGHT_ENCODER_A_CHANNEL, DRIVE_RIGHT_ENCODER_B_CHANNEL, SensorConstants.RIGHT_DRIVETRAIN_DPP);
	public final AnalogGyro gyroscope = new AnalogGyro(1);
	public final ThreeAxisAccelerometer accelerometer = Hardware.Accelerometers.builtIn();
	
	public final AngleSensor breacherPotentiometer = null; //Hardware.AngleSensors.potentiometer(BREACHER_POTENTIOMETER_CHANNEL, BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES, BREACHER_POTENTIOMETER_DEGREE_OFFSET);
	public final AngleSensor shooterPotentiometer = Hardware.AngleSensors.potentiometer(2, SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES, SHOOTER_POTENTIOMETER_DEGREE_OFFSET);
	
	public final XBoxController xbox = XBox.getXBox(XBOX_PORT);
	
	public final MockFlightStick mockLeftStick = new MockFlightStick(0, 0, 0, 0, 0, 0);
	public final MockFlightStick mockRightStick = new MockFlightStick(0, 0, 0, 0, 0, 0);
	
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
		if(getControlScheme().equals(ControlScheme.XBOX)) {
			return mockLeftStick;
		}
		return shooterStick;
	}
	@Override 
	public FlightStick getSecondaryStick() {
		if(getControlScheme().equals(ControlScheme.XBOX)) {
			return mockRightStick;
		}
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
	public AnalogGyro getGyroscope() {
		return gyroscope;
	}
	@Override
	public ThreeAxisAccelerometer getAccelerometer() {
		return accelerometer;
	}
	public AngleSensor getBreacherPotentiometer() {
		return breacherPotentiometer;
	}
	@Override
	public AngleSensor getShooterArmPotentiometer() {
		return shooterPotentiometer;
	}

	@Override
	public XBoxController getXBox() {
		return xbox;
	}

	/**
	 * Reads and parses vision data from the serial port.
	 *
	 * @return 	int array with 4 elements: 		[x, y, w, h]
	 * 			where: 	x is the target's horizontal displacement from image center
	 * 					y is the target's vertical displacement from image center
	 * 					w is the width of the target's bounding box
	 * 					h is the height of the target's bounding box
	 *
	 * Note: all units are in camera pixels
	 */
	@Override
	public int[] getShooterDisplacement() {
		String rawData = serialPort.readString(); // read raw data from the serial port
		return getShooterDisplacement(rawData);
	}

	public int[] getShooterDisplacement(String rawData) {
		// Expected string format: 	x\ty\tw\th\r\n
		if (rawData.length() != 0) {
			try {
				String[] splitData = rawData.split("\r\n")[0].split("\t"); // make sure we get the most recent data
				
				int[] displacements = new int[4];
				displacements[0] = Integer.valueOf(splitData[0]);
				displacements[1] = Integer.valueOf(splitData[1]);
				displacements[2] = Integer.valueOf(splitData[2]);
				displacements[3] = Integer.valueOf(splitData[3].replace("\r\n","")); // strip \r\n from end of line
				
				return displacements;  
			} catch (Exception e) {  // malformatted string
		    	Logger.getLogger("Central").log(Level.WARNING, "SERIAL ERROR: Malformatted Data");
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public ControlScheme getControlScheme() {
		return control;
	}

	@Override
	public void setControlScheme(ControlScheme control) {
		this.control = control;
	}
}