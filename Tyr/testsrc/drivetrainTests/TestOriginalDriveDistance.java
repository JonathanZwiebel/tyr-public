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
import org.strongback.mock.MockAngleSensor;
import org.strongback.mock.MockSwitch;
import org.strongback.Strongback;
import static org.hamcrest.CoreMatchers.*;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;

import static com.palyrobotics.robot.RobotConstants.*;

import hardware.MockRobotInput;
import hardware.subsystems.MockDrivetrainHardware;

public class TestOriginalDriveDistance {

	private InputSystems input;
	private DrivetrainSystems output;
	private DrivetrainController drivetrain;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public TestRule globalTimeout = Timeout.seconds(3000);

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
	public void testEncoders() {
		thrown.expect(NullPointerException.class);
		((MockAngleSensor) input.getLeftDriveEncoder()).setAngle((Double) null);
		((MockAngleSensor) input.getRightDriveEncoder()).setAngle((Double) null);
	}
	
	@Test
	public void testInitialState() throws InterruptedException {
		((MockSwitch) input.getDriveStick().getButton(0)).setTriggered(true);
		for(int i = 0; i < CYCLE_COUNT_FOR_STATE_CHANGE_UNIT_TESTS; i++) {
			float start = System.currentTimeMillis();
			//drivetrain.update();
			float end = System.currentTimeMillis();
			int duration_nano = (int) (1000000 * (end-start));
			int extra_nano = 20 * 1000000 - duration_nano;
			TimeUnit.NANOSECONDS.sleep(extra_nano);
		}
		assertThat("Drivetrain state is not driving distance. ", drivetrain.getDrivetrainState(), equalTo(DrivetrainState.DRIVING_DISTANCE));
	}
}
