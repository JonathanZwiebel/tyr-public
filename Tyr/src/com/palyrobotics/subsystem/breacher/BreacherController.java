package com.palyrobotics.subsystem.breacher;

import org.strongback.control.PIDController;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;
/**
 * Operates the breacher subystem
 * Has a state for the current operation being executed
 * Has a queue of the commands for this subystem
 * @author Nihar
 */
public class BreacherController {
	/** Breacher systems **/
	protected BreacherSystems breacher;
	/** PID Controller **/
	protected PIDController PIDController;
	/**
	 * Current operation being run by the breacher
	 * Locked when it shouldn't read commands
	 */
	protected BreacherState state = BreacherState.IDLE;
	public enum BreacherState {
		IDLE,
		LOCKED,
		OPENING,
		CLOSING
	}
	
	public BreacherController(BreacherSystems breacher) {
		this.breacher = breacher;
	}
	
	/**
	 * Changes the breacher's state
	 * @param state State to change to
	 * @return true if state change acknowledged
	 */
	public boolean setState(BreacherState state) {
		this.state = state;
		return true;
	}
	
	public void init() {
		PIDController.withGains(PROPORTIONAL, INTEGRAL, DERIVATIVE);
	}

	public void update() {
		if(state == BreacherState.LOCKED) {
			return;
		}
	}
}
