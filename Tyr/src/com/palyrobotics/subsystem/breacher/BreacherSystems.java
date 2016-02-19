package com.palyrobotics.subsystem.breacher;

import org.strongback.components.Motor;

/**
 * Polymorphism superclass for both mock and real hardware of the breacher
 * subsystem Uses getters and setters for each hardware part
 * 
 * @author Nihar
 */

public interface BreacherSystems {
	public Motor getMotor();
}