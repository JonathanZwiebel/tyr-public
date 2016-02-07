package drivetrainTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.strongback.command.CommandTester;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.drivetrain.DriveDistance;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;

import hardware.MockDrivetrainHardware;
import hardware.MockRobotInput;

public class TestDriveDistance {

	private InputSystems input;
	private DrivetrainSystems output;
	private DrivetrainController drivetrain;
	private DriveDistance driveDist;
	private CommandTester command;
	
	@Before
	public void beforeEach() {
		input = new MockRobotInput();
		output = new MockDrivetrainHardware();
		drivetrain = new DrivetrainController(output, input);
		driveDist = new DriveDistance(drivetrain, 50);
		command = new CommandTester(driveDist);
	}
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public TestRule globalTimeout = new Timeout(3000);
	
	/**
	 * Tests the encoders to make sure they are not null.
	 */
	@Test
	public void testEncoders() {
		assertNotNull(input.getLeftDriveEncoder().getAngle());
		assertNotNull(input.getRightDriveEncoder().getAngle());
	}
	
	@Test
	public void noTerminate() {
		assertFalse(command.step(20));
	}

	@Test
	public void testInitialState() {
		command.step(20);
		driveDist.initialize();
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.DRIVING_DISTANCE);
	}
	
	@Test
	public void testInterruptedState() {
		command.step(20);
		driveDist.interrupted();
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.IDLE);
	}
	
	@Test
	public void testEndState() {
		command.step(20);
		driveDist.end();
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.IDLE);
	}
	
}
