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
import com.palyrobotics.subsystem.drivetrain.DriveTeleop;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;

import hardware.MockDrivetrainHardware;
import hardware.MockRobotInput;

public class TestDriveTeleop {
	
	private InputSystems input;
	private DrivetrainSystems output;
	private DrivetrainController drivetrain;
	private DriveTeleop driveTeleop;
	private CommandTester command;
	
	/**
	 * Initializes the hardware and the command. 
	 */
	@Before
	public void beforeEach() {
		input = new MockRobotInput();
		output = new MockDrivetrainHardware();
		drivetrain = new DrivetrainController(output, input);
		driveTeleop = new DriveTeleop(drivetrain);
		command = new CommandTester(driveTeleop);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public TestRule globalTimeout = new Timeout(3000);
		
	/**
	 * Ensures the command does not terminate by returning true. 
	 */
	@Test
	public void noTerminate() {
		assertFalse(command.step(20));
	}
	
	/**
	 * Ensures the flighsticks have been properly initialized. 
	 */
	@Test
	public void testFlightsticks() {
		assertNotNull(input.getDriveStick());
		assertNotNull(input.getTurnStick());
		assertNotNull(input.getOperatorStick());
	}
	
	/**
	 * Ensures the state is properly set during the initialization of the command. 
	 */
	@Test
	public void testInitializeState() {
		driveTeleop.initialize();
		command.step(20);
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.DRIVING_TELEOP);
	}
	
	/**
	 * Ensures the state is set to interrupted if the command gets interrupted. 
	 */
	@Test
	public void testInterrupted() {
		driveTeleop.interrupted();
		command.step(20);
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.IDLE);
	}
	
	/**
	 * Makes sure that the motors have stopped at the end of the command. 
	 * Also makes sure that the state is properly reset to idle at the end.
	 */
	@Test
	public void testEnd() {
		driveTeleop.end();
		command.step(20);
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.IDLE);
		assertTrue(output.getLeftMotor().getSpeed() == 0.0);
		assertTrue(output.getRightMotor().getSpeed() == 0.0);
	}
}
