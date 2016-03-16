package com.palyrobotics.subsystem.grabber;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;
import static com.palyrobotics.robot.Buttons.*;
import com.palyrobotics.robot.*;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveDownCommand;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveUpCommand;
import com.palyrobotics.subsystem.grabber.commands.GrabberTeleop;

public class GrabberController implements Requirable {
	private GrabberSystems output;
	private InputSystems robotInput;
	private SwitchReactor reactor;
	
	public enum GrabberState{
		IDLE,
		TELEOP
	}
	
	private GrabberState state;
	
	public GrabberController(GrabberSystems out, InputSystems hardware) {
		this.output = out;
		this.robotInput = hardware;
		reactor = Strongback.switchReactor();
	}
	
	public void init(){
		state = GrabberState.IDLE;
		
		if(RobotController.usingXBox()) {
			Strongback.submit(new GrabberTeleop(this, robotInput));
		}
		
		if(!RobotController.usingXBox()) {
			reactor.onTriggered(robotInput.getSecondaryStick().getButton(GRABBER_UP_BUTTON), () -> Strongback.submit(new GrabberMoveUpCommand(this)));
			reactor.onTriggered(robotInput.getSecondaryStick().getButton(GRABBER_DOWN_BUTTON), () -> Strongback.submit(new GrabberMoveDownCommand(this)));
		}
	}
	
	public void update(){
		state = GrabberState.TELEOP;
	}

	public GrabberSystems getOutput() {
		return output;
	}

	public InputSystems getRobotInput() {
		return robotInput;
	}	
	
	public void disable(){
		
	}
	
}