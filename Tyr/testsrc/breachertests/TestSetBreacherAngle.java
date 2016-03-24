package breachertests;

import static org.junit.Assert.*;
import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.strongback.mock.MockAngleSensor;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.breacher.commands.SetBreacherAngle;

import hardware.MockBreacherHardware;
import hardware.MockRobotInput;

public class TestSetBreacherAngle {
	private SetBreacherAngle set;
	private BreacherSystems hardware;
	private InputSystems input;
	private BreacherController controller;

	@Before
	public void beforeAll() {
		hardware = new MockBreacherHardware();
		input = new MockRobotInput();
		controller = new BreacherController(hardware, input);
		set = new SetBreacherAngle(controller, 45);
	}

	@Test
	public void testSetArm() {
		//while the command is not done
		while(!set.execute()) {
			//moves the potentiometer according to the movement of the motor
			((MockAngleSensor) input.getBreacherPotentiometer()).setAngle(input.getBreacherPotentiometer().getAngle() + controller.getBreacher().getMotor().getSpeed());
			
			//runs the command
			set.execute();
		}
		
		//assert that the breacher is within the acceptable error bounds
		assertTrue(Math.abs(input.getBreacherPotentiometer().getAngle() - 45) <= ACCEPTABLE_POTENTIOMETER_ERROR);

	}
}