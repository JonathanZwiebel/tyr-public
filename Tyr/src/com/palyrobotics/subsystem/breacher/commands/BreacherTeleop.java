package com.palyrobotics.subsystem.breacher.commands;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;

public class BreacherTeleop extends Command {
	
	private BreacherController controller;
	private InputSystems input;
	private SwitchReactor reactor;
	
	public BreacherTeleop(BreacherController controller, InputSystems input) {
		super(controller);
		this.controller = controller;
		this.input = input;
		reactor = controller.getReactor();
	}
	
	@Override
	public boolean execute() {
		if(input.getOperatorStick().getTrigger().isTriggered()) {
			System.out.println("hi");
			Strongback.submit(new RaiseArm(controller));
		}
		
		else {
			Strongback.submit(new StopArm(controller));
		}
		reactor.onTriggered(input.getOperatorStick().getButton(1), ()->Strongback.submit(new RaiseArm(controller)));
		reactor.onUntriggered(input.getOperatorStick().getButton(1), ()->Strongback.submit(new StopArm(controller)));
		reactor.onTriggered(input.getOperatorStick().getButton(2), ()->Strongback.submit(new LowerArm(controller)));
		reactor.onUntriggered(input.getOperatorStick().getButton(2), ()->Strongback.submit(new StopArm(controller)));
		return true;
	}

}