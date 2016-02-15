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
import com.palyrobotics.subsystem.drivetrain.drivetraincommands.TurnAngle;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;

import hardware.MockDrivetrainHardware;
import hardware.MockRobotInput;

public class TestTurnAngle {
	private InputSystems input;
	private DrivetrainSystems output;
	private DrivetrainController drivetrain;
	private TurnAngle turnAngle;
	private CommandTester command;

	/**
	 *Before each test, initializes the hardware and the command.  
	 */
	@Before
	public void beforeEach() {
		input = new MockRobotInput();
		output = new MockDrivetrainHardware();
		drivetrain = new DrivetrainController(output, input);
		turnAngle = new TurnAngle(drivetrain, 90);
		command = new CommandTester(turnAngle);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Rule
	public TestRule globalTimeout = new Timeout(3000);
	
	@Test
	public void testEncoders() {
		assertNotNull("Null left encoder. ", input.getLeftDriveEncoder());
		assertNotNull("Null right encoder. ", input.getRightDriveEncoder());
	}
	/**
	 * Does not work without working fake encoders
	 * that properly correspond to the fake motors. 
	 */
	@Test
	public void testTurnAngle() {
		//TODO: Relate the fake encoders with the fake motors. 
		do{
			turnAngle.execute();
		} while(turnAngle.execute() == false);
		assertTrue(input.getLeftDriveEncoder().getAngle() == 90);
		assertTrue(input.getRightDriveEncoder().getAngle() == -90);
	}
	/**
	 * Ensures the gyroscopes have been declared properly and are not null. 
	 */
	@Test
	public void testGyroscope() {
		assertNotNull(input.getGyroscope());
	}

	/**
	 * Makes sure the state is properly changed in the initialization of the
	 * command. 
	 */
	@Test
	public void testInitializeState() {
		command.step(20);
		turnAngle.initialize();
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.TURNING_ANGLE);
	}
	
	/**
	 * Ensures the state is properly reset if the command is 
	 * interrupted. 
	 */
	@Test
	public void testInterruptedState() {
		command.step(20);
		turnAngle.interrupted();
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.IDLE);
	}
	
	/**
	 * Ensures the motors are properly stopped and that the state
	 * is reset back to idle. 
	 */
	@Test
	public void testEnd() {
		command.step(20);
		turnAngle.end();
		assertTrue(output.getLeftMotor().getSpeed() == 0.0);
		assertTrue(output.getRightMotor().getSpeed() == 0.0);
		assertTrue(drivetrain.getDrivetrainState() == DrivetrainState.IDLE);
	}
}
