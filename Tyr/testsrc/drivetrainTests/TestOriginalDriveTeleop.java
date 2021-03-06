package drivetrainTests;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.strongback.Strongback;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;

import static org.hamcrest.CoreMatchers.*;
import static com.palyrobotics.robot.RobotConstants.*;

import hardware.MockRobotInput;
import hardware.mocks.MockFlightStick;
import hardware.subsystems.MockDrivetrainHardware;

public class TestOriginalDriveTeleop {
	
	private InputSystems input;
	private DrivetrainSystems output;
	private DrivetrainController drivetrain;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public TestRule globalTimeout = Timeout.seconds(3);
	
	@Before
	public void beforeEach() {
		input = new MockRobotInput();
		output = new MockDrivetrainHardware();
		drivetrain = new DrivetrainController(output, input);
		Strongback.start();
		drivetrain.init();
	}
	
	@After
	public void afterEach() {
		Strongback.disable();
	}
	
	@SuppressWarnings("null")
	@Test
	public void testFlightSticks() {
		thrown.expect(NullPointerException.class);
		((MockFlightStick) input.getDriveStick()).setThrottle((Double) null);
		((MockFlightStick) input.getTurnStick()).setThrottle((Double) null);
		((MockFlightStick) input.getXBox()).setThrottle((Double) null);
	}
	
	@Test
	public void testInitialState() throws InterruptedException {
		for(int i = 0; i < CYCLE_COUNT_FOR_STATE_CHANGE_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			drivetrain.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (1000000 * (end-start));
			int extra_nano = 20 * 1000000 - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		assertThat("Drivetrainstate not in teleop. ", drivetrain.getDrivetrainState(), equalTo(DrivetrainState.DRIVING_TELEOP));
	}
}
