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
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.ShooterAlign;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;

import hardware.MockDrivetrainHardware;
import hardware.MockRobotInput;

public class TestShooterAlign {

	private InputSystems input;
	private DrivetrainSystems output;
	private DrivetrainController drivetrain;
	private ShooterAlign shooterAlign;
	private CommandTester command;

	@Before
	public void beforeEach() {
		input = new MockRobotInput();
		output = new MockDrivetrainHardware();
		drivetrain = new DrivetrainController(output, input);
		shooterAlign = new ShooterAlign(drivetrain, null);
		command = new CommandTester(shooterAlign);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Rule
	public TestRule globalTimeout = Timeout.seconds(3);

	@Test
	public void testGyroscope() {
		assertNotNull("Gyroscope is null. ", input.getGyroscope());
	}

	/**
	 * Makes sure the state is properly set during the initialization of the
	 * command.
	 */
	@Test
	public void testInitializedState() {
		command.step(20);
		shooterAlign.initialize();
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.SHOOTER_ALIGN);
	}

	/**
	 * Makes sure the state is properly set if the command is interrupted.
	 */
	@Test
	public void testInterruptedState() {
		command.step(20);
		shooterAlign.interrupted();
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.IDLE);
	}

	/**
	 * Makes sure the state is properly set at the end of the command.
	 */
	@Test
	public void testEndState() {
		command.step(20);
		shooterAlign.end();
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.IDLE);
	}

}
