package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Solenoid;
import org.strongback.components.TalonSRX;
import org.strongback.components.Solenoid.Direction;
import org.strongback.hardware.Hardware;

import com.palyrobotics.robot.Ports;

/**
 * A representation of the hardware on Tyr
 * 
  * @author Paly Robotics Programming Red Module
 */
public class ShooterHardware implements ShooterSystems {
	TalonSRX armMotor = Hardware.Motors.talonSRX(Ports.SHOOTER_ARM_TALON_DEVICE_ID);
	Solenoid loadingActuator = Hardware.Solenoids.doubleSolenoid(Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE, Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE, Direction.STOPPED);
	Solenoid lockingActuator = Hardware.Solenoids.doubleSolenoid(Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE, Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE, Direction.STOPPED);
	
	@Override
	public TalonSRX getArmMotor() {
		return armMotor;
	}

	@Override
	public Solenoid getLockingActuator() {
		return lockingActuator;
	}
	
	@Override
	public Solenoid getLoadingActuator() {
		return loadingActuator;
	}
}
