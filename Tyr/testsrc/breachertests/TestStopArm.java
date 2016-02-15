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
import com.palyrobotics.subsystem.breacher.commands.StopArm;

import hardware.MockBreacherHardware;
import hardware.MockRobotInput;

public class TestStopArm {
	private StopArm stop;
	private BreacherSystems hardware;
	private InputSystems input;
	private BreacherController controller;
	private CommandTester command;

	@Before
	public void beforeAll() {
		hardware = new MockBreacherHardware();
		input = new MockRobotInput();
		controller = new BreacherController(hardware, input);
		stop = new StopArm(controller);
		command = new CommandTester(stop);
	}

	@Test
	public void testStopArm() {
		command.step(20);
		assertTrue(0 == controller.getBreacher().getMotor().getSpeed());
	}
}
