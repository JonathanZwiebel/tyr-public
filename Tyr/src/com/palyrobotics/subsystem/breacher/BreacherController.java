package com.palyrobotics.subsystem.breacher;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;
import org.strongback.control.PIDController;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.commands.RaiseArm;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;
/**
 * Operates the breacher subystem
 * Has a state for the current operation being executed
 * Has a queue of the commands for this subystem
 * @author Nihar
 */
public class BreacherController implements Requirable {
	
	/** Breacher systems **/
	private BreacherSystems breacher;
	
	/** PID Controller **/
	private PIDController PIDController;
	
	protected InputSystems input;
	
	private SwitchReactor reactor;
	
	/**
	 * Current operation being run by the breacher
	 * Locked when it shouldn't read commands
	 */
	protected BreacherState state = BreacherState.START_TELEOP;
	public enum BreacherState {
		IDLE,
		LOCKED,
		OPENING,
		CLOSING,
		START_TELEOP
	}
	
	public BreacherController(BreacherSystems breacher, InputSystems input) {
		this.setBreacher(breacher);
		this.input = input;
		this.reactor = reactor;
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
	
	public BreacherState getState() {
		return state;
	}

	public void init() {
		//PIDController.withGains(PROPORTIONAL, INTEGRAL, DERIVATIVE);
	}

	public void update() {
		System.out.println("Potentiometer angle: " + breacher.getPotentiometer().getAngle());
		if(state == BreacherState.LOCKED) {
			return;
		}
//		if(state== BreacherState.START_TELEOP){
//			Strongback.submit(new BreacherTeleop(this,input));
//		}
		Strongback.submit(new RaiseArm(this));
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
	
	public SwitchReactor getReactor() {
		return reactor;
	}
	
	public void disable() {
		
	}

	public PIDController getPIDController() {
		return PIDController;
	}

}
