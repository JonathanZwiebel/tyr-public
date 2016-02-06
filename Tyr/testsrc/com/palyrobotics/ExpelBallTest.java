package com.palyrobotics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.strongback.command.Command;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.accumulator.ExpelBall;
import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;
import hardware.MockAccumulatorHardware;
import hardware.MockRobotInput;

public class ExpelBallTest {
	
	private CommandTester tester;
	private Command command;
	private AccumulatorController controller;
	private AccumulatorSystems hardware;
	private InputSystems input;
	boolean finished;
	long begin;
	
		
	@Before
	public void beforeMethods() {
		input = new MockRobotInput();
		hardware = new MockAccumulatorHardware();
		controller = new AccumulatorController(hardware,input);
		command = new ExpelBall(controller);
		tester = new CommandTester(command);
		controller.systems.getAccumulatorMotors().setSpeed(0);
	}
	
	@Test
	public void testInitialize() {
		controller.setState(AccumulatorState.IDLE);
		command.initialize();
		assertTrue(controller.getState() == AccumulatorState.EJECTING);
	}
	
	@Test
	public void testExecute() {
		begin = System.currentTimeMillis();
		command.initialize();
		while (true){
			finished = command.execute();
			if (System.currentTimeMillis()-begin >= EXPEL_TIME){
				break;
			}
			assertTrue(controller.systems.getAccumulatorMotors().getSpeed()==-ACCUMULATOR_POWER);
			assertFalse(finished);
			assertTrue(controller.getState()==AccumulatorState.EJECTING);
		}
		finished = command.execute();
		assertTrue(controller.systems.getAccumulatorMotors().getSpeed()==0);
		assertTrue(finished);
		assertTrue(controller.getState()==AccumulatorState.IDLE);
	}
}