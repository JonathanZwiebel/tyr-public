package shootertests;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.*;
import org.strongback.Strongback;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.robot.RobotConstants;
import com.palyrobotics.subsystem.shooter.*;
import com.palyrobotics.subsystem.shooter.ShooterController.*;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController.ShooterArmState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLoadingActuatorController.ShooterLoadingActuatorState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;

import hardware.MockRobotInput;
import hardware.MockShooterHardware;
import rules.RepeatRule;
import suites.NonConstant;

@Category(NonConstant.class)
public class TestShooterControllerInterfaceAndSetState {
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
		Strongback.start();
		controller.init();
	}
	
	/**
	 * Ensures that the ShooterController initializes all of its subsidiary controllers.
	 */
	@Test
	public void testInitAll() {
		assertThat("Did not successfully initalize armController", controller.armController.state, equalTo(ShooterArmState.IDLE));
		assertThat("Did not successfully initalize loadingAcutatorController", controller.lockingActuatorController.state, equalTo(ShooterLockingActuatorState.IDLE));
		assertThat("Did not successfully initalize lockingAcutatorController", controller.loadingActuatorController.state, equalTo(ShooterLoadingActuatorState.IDLE));
	}
	
	/**
	 * Ensures that the ShooterController disables all of its subsidiary controllers.
	 */
	@Test
	public void testDisableAll() {
		controller.disable();
		assertThat("Did not successfully disable armController", controller.armController.state, equalTo(ShooterArmState.DISABLED));
		assertThat("Did not successfully disable loadingAcutatorController", controller.lockingActuatorController.state, equalTo(ShooterLockingActuatorState.DISABLED));
		assertThat("Did not successfully disable lockingAcutatorController", controller.loadingActuatorController.state, equalTo(ShooterLoadingActuatorState.DISABLED));
	}
	
	/**
	 * Ensures that the ShooterController successfully all of its subsidiary controllers.
	 * @throws InterruptedException 
	 */
	@Test
	public void testUpdateAll() throws InterruptedException {
		for(int i = 0; i < RobotConstants.CYCLE_COUNT_FOR_BASIC_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			controller.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (RobotConstants.NANOSECONDS_PER_MILLISECOND * (end - start));
			int extra_nano = RobotConstants.MILLISECONDS_PER_UPDATE * RobotConstants.NANOSECONDS_PER_MILLISECOND - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		assertThat("Did not successfully update shooterController", controller.getState(), equalTo(ShooterState.TELEOP));
		assertThat("Did not successfully update armController", controller.armController.state, equalTo(ShooterArmState.TELEOP));
		assertThat("Error in updating loadingAcutatorController", controller.lockingActuatorController.state, equalTo(ShooterLockingActuatorState.IDLE));
		assertThat("Error in updating lockingAcutatorController", controller.loadingActuatorController.state, equalTo(ShooterLoadingActuatorState.IDLE));
	}
}
