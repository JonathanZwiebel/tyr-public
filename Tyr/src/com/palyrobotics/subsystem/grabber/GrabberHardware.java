package com.palyrobotics.subsystem.grabber;

import edu.wpi.first.wpilibj.Servo;

public class GrabberHardware implements GrabberSystems {
	public Servo servo = new Servo(GrabberConstants.SERVO);

	@Override
	public Servo getServo() {
		// TODO Auto-generated method stub
		return this.servo;
	}
}
