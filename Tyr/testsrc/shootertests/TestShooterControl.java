package shootertests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.*;
import org.strongback.Strongback;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.shooter.*;
import com.palyrobotics.subsystem.shooter.shootercommands.FullShooterFireCommand;
import com.palyrobotics.subsystem.shooter.shootercontrollers.*;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterArmController.ShooterArmState;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController.*;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterLoadingActuatorController.ShooterLoadingActuatorState;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;

import hardware.MockRobotInput;
import hardware.MockShooterHardware;
import rules.RepeatRule;

public class TestShooterControl {
	private InputSystems input;
	private ShooterSystems output;
	private ShooterController controller;
	private CommandTester command_tester;
	
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
	 * Ensures that the ShooterController initializes all of its subsidiary controllers.
	 */
	@Test
	public void testInitAll() {
		controller.init();
		assertThat("Did not successfully initalize armController", controller.armController.state, equalTo(ShooterArmState.IDLE));
		assertThat("Did not successfully initalize loadingAcutatorController", controller.lockingActuatorController.state, equalTo(ShooterLockingActuatorState.IDLE));
		assertThat("Did not successfully initalize lockingAcutatorController", controller.loadingActuatorController.state, equalTo(ShooterLoadingActuatorState.IDLE));
	}
	
	/**
	 * Ensures that the ShooterController disables all of its subsidiary controllers.
	 */
	@Test
	public void testDisableAll() {
		controller.init();
		controller.disable();
		assertThat("Did not successfully disable armController", controller.armController.state, equalTo(ShooterArmState.DISABLED));
		assertThat("Did not successfully disable loadingAcutatorController", controller.lockingActuatorController.state, equalTo(ShooterLockingActuatorState.DISABLED));
		assertThat("Did not successfully disable lockingAcutatorController", controller.loadingActuatorController.state, equalTo(ShooterLoadingActuatorState.DISABLED));
	}
	
	/**
	 * Ensures that the ShooterController sucessfully all of its subsidiary controllers.
	 */
	@Test
	public void testUpdateAll() {
		controller.init();
		controller.update();
		assertThat("Did not successfully update shooterController", controller.getState(), equalTo(ShooterState.TELEOP));
		assertThat("Did not successfully update armController", controller.armController.state, equalTo(ShooterArmState.IDLE));
		assertThat("Error in updating loadingAcutatorController", controller.lockingActuatorController.state, equalTo(ShooterLockingActuatorState.IDLE));
		assertThat("Error in updating lockingAcutatorController", controller.loadingActuatorController.state, equalTo(ShooterLoadingActuatorState.IDLE));
	}
}
