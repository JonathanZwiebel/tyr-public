package breachertests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherConstants;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.breacher.commands.RaiseArm;

import hardware.MockBreacherHardware;
import hardware.MockRobotInput;

public class TestRaiseArm {
	private RaiseArm raise;
	private BreacherSystems hardware;
	private InputSystems input;
	private BreacherController controller;
	private CommandTester command;
	
	@Before
	public void beforeAll() {
		hardware = new MockBreacherHardware();
		input = new MockRobotInput();
		controller = new BreacherController(hardware, input);
		raise = new RaiseArm(controller);
		command = new CommandTester(raise);
	}

	@Test
	public void testRaiseArm() {
		command.step(20);
		assertTrue(BreacherConstants.RAISE_SPEED == controller.getBreacher().getMotor().getSpeed());
	}
}
