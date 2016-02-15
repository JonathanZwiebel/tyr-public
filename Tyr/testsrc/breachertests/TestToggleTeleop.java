package breachertests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.breacher.commands.StopArm;
import com.palyrobotics.subsystem.breacher.commands.ToggleTeleop;

import hardware.MockBreacherHardware;
import hardware.MockRobotInput;

public class TestToggleTeleop {
	private ToggleTeleop toggle;
	private BreacherSystems hardware;
	private InputSystems input;
	private BreacherController controller;
	private CommandTester command;

	@Before
	public void beforeAll() {
		hardware = new MockBreacherHardware();
		input = new MockRobotInput();
		controller = new BreacherController(hardware, input);
		toggle = new ToggleTeleop(controller, input);
		command = new CommandTester(toggle);
	}

	@Test
	public void testStopArm() {
		double firstValue = input.getBreacherPotentiometer().getAngle();
		command.step(20);
		assertTrue(firstValue == input.getBreacherPotentiometer().getAngle());
	}
}
