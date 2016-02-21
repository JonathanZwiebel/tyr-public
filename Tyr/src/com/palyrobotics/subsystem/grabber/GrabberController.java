package com.palyrobotics.subsystem.grabber;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;

import com.palyrobotics.robot.*;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveDownCommand;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveUpCommand;

public class GrabberController {
	private GrabberSystems input;
	private InputSystems robotInput;
	private SwitchReactor reactor;
	
	public enum GrabberState{
		IDLE,
		TELEOP
	}
	
	private GrabberState state;
	
	public GrabberController(GrabberSystems in, InputSystems hardware){
		this.input = in;
		this.robotInput = hardware;
		reactor = Strongback.switchReactor();
	}
	
	public void init(){
		state = GrabberState.IDLE;
	}
	
	public void update(){
		state = GrabberState.TELEOP;
		reactor.onTriggered(robotInput.getSecondaryStick().getButton(GrabberConstants.DOWN_BUTTON), () -> Strongback.submit(new GrabberMoveUpCommand(this)));
		reactor.onTriggered(robotInput.getSecondaryStick().getButton(GrabberConstants.UP_BUTTON), () -> Strongback.submit(new GrabberMoveDownCommand(this)));

	}

	public GrabberSystems getInput() {
		return input;
	}

	public InputSystems getRobotInput() {
		return robotInput;
	}	
	
	public void disable(){
		
	}
	
}
