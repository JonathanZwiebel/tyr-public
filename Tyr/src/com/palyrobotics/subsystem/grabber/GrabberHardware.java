package com.palyrobotics.subsystem.grabber;

import static com.palyrobotics.robot.Ports.*;

import edu.wpi.first.wpilibj.Servo;

public class GrabberHardware implements GrabberSystems {
	public Servo rightServo = new Servo(RIGHT_GRABBER_SERVO_VICTOR_CHANNEL);
	public Servo leftServo = new Servo(LEFT_GRABBER_SERVO_VICTOR_CHANNEL);
	
	@Override
	public Servo getRightServo() {
		return this.rightServo;
	}
	
	@Override
	public Servo getLeftServo() {
		return this.leftServo;
	}
}
