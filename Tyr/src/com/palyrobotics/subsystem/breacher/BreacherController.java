package com.palyrobotics.subsystem.breacher;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;
import org.strongback.control.PIDController;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.commands.LowerArm;
import com.palyrobotics.subsystem.breacher.commands.RaiseArm;
import com.palyrobotics.subsystem.breacher.commands.StopArm;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

/**
 * Operates the breacher subystem Has a state for the current operation being
 * executed Has a queue of the commands for this subystem
 * 
 * @author Nihar
 */
public class BreacherController implements Requirable {

	/** Breacher systems **/
	private BreacherSystems breacher;

	private InputSystems input;
	
	private SwitchReactor reactor;

	/**
	 * Current operation being run by the breacher Locked when it shouldn't read commands
	 */
	protected BreacherState state = BreacherState.START_TELEOP;

	public enum BreacherState {
		IDLE, LOCKED, OPENING, CLOSING, START_TELEOP
	}

	public BreacherController(BreacherSystems breacher, InputSystems input) {
		this.setBreacher(breacher);
		this.input = input;
		reactor = Strongback.switchReactor();
	}

	/**
	 * Changes the breacher's state
	 * 
	 * @param state the state to change to
	 *            
	 * @return true if state change acknowledged
	 */
	public boolean setState(BreacherState state) {
		this.state = state;
		return true;
	}

	public BreacherState getState() {
		return state;
	}

	public void init() {
		// when button 1 of the operator stick is pressed, raise the arm.
		reactor.whileTriggered(input.getOperatorStick().getButton(1), () -> Strongback.submit(new RaiseArm(this)));

		// when button 1 of the operator stick has been released, stop the arm.
		reactor.onUntriggered(input.getOperatorStick().getButton(1), () -> Strongback.submit(new StopArm(this)));

		// when button 2 of the operator stick is pressed, lower the arm.
		reactor.whileTriggered(input.getOperatorStick().getButton(2), () -> Strongback.submit(new LowerArm(this)));

		// when button 2 of the operator stick has been released, stop the arm.
		reactor.onUntriggered(input.getOperatorStick().getButton(2), () -> Strongback.submit(new StopArm(this)));
	}

	public void update() {
		if (state == BreacherState.LOCKED) {
			return;
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

	}

}