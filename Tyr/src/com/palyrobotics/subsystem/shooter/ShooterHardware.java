package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.TalonSRX;

/**
 * A representation of the hardware on Tyr
 * 
  * @author Paly Robotics Programming Red Module
 */
public class ShooterHardware implements ShooterSystems {
	TalonSRX armMotor = null;
	Solenoid loadingActuator = null;
	Solenoid shootingActuator = null;
	
	@Override
	public Motor getArmMotor() {
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
