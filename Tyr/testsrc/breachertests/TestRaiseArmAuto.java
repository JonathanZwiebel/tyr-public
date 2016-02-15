package breachertests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherConstants;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.breacher.commands.RaiseArmAuto;

import hardware.MockBreacherHardware;
import hardware.MockRobotInput;

public class TestRaiseArmAuto {
	private RaiseArmAuto raise;
	private BreacherSystems hardware;
	private InputSystems input;
	private BreacherController controller;
	private CommandTester command;

	@Before
	public void beforeAll() {
		hardware = new MockBreacherHardware();
		input = new MockRobotInput();
		controller = new BreacherController(hardware, input);
		raise = new RaiseArmAuto(controller);
		command = new CommandTester(raise);
	}

	@Test
	public void testRaiseArmAuto() {
		command.step(20);
		if (System.currentTimeMillis() - 20 < BreacherConstants.OPEN_TIME) {
			assertTrue(BreacherConstants.RAISE_SPEED == controller.getBreacher().getMotor().getSpeed());
		} else {
			assertTrue(controller.getBreacher().getMotor().getSpeed() == 0);
		}
	}
}
