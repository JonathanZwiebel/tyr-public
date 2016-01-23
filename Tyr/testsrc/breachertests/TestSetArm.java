package breachertests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.robot.RobotController;
import com.palyrobotics.subsystem.breacher.BreacherConstants;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.breacher.commands.LowerArm;
import com.palyrobotics.subsystem.breacher.commands.SetArm;

import hardware.MockBreacherHardware;
import hardware.MockRobotInput;

public class TestSetArm {
	private SetArm set;
	private BreacherSystems hardware;
	private InputSystems input;
	private BreacherController controller;
	private RobotController robot;
	private CommandTester command;
	
	@Before
	public void beforeAll() {
		robot = new RobotController();
		hardware = new MockBreacherHardware();
		input = new MockRobotInput();
		controller = new BreacherController(hardware, input);
		set = new SetArm(controller, 45);
		command = new CommandTester(set);
	}

	@Test
	public void testSetArm() {
		command.step(20);
		assertTrue(controller.getBreacher().getPotentiometer().getAngle() == 45.0);

	}
}
