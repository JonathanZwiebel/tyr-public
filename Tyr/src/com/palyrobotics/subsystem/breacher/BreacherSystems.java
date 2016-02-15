package com.palyrobotics.subsystem.breacher;

import org.strongback.components.AngleSensor;
import org.strongback.components.Motor;

/**
 * Polymorphism superclass for both mock and real hardware of the breacher
 * subsystem Uses getters and setters for each hardware part
 * 
 * @author Nihar
 */

public interface BreacherSystems {
	public void setMotor(Motor motor);

	public void setPotentiometer(AngleSensor potentiometer);

	public AngleSensor getPotentiometer();

	public Motor getMotor();
}