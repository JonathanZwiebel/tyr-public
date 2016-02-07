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

import hardware.*;
import rules.Repeat;
import rules.RepeatRule;

public class TestFullShooterTeleopCommand {
	private InputSystems input;
	private ShooterSystems output;
	private ShooterController controller;
	
	private static final float POSITIVE_JOYSTICK_VALUE = 0.5f;
	private static final float NEGATIVE_JOYSTICK_VALUE = -0.5f;
	
	private static final float NEUTRAL_MOTOR_SPEED = 0.0f;
	
	private static final float VALID_ARM_ANGLE = 10.0f;
	private static final float SMALL_ARM_ANGLE = 1.0f;

	
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
		Strongback.start();
		controller.init();
	}
	
	/**
	 * Ensures that the execute will not run if the angle is null.
	 */
	@SuppressWarnings("null")
	@Test
	public void testNullExecution() {
		thrown.expect(NullPointerException.class);
		((MockAngleSensor) controller.input.getShooterArmAngleSensor()).setAngle((Double) null);
		Strongback.disable();
	}
	
	/**
	 * Uses the CommandTester object to run the command as if it
	 * was in the queue. Ensures that it never returns true to terminate.
	 * @throws InterruptedException 
	 */
	@Test
	@Repeat(times = 10)
	public void testDoesNotTerminate() throws InterruptedException {
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_REPETITION_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		Strongback.disable();
	}

	/**
	 * Sets the angle to an acceptable degree and changes the joystick to a different pitch. 
	 * It should set the motor speed to the pitch
	 * @throws InterruptedException 
	 */
	@Test
	public void testNormalJoystickInputSetsMotor() throws InterruptedException { 
		((MockAngleSensor) controller.input.getShooterArmAngleSensor()).setAngle(VALID_ARM_ANGLE);
		((MockContinuousRange) (controller.input.getOperatorStick()).getPitch()).write(POSITIVE_JOYSTICK_VALUE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertTrue(Double.toString(controller.systems.getArmMotor().getSpeed()), controller.systems.getArmMotor().getSpeed() > NEUTRAL_MOTOR_SPEED);
		Strongback.disable();
	}
	
	/**
	 * Sets the angle to an out of range degree and changes the joystick to a different pitch. 
	 * It should set the motor speed to the pitch
	 * @throws InterruptedException 
	 */
	@Test
	public void testPositiveJoystickInputWhenAboveSetsMotorToZero() throws InterruptedException { 
		((MockAngleSensor) controller.input.getShooterArmAngleSensor()).setAngle(ShooterConstants.MAX_ARM_ANGLE + SMALL_ARM_ANGLE);
		((MockContinuousRange) (controller.input.getOperatorStick()).getPitch()).write(POSITIVE_JOYSTICK_VALUE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertTrue(Double.toString(controller.systems.getArmMotor().getSpeed()), controller.systems.getArmMotor().getSpeed() == NEUTRAL_MOTOR_SPEED);
		Strongback.disable();
	}
	
	/**
	 * Sets the angle to an out of range degree and changes the joystick to a different pitch. 
	 * It should set the motor speed to the pitch
	 * @throws InterruptedException 
	 */
	@Test
	public void testNegativeJoystickInputWhenBelowSetsMotorToZero() throws InterruptedException { 
		((MockAngleSensor) controller.input.getShooterArmAngleSensor()).setAngle(ShooterConstants.MIN_ARM_ANGLE - SMALL_ARM_ANGLE);
		((MockContinuousRange) (controller.input.getOperatorStick()).getPitch()).write(NEGATIVE_JOYSTICK_VALUE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertTrue(Double.toString(controller.systems.getArmMotor().getSpeed()), controller.systems.getArmMotor().getSpeed() == NEUTRAL_MOTOR_SPEED);
		Strongback.disable();
	}
	
	/**
	 * Sets the angle to an out of range degree and changes the joystick to a different pitch. 
	 * It should set the motor speed to the pitch
	 * @throws InterruptedException 
	 */
	@Test
	public void testPositiveJoystickInputWhenBelowActsNormal() throws InterruptedException { 
		((MockAngleSensor) controller.input.getShooterArmAngleSensor()).setAngle(ShooterConstants.MIN_ARM_ANGLE - SMALL_ARM_ANGLE);
		((MockContinuousRange) (controller.input.getOperatorStick()).getPitch()).write(POSITIVE_JOYSTICK_VALUE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertTrue(Double.toString(controller.systems.getArmMotor().getSpeed()), controller.systems.getArmMotor().getSpeed() > NEUTRAL_MOTOR_SPEED);
		Strongback.disable();
	}
	
	/**
	 * Sets the angle to an out of range degree and changes the joystick to a different pitch. 
	 * It should set the motor speed to the 
	 * @throws InterruptedException 
	 */
	@Test
	public void testNegativeJoystickInputWhenAboveActsNormal() throws InterruptedException { 
		((MockAngleSensor) controller.input.getShooterArmAngleSensor()).setAngle(ShooterConstants.MAX_ARM_ANGLE + SMALL_ARM_ANGLE);
		((MockContinuousRange) (controller.input.getOperatorStick()).getPitch()).write(NEGATIVE_JOYSTICK_VALUE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertTrue(Double.toString(controller.systems.getArmMotor().getSpeed()), controller.systems.getArmMotor().getSpeed() < NEUTRAL_MOTOR_SPEED);
		Strongback.disable();
	}
}
