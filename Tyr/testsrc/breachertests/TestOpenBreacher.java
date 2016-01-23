package breachertests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.breacher.commands.OpenBreacher;
import com.palyrobotics.subsystem.breacher.commands.StopArm;

import hardware.MockBreacherHardware;
import hardware.MockRobotInput;

public class TestOpenBreacher {
	private OpenBreacher open;
	private BreacherSystems hardware;
	private InputSystems input;
	private BreacherController controller;
	private CommandTester command;
	
	@Before
	public void beforeAll() {
		hardware = new MockBreacherHardware();
		input = new MockRobotInput();
		controller = new BreacherController(hardware, input);
		open = new OpenBreacher(controller, 10);
		command = new CommandTester(open);
	}

	@Test
	public void testStopArm() {
		command.step(100);
		assertTrue(0 == controller.getBreacher().getMotor().getSpeed());
	}
}
