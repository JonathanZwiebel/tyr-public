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
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;
import com.palyrobotics.subsystem.drivetrain.TurnAngle;

import hardware.MockDrivetrainHardware;
import hardware.MockRobotInput;

public class TestTurnAngle {
	private InputSystems input;
	private DrivetrainSystems output;
	private DrivetrainController drivetrain;
	private TurnAngle turnAngle;
	private CommandTester command;

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
}
