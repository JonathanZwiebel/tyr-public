package com.palyrobotics.subsystem.breacher;

import org.strongback.components.Motor;

/**
 * Polymorphism superclass for both mock and real hardware of the breacher subsystem
 * Uses getters and setters for each hardware part
 *  @author Nihar
 */

public abstract class BreacherSystems {
	private Motor leftMotor;
	public void setLeftMotor(Motor leftMotor) {
		this.leftMotor = leftMotor;
	}
	public Motor getLeftMotor() {
		return leftMotor;
	}
	
	private Motor rightMotor;
	public Motor getRightMotor() {
		return rightMotor;
	}
	public void setRightMotor(Motor rightMotor) {
		this.rightMotor = rightMotor;
	}
}
