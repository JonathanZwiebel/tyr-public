package breachertests;

import static org.junit.Assert.*;

import static com.palyrobotics.subsystem.breacher.BreacherConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.strongback.components.AngleSensor;
import org.strongback.components.Motor;
import org.strongback.mock.Mock;
import org.strongback.mock.MockAngleSensor;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import hardware.MockBreacherHardware;
import hardware.MockRobotInput;

public class TestBounce {
	private Motor motor;
	private BreacherController controller;
	private BreacherSystems hardware;
	private InputSystems input;
	private MockAngleSensor pot;
	
	@Before
	public void beforeAll() {
		input = new MockRobotInput();
		hardware = new MockBreacherHardware();
		controller = new BreacherController(hardware, input);
		pot = (MockAngleSensor)input.getBreacherPotentiometer();
		motor = hardware.getMotor();
		
	}
	
	@Test
	public void test() {
		double start = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - start < 5000) {
			pot.setAngle(pot.getAngle() + motor.getSpeed());
			motor.setSpeed(-0.1);
			controller.update();
			System.out.println(pot.getAngle());
		}
		
		assertFalse(input.getBreacherPotentiometer().getAngle() < MIN_POTENTIOMETER_ANGLE);
		
	}
	

}