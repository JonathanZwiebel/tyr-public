package com.palyrobotics.subsystem.grabber;

import com.palyrobotics.robot.Ports;

import edu.wpi.first.wpilibj.Servo;

public class GrabberHardware implements GrabberSystems {
	public Servo servo = new Servo(Ports.GRABBER_SERVO_VICTOR_CHANNEL);

	@Override
	public Servo getServo() {
		return this.servo;
	}
}
