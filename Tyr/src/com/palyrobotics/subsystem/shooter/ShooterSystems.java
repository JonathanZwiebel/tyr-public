package com.palyrobotics.subsystem.shooter;

import org.strongback.components.Solenoid;
import org.strongback.components.TalonSRX;

<<<<<<< HEAD
/**
 * An interface to be extended by Tyr hardware or a mock shooter that contains 
 * the output mechanism on the shooter
 * 
 * @author Paly Robotics Programming Red Module
 */
public interface ShooterSystems {	
	/**
	 * Returns the motor that will control the shooter arm
	 * @return the motor that controls the shooter arm
	 */
	public TalonSRX getArmMotor();
	
	/**
	 * Returns the solenoid that controls the locking latch on the arm
	 * When actuated the shooter will not beyond the locked point
	 * @return the solenoid that controls the locking actuator
	 */
	public Solenoid getLockingActuator();	
	
	/**
	 * Returns the solenoid that controls the loading mechanism on the arm
	 * When retracted the shooter is loaded and the springs are compressed
	 * @return the solenoid that controls the locking actuator
	 */
	public Solenoid getLoadingActuator();
=======
public interface ShooterSystems {
	public Motor getMotor();

	public void setMotor(Motor motor);
	// this will be either a talon (ShooterHardware) or a mock talon
	// (MockShooterHardware)
>>>>>>> 6e7dc8a... reformatted code
}
