package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Solenoid;
import org.strongback.components.TalonSRX;
import org.strongback.hardware.Hardware;

import com.palyrobotics.robot.Ports;

/**
 * A representation of the hardware on Tyr
 * 
  * @author Paly Robotics Programming Red Module
 */
public class ShooterHardware implements ShooterSystems {
	TalonSRX armMotor = Hardware.Motors.talonSRX(Ports.SHOOTER_ARM_TALON_SRX_CHANNEL);
	Solenoid loadingActuator = null;
	Solenoid shootingActuator = null;
	
	@Override
	public TalonSRX getArmMotor() {
		return armMotor;
	}

	@Override
	public Solenoid getLockingActuator() {
		return loadingActuator;
	}
	
	@Override
	public Solenoid getLoadingActuator() {
		return shootingActuator;
	}
}
