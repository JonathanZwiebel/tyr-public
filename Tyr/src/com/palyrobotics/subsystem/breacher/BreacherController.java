package com.palyrobotics.subsystem.breacher;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.commands.JoystickControl;
import com.palyrobotics.subsystem.breacher.commands.LowerArm;
import com.palyrobotics.subsystem.breacher.commands.RaiseArm;
import com.palyrobotics.subsystem.breacher.commands.StopArm;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

/**
 * Operates the breacher subystem Has a state for the current operation being
 * executed Has a queue of the commands for this subystem
 * 
 * @author Eric
 */
public class BreacherController implements Requirable {

	/** Breacher systems **/
	private BreacherSystems breacher;

	private InputSystems input;
	
	private SwitchReactor reactor;

	/**
	 * Current operation being run by the breacher Locked when it shouldn't read commands
	 */
	protected Macro macroState;
	
	protected Micro microState;

	public enum Macro {
		TELEOP, AUTO, DISABLED
	}
	
	public enum Micro {
		BOUNCING, IDLE, OPENING, CLOSING, SETTING, JOYSTICK
	}

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
	public boolean setMacroState(Macro state) {
		this.macroState = state;
		return true;
	}

	/**
	 * Gets the breacher's general state
	 * 
	 * @return the macro state
	 */
	public Macro getMacroState() {
		return macroState;
	}

	/**
	 * Changes the breacher's micro or specific state
	 * This state is for more specific things, such as raising or lowering.
	 * 
	 * @param state the desired state
	 * @return if it completed
	 */
	public boolean setMicroState(Micro state) {
		this.microState = state;
		return true;
	}
	
	/**
	 * Gets the breacher's specific state
	 * 
	 * @return the micro state
	 */
	public Micro getMicroState() {
		return microState;
	}
	
	/**
	 * Initializes this breacher controller.
	 * The buttons and their respective actions are defined here.
	 */
	public void init() {
		// when button 1 of the operator stick is pressed, raise the arm.
		reactor.whileTriggered(input.getSecondaryStick().getButton(RAISE_BUTTON), () -> Strongback.submit(new RaiseArm(this)));

		// when button 1 of the operator stick has been released, stop the arm.
		reactor.onUntriggered(input.getSecondaryStick().getButton(RAISE_BUTTON), () -> Strongback.submit(new StopArm(this)));

		// when button 2 of the operator stick is pressed, lower the arm.
		reactor.whileTriggered(input.getSecondaryStick().getButton(LOWER_BUTTON), () -> Strongback.submit(new LowerArm(this)));

		// when button 2 of the operator stick has been released, stop the arm.
		reactor.onUntriggered(input.getSecondaryStick().getButton(LOWER_BUTTON), () -> Strongback.submit(new StopArm(this)));
	}

	/**
	 * Updates the breacher controller.
	 * 
	 * Stops the breacher from moving too far in either direction.
	 */
	public void update() {
		
		if(input.getBreacherPotentiometer().getAngle() < MIN_POTENTIOMETER_ANGLE) {
			setMicroState(Micro.BOUNCING);
			breacher.getMotor().setSpeed(BOUNCE_SPEED);
			setMicroState(Micro.IDLE);
		}
		
		if(input.getBreacherPotentiometer().getAngle() > MAX_POTENTIOMETER_ANGLE) {
			setMicroState(Micro.BOUNCING);
			breacher.getMotor().setSpeed(-BOUNCE_SPEED);
			setMicroState(Micro.IDLE);
		}
		
		if((getMicroState() == Micro.IDLE || getMicroState() == Micro.JOYSTICK) && getMacroState() == Macro.TELEOP) {
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