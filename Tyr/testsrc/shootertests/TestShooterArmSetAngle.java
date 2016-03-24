package shootertests;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.experimental.categories.Category;
import org.strongback.Strongback;
import org.strongback.mock.MockAngleSensor;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.subsystem.shooter.ShooterConstants;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterSystems;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController.ShooterArmState;

import hardware.MockRobotInput;
import hardware.MockShooterHardware;
import suites.NonConstant;

@Category(NonConstant.class)
public class TestShooterArmSetAngle {
	private InputSystems input;
	private ShooterSystems output;
	private ShooterController controller;
	private MockAngleSensor angleSensor;
	
	private static final float SAMPLE_ANGLE = 30.0f;
	private static final float INITIAL_ANGLE = 0.0f;

	
	/**
	 * Executes before each method to initialize objects.
	 * @throws InterruptedException 
	 */
	@Before
	public void beforeEach() throws InterruptedException {
		input = new MockRobotInput();
		output = new MockShooterHardware();
		controller = new ShooterController(output,input);
		angleSensor = (MockAngleSensor) controller.input.getShooterArmPotentiometer();
		Strongback.start();
		controller.init();
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_STATE_CHANGE_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
	}
	
	@After
	public void afterEach() {
		Strongback.disable();
	}
	
	@Test
	public void testArmSetStateChangesState() throws InterruptedException {
		controller.armController.setState(ShooterArmState.SET_ANGLE, 30.0f);
		angleSensor.setAngle(INITIAL_ANGLE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_STATE_CHANGE_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertEquals(controller.armController.state, ShooterArmController.ShooterArmState.SET_ANGLE);
	}
	
	@Test
	public void testArmSetAngleExitsOnlyWhenReached() throws InterruptedException {
		controller.armController.setState(ShooterArmState.SET_ANGLE, SAMPLE_ANGLE);
		angleSensor.setAngle(INITIAL_ANGLE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertEquals(controller.armController.state, ShooterArmController.ShooterArmState.SET_ANGLE);
		angleSensor.setAngle(SAMPLE_ANGLE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertEquals(controller.armController.state, ShooterArmController.ShooterArmState.TELEOP);
	}
	
	@Test
	public void testArmSetAngleChecksDerivativeME() throws InterruptedException {
		controller.armController.setState(ShooterArmState.SET_ANGLE, SAMPLE_ANGLE);
		angleSensor.setAngle(INITIAL_ANGLE);
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		boolean adding = true;
		
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			if(adding) {
				angleSensor.setAngle(SAMPLE_ANGLE + ShooterConstants.ARM_DERIVATIVE_ME * 4);
				adding = false;
			}
			else {
				angleSensor.setAngle(SAMPLE_ANGLE - ShooterConstants.ARM_DERIVATIVE_ME * 4);
				adding = true;
			}
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		
		assertEquals(ShooterArmController.ShooterArmState.SET_ANGLE, controller.armController.state);
	}
}
