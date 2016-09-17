package com.palyrobotics.test.simulated_systems.simulated_outputs;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Solenoid.Direction;

import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;
import com.palyrobotics.test.simulated_systems.simulated_components.SimulatedMotor;
import com.palyrobotics.test.simulated_systems.simulated_components.SimulatedSolenoid;

public class SimulatedDrivetrainSystems implements DrivetrainSystems {

	private Motor leftMotor = new SimulatedMotor();
	private Motor rightMotor = new SimulatedMotor();
	
	private Solenoid solenoid = new SimulatedSolenoid(Direction.EXTENDING);
	
	@Override
	public Motor getLeftMotor() {
		return this.leftMotor;
	}

	@Override
	public Motor getRightMotor() {
		return this.rightMotor;
	}

	@Override
	public Solenoid getSolenoid() {
		return this.solenoid;
	}
	
}