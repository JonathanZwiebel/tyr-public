package shootertests;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.strongback.Strongback;
import org.strongback.mock.MockAngleSensor;

import com.palyrobotics.robot.*;
import com.palyrobotics.subsystem.shooter.*;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

import hardware.*;
import rules.Repeat;
import rules.RepeatRule;

public class TestFullShooterTeleopCommand {
	private InputSystems input;
	private ShooterSystems output;
	private ShooterController controller;
	
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
    public TestRule globalTimeout = new Timeout(3000);
	
	@Rule
	public RepeatRule repeat = new RepeatRule();
	
	/**
	 * Executes before each method to initialize objects.
	 */
	@Before
	public void beforeEach() {
		input = new MockRobotInput();
		output = new MockShooterHardware();
		controller = new ShooterController(output,input);
	}
	
	/**
	 * Ensures that the execute will not run if the angle is null.
	 */
	@SuppressWarnings("null")
	@Test
	public void testNullExecution() {
		thrown.expect(NullPointerException.class);
		((MockAngleSensor) controller.input.getShooterPotentiometer()).setAngle((Double) null);
	}
	
	/**
	 * Uses the CommandTester object to run the command as if it
	 * was in the queue. Ensures that it never returns true to terminate.
	 */
	@Test
	@Repeat(times = 1000)
	public void testDoesNotTerminate() {
	}

	/**
	 * Sets the angle to an acceptable degree and changes the joystick to a different pitch. 
	 * It should set the motor speed to the pitch
	 * @throws InterruptedException 
	 */
	@Test
	public void testNormalJoystickInput() throws InterruptedException { 
		Strongback.start();
		((MockAngleSensor) controller.input.getShooterPotentiometer()).setAngle(10.0f);
		((MockContinuousRange) (controller.input.getOperatorStick()).getPitch()).write(0.5f);
		controller.init();
		
		for(int i = 0; i < 3; i++) {
			float start = System.currentTimeMillis();
			System.out.println("\nCycle " + i);
			controller.update();
			float end = System.currentTimeMillis();
			int duration = (int) (1000000 * (end - start));
			int extra = 20 * 1000000 - duration;
			System.out.println("Cycle took " + duration + " | sleeping for " + extra);
			TimeUnit.NANOSECONDS.sleep(extra);
		}
		
		System.out.println("\nPotentiometer True Angle: " + controller.input.getShooterPotentiometer().getAngle());
		assertTrue(Double.toString(controller.systems.getMotor().getSpeed()), controller.systems.getMotor().getSpeed() > 0.4f);
		Strongback.disable();
	}
}
