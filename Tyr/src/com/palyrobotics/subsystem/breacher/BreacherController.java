package com.palyrobotics.subsystem.breacher;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.commands.JoystickControl;
import com.palyrobotics.subsystem.breacher.commands.LowerArm;
import com.palyrobotics.subsystem.breacher.commands.RaiseArm;
import com.palyrobotics.subsystem.breacher.commands.StopArm;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Operates the breacher subystem
 * Has a state for the current control scheme (Teleop, auto, disabled)
 * Another state representing the current action performed
 */
public class BreacherController implements Requirable {

	/** Breacher systems **/
	private BreacherSystems breacher;

	private InputSystems input;
	
	private SwitchReactor reactor;

	/**
	 * Current control scheme of the breacher subsystem
	 */
	public enum MacroBreacherState {
		TELEOP, AUTO, DISABLED
	}
	protected MacroBreacherState macroBreacherState;
	
	/**
	 * Current operation being performed
	 */
	public enum MicroBreacherState {
		BOUNCING, IDLE, OPENING, CLOSING, SETTING_ANGLE, JOYSTICK_CONTROL
	}
	protected MicroBreacherState microBreacherState;

	public BreacherController(BreacherSystems breacher, InputSystems input) {
		this.setBreacher(breacher);
		this.input = input;
		reactor = Strongback.switchReactor();
	}

	/**
	 * Changes the breacher's macro or general state
	 * This state is for general things such as teleop, autonomous, etc.
	 * 
	 * @param state the state to change to
	 *            
	 * @return true if state change acknowledged
	 */
	public boolean setMacroState(MacroBreacherState state) {
		this.macroBreacherState = state;
		return true;
	}

	/**
	 * Gets the breacher's general state
	 * 
	 * @return the macro state
	 */
	public MacroBreacherState getMacroState() {
		return macroBreacherState;
	}

	/**
	 * Changes the breacher's micro or specific state
	 * This state is for more specific things, such as raising or lowering.
	 * 
	 * @param state the desired state
	 * @return True when state change occurred
	 */
	public boolean setMicroState(MicroBreacherState state) {
		this.microBreacherState = state;
		return true;
	}
	
	/**
	 * Gets the breacher's specific state
	 * 
	 * @return the micro state
	 */
	public MicroBreacherState getMicroState() {
		return microBreacherState;
	}
	
	/**
	 * Initializes this breacher controller.
	 * The buttons and their respective actions are defined here.
	 */
	public void init() {
    	Logger.getLogger("Central").log(Level.INFO, "The BreacherController was initialized.");
		// when button 1 of the operator stick is pressed, raise the arm.
		reactor.whileTriggered(input.getSecondaryStick().getButton(Buttons.BREACHER_RAISE_BUTTON), () -> Strongback.submit(new RaiseArm(this)));

		// when button 1 of the operator stick has been released, stop the arm.
		reactor.onUntriggered(input.getSecondaryStick().getButton(Buttons.BREACHER_RAISE_BUTTON), () -> Strongback.submit(new StopArm(this)));

		// when button 2 of the operator stick is pressed, lower the arm.
		reactor.whileTriggered(input.getSecondaryStick().getButton(Buttons.BREACHER_LOWER_BUTTON), () -> Strongback.submit(new LowerArm(this)));

		// when button 2 of the operator stick has been released, stop the arm.
		reactor.onUntriggered(input.getSecondaryStick().getButton(Buttons.BREACHER_LOWER_BUTTON), () -> Strongback.submit(new StopArm(this)));
	}

	/**
	 * Updates the breacher controller.
	 * 
	 * Stops the breacher from moving too far in either direction.
	 */
	public void update() {		
		if((getMicroState() == MicroBreacherState.IDLE )) {
			Strongback.submit(new JoystickControl(this, input));
		}
		
	}

	public BreacherSystems getBreacher() {
		return breacher;
	}

	public InputSystems getInput() {
		return input;
	}

	public void setBreacher(BreacherSystems breacher) {
		this.breacher = breacher;
	}

	public void disable() {
		breacher.getMotor().setSpeed(0);
	}

}