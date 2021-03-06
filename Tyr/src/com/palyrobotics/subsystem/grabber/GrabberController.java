package com.palyrobotics.subsystem.grabber;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Requirable;
import static com.palyrobotics.robot.Buttons.*;
import com.palyrobotics.robot.*;
import com.palyrobotics.robot.InputSystems.ControlScheme;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveDownCommand;
import com.palyrobotics.subsystem.grabber.commands.GrabberMoveUpCommand;
import com.palyrobotics.subsystem.grabber.commands.GrabberTeleop;

public class GrabberController implements Requirable {
	private GrabberSystems output;
	private InputSystems robotInput;
	private SwitchReactor reactor;
	
	public enum GrabberState{
		IDLE,
		TELEOP,
	}
	
	public enum MicroGrabberState{
		RAISED,
		LOWERED
	}
	
	private MicroGrabberState microState = MicroGrabberState.RAISED;
	
	private GrabberState state;
	
	public GrabberController(GrabberSystems out, InputSystems input) {
		this.output = out;
		this.robotInput = input;
		reactor = Strongback.switchReactor();
	}
	
	public void init(){
		state = GrabberState.IDLE;
		
		if(robotInput.getControlScheme().equals(ControlScheme.XBOX)) {
			Strongback.submit(new GrabberTeleop(this, robotInput));
		}
		
		if(robotInput.getControlScheme().equals(ControlScheme.JOYSTICKS)) {
			reactor.onTriggered(robotInput.getSecondaryStick().getButton(GRABBER_UP_BUTTON), () -> Strongback.submit(new GrabberMoveUpCommand(this)));
			reactor.onTriggered(robotInput.getSecondaryStick().getButton(GRABBER_DOWN_BUTTON), () -> Strongback.submit(new GrabberMoveDownCommand(this)));
		}
	}
	
	public GrabberState getGrabberState() {
		return state;
	}
	
	public void setGrabberState(GrabberState state) {
		this.state = state;
	}
	
	public MicroGrabberState getMicroGrabberState() {
		return microState;
	}
	
	public void setMicroGrabberState(MicroGrabberState state) {
		this.microState = state;
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