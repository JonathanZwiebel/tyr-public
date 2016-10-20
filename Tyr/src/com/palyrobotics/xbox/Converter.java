package com.palyrobotics.xbox;

import static com.palyrobotics.robot.Buttons.*;

public class Converter {
	
	/**
	 * 
	 * @param xbox the xbox
	 * @param leftStick expeler
	 * @param rightStick secondary
	 */
	public static void convert(XBoxController xbox, MockFlightStick leftStick, MockFlightStick rightStick) {
		/**
		 * XBox Mappings
		 * 
		 * Right Joystick Button -> breacher hold button
		 * Left XBox Joystick -> leftStick
		 * Right XBox Joystick -> rightStick
		 * Right XBox Joystick Button -> breacher hold
		 * Left XBox Joystick Button -> expeler hold
		 * Left Bumper -> toggle grabber
		 * Right Bumper -> close lock
		 * Left Trigger -> intake
		 * Right Trigger -> expel
		 * A -> expeler extend
		 * B -> expeler retract
		 * X -> expel ball
		 * Y -> stop accumulator
		 */
		
		//Controls the expeling
		boolean expel;
		
		//Controls the accumulating
		boolean intake;
		
		if(xbox.getRightTrigger().read() > 0.9) {
			expel = true;
		}
		else {
			expel = false;
		}
		
		if(xbox.getLeftTrigger().read() > 0.9) {
			intake = true;
		}
		else {
			intake = false;
		}
		
		//Maps the left stick of the xbox to the left joystick
		leftStick.setPitch(xbox.getLeftY().read() * xbox.getLeftY().read() * xbox.getLeftY().read());
		leftStick.setRoll(xbox.getLeftX().read());
		
		//Maps the left trigger to the yaw of the left joystick
		leftStick.setYaw(xbox.getLeftTrigger().read());
		
		//Maps a to the extend actuator button of the left joystick
		leftStick.setButton(LOADING_ACTUATOR_RETRACT_OPERATOR_STICK_BUTTON, xbox.getB().isTriggered());
		
		//Maps b to the retract actuator button of the left joystick 
		leftStick.setButton(LOADING_ACTUATOR_EXTEND_OPERATOR_STICK_BUTTON, xbox.getX().isTriggered());
		
		//Maps the right trigger to the firing button of the left joystick
		leftStick.setButton(LOCKING_ACTUATOR_LOCK_OPERATOR_STICK_BUTTON, xbox.getA().isTriggered());
		
		//Maps the right bumper to the unlock actuator button of the left joystick
		leftStick.setButton(LOCKING_ACTUATOR_UNLOCK_OPERATOR_STICK_BUTTON, xbox.getY().isTriggered());
				
		//Maps the left bumper to the grabber toggle button of the right joystick
		rightStick.setButton(GRABBER_TOGGLE_BUTTON, xbox.getLeftBumper().isTriggered());
		
		//Maps x to the accumulator expel button of the right joystick
		rightStick.setButton(ACCUMULATOR_EXPEL_BUTTON, expel);
		
		//Maps the left trigger to the accumulator intake button of the right joystick
		rightStick.setButton(ACCUMULATOR_INTAKE_BUTTON, intake);
		
		//Maps the left joystick button to the expeler hold button of the left joystick
		leftStick.setButton(SHOOTER_ARM_HOLD_OPERATOR_STICK_BUTTON, xbox.getLeftStickPressed().isTriggered());
		
		//Maps the right joystick button to the breacher hold button of the right joystick
		rightStick.setButton(BREACHER_HOLD_BUTTON, xbox.getRightStickPressed().isTriggered());
		
		//Maps the right stick of the xbox to the right joystick
		rightStick.setPitch(xbox.getRightY().read() * xbox.getRightY().read() * xbox.getRightY().read());
		rightStick.setRoll(xbox.getRightX().read());
		
		//Maps the right trigger of the xbox to the yaw of the right joystick
		rightStick.setYaw(xbox.getRightTrigger().read());
	}
	
}