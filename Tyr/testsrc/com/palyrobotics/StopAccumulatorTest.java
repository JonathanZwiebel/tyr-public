package com.palyrobotics;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.strongback.command.CommandTester;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.accumulator.StopAccumulator;
import hardware.MockAccumulatorHardware;
import hardware.MockRobotInput;

public class StopAccumulatorTest {
	
	private CommandTester tester;
	private StopAccumulator command;
	private AccumulatorController controller;
	private AccumulatorSystems hardware;
	private InputSystems input;
	
	
	@Before
	public void beforeMethods() {
		hardware = new MockAccumulatorHardware();
		hardware.getAccumulatorMotors().setSpeed(0.3);
		input = new MockRobotInput();
		controller = new AccumulatorController(hardware,input);
	}
	@Test
	public void testExecute() {
		command = new StopAccumulator(controller);
		command.execute();
		double speed = hardware.getAccumulatorMotors().getSpeed();
		assertThat("Motors are not set to 0", speed, equalTo(0));
		
	}
	@Test
	public void shouldEndCommand() {
		command = new StopAccumulator(controller);
		tester = new CommandTester(command);
		boolean finished = tester.step(20);
		assertThat("Command does not terminate", finished, equalTo(true));
	}

}
