package breachertests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherConstants;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.breacher.commands.LowerArm;

import hardware.MockRobotInput;
import hardware.subsystems.MockBreacherHardware;

public class TestLowerArm {
	private LowerArm lower;
	private BreacherSystems hardware;
	private InputSystems input;
	private BreacherController controller;
	private CommandTester command;

	@Before
	public void beforeAll() {
		hardware = new MockBreacherHardware();
		input = new MockRobotInput();
		controller = new BreacherController(hardware, input);
		lower = new LowerArm(controller);
		command = new CommandTester(lower);
	}

	@Test
	public void testLowerArm() {
		command.step(20);
		assertTrue(BreacherConstants.LOWER_SPEED == controller.getBreacher().getMotor().getSpeed());
	}
}
