package com.palyrobotics.test.simulated_systems.simulated_inputs;

import org.strongback.components.AngleSensor;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.test.simulated_systems.simulated_components.SimulatedEncoder;
import com.palyrobotics.test.simulated_systems.simulated_components.SimulatedFlightStick;
import com.palyrobotics.test.simulated_systems.simulated_components.SimulatedGyroscope;
import com.palyrobotics.test.simulated_systems.simulated_components.SimulatedXBoxController;
import com.palyrobotics.xbox.XBoxController;

import edu.wpi.first.wpilibj.AnalogGyro;

public class SimulatedInputSystems implements InputSystems {
	
	// TODO: Implement system for simulated joystick and other human input
	private FlightStick driveStick = new SimulatedFlightStick();
	private FlightStick turnStick = new SimulatedFlightStick();
	
	private XBoxController xbox = new SimulatedXBoxController();
	
	private FlightStick shooterStick = new SimulatedFlightStick();
	private FlightStick secondaryStick = new SimulatedFlightStick();
	
	private AngleSensor leftDriveEncoder = new SimulatedEncoder();
	private AngleSensor rightDriveEncoder = new SimulatedEncoder();
	
	private AnalogGyro gyroscope = new SimulatedGyroscope();
	
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
	public XBoxController getXBox() {
		return xbox;
	}

	@Override
	public void setControlScheme(ControlScheme control) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ControlScheme getControlScheme() {
		// TODO Auto-generated method stub
		return null;
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
	public AngleSensor getBreacherPotentiometer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AngleSensor getShooterArmPotentiometer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThreeAxisAccelerometer getAccelerometer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getShooterDisplacement() {
		// TODO Auto-generated method stub
		return null;
	}
	
}