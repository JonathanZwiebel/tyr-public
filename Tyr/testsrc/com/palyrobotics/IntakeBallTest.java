package com.palyrobotics;

import static org.junit.Assert.*;
import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;
import org.strongback.mock.MockSwitch;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.accumulator.AccumulatorConstants;
import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.accumulator.IntakeBall;

import hardware.MockAccumulatorHardware;
import hardware.MockRobotInput;

public class IntakeBallTest {

	private CommandTester tester;
	private IntakeBall command;
	private AccumulatorSystems hardware;
	private AccumulatorController controller;
	private InputSystems input;
	
	
	@Before
	public void beforeMethods() {
		input = new MockRobotInput();
		hardware = new MockAccumulatorHardware();
		controller = new AccumulatorController(hardware,input);
		command = new IntakeBall(controller,input);
		tester = new CommandTester(command);
	}

	@Test
	public void testExecute() {
		controller.setState(AccumulatorState.EJECTING);
		controller.systems.getAccumulatorMotors().setSpeed(0);
		command.initialize();
		tester.step(20);
		assertTrue(controller.systems.getAccumulatorMotors().getSpeed()==AccumulatorConstants.ACCUMULATOR_POWER);
		assertTrue(AccumulatorState.ACCUMULATING==controller.getState());
		boolean finished = tester.step(20);
		assertFalse(finished);
		((MockSwitch) input.getAccumulatorFilledLimitSensor()).setTriggered();
		finished = tester.step(20);
		assertTrue(finished);
		assertTrue(controller.getState()==AccumulatorState.HOLDING);
	}
	
	@Test
	public void shouldFinish() {
		command.initialize();
		boolean finished;
		((MockSwitch) input.getAccumulatorFilledLimitSensor()).setNotTriggered();
		for (int i = 1; i < 1000; i++){
			finished = tester.step(20);
			assertFalse(finished);
		}
		((MockSwitch) input.getAccumulatorFilledLimitSensor()).setTriggered();
		finished = tester.step(20);
		assertTrue(finished);
	}
	
	@Test
	public void testInitialize() {
		controller.setState(AccumulatorState.IDLE);
		command.initialize();
		assertTrue(controller.getState()==AccumulatorState.ACCUMULATING);
	}
}
