package com.palyrobotics.xbox;

import static com.palyrobotics.robot.Ports.*;
import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;
import static com.palyrobotics.subsystem.shooter.ShooterConstants.*;

public class Converter {
	
	/**
	 * 
	 * @param xbox the xbox
	 * @param leftStick shooter
	 * @param rightStick secondary
	 */
	public static void convert(XBoxController xbox, MockFlightStick leftStick, MockFlightStick rightStick) {
		/**
		 * XBox Mappings
		 * 
		 * Left XBox Joystick -> leftStick
		 * Right XBox Joystick -> rightStick
		 * Left Bumper -> accumulate ball
		 * Right Bumper -> close lock
		 * Left Trigger -> grabber
		 * Right Trigger -> shoot
		 * A -> shooter extend
		 * B -> shooter retract
		 * X -> expel ball
		 * Y -> stop accumulator
		 */
		
		//Controls the shooting
		boolean shoot;
		
		if(xbox.getRightTrigger().read() > 0.9) {
			shoot = true;
		}
		else {
			shoot = false;
		}
		
		//Maps the left stick of the xbox to the left joystick
		leftStick.setPitch(xbox.getLeftY().read() * xbox.getLeftY().read() * xbox.getLeftY().read());
		leftStick.setRoll(xbox.getLeftX().read());
		
		//Maps the left trigger to the yaw of the left joystick, which is used for grabber control.
		leftStick.setYaw(0.7 * xbox.getLeftTrigger().read());
		
		//Maps a to the extend actuator button of the left joystick
		leftStick.setButton(LOADING_ACTUATOR_EXTEND_OPERATOR_STICK_BUTTON, xbox.getA().isTriggered());
		
		//Maps b to the retract actuator button of the left joystick 
		leftStick.setButton(LOADING_ACTUATOR_RETRACT_OPERATOR_STICK_BUTTON, xbox.getB().isTriggered());
		
		//Maps the right trigger to the firing button of the left joystick
		leftStick.setButton(LOCKING_ACTUATOR_UNLOCK_OPERATOR_STICK_BUTTON, shoot);
		
		//Maps the right bumper to the unlock actuator button of the left joystick
		leftStick.setButton(LOCKING_ACTUATOR_LOCK_OPERATOR_STICK_BUTTON, xbox.getRightBumper().isTriggered());
		
		//Maps y to the stop button of the right joystick
		rightStick.setButton(STOP_BUTTON, xbox.getY().isTriggered());
		
		//Maps x to the accumulator expel button of the right joystick
		rightStick.setButton(EXPEL_BUTTON, xbox.getX().isTriggered());
		
		//Maps the left bumper to the accumulator accumulate button of the right joystick
		rightStick.setButton(ACCUMULATE_BUTTON, xbox.getLeftBumper().isTriggered());
		
		//Maps the right stick of the xbox to the right joystick
		rightStick.setPitch(xbox.getRightY().read() * xbox.getRightY().read() * xbox.getRightY().read());
		rightStick.setRoll(xbox.getRightX().read());
		rightStick.setYaw(xbox.getRightTrigger().read());
	}
	
}