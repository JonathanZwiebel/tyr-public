package com.palyrobotics.xbox;

import edu.wpi.first.wpilibj.Joystick;

public class XBox {
	public static XBoxController getXBox(int port) {
		Joystick joystick = new Joystick(port);
		return XBoxController.create(joystick::getRawAxis, //axis to value
                joystick::getRawButton, //button number to switch
                joystick::getPOV, //dpad
                () -> joystick.getRawAxis(0), //leftx
                () -> joystick.getRawAxis(1) * -1, //lefty
                () -> joystick.getRawAxis(4), //rightx
                () -> joystick.getRawAxis(5) * -1,//righty
                () -> joystick.getRawAxis(2),//lefttrigger
                () -> joystick.getRawAxis(3),//righttrigger
                () -> joystick.getRawButton(5),//leftbumper
                () -> joystick.getRawButton(6),//rightbumper
                () -> joystick.getRawButton(1),//buttona
                () -> joystick.getRawButton(2),//buttonb
                () -> joystick.getRawButton(3),//buttonx
                () -> joystick.getRawButton(4), //buttony
                () -> joystick.getRawButton(9), //left stick
                () -> joystick.getRawButton(10)); // right stick
	}
}