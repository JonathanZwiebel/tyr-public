package com.palyrobotics.subsystem.grabber;

import static com.palyrobotics.robot.Ports.*;

import org.strongback.components.Solenoid;
import org.strongback.components.Solenoid.Direction;
import org.strongback.hardware.Hardware;


public class GrabberHardware implements GrabberSystems {

	private Solenoid grabber = Hardware.Solenoids.doubleSolenoid(GRABBER_EXTEND_VALVE, GRABBER_RETRACT_VALVE, Direction.RETRACTING);
	
	@Override
	public Solenoid getGrabber() {
		return grabber;
	}

}
