package shootertests;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.strongback.command.CommandTester;
import org.strongback.mock.MockAngleSensor;

import com.palyrobotics.robot.*;
import com.palyrobotics.subsystem.shooter.*;
import com.palyrobotics.subsystem.shooter.shootercommands.ShooterArmTeleopCommand;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

import hardware.*;
import rules.Repeat;
import rules.RepeatRule;

public class TestLimited {
	private InputSystems input;
	private ShooterSystems output;
	private ShooterController con;
	private ShooterArmTeleopCommand command;
	private CommandTester ctester;
	
	
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
	public void BeforeEach() {
		input = new MockRobotInput();
		output = new MockShooterHardware();
		con = new ShooterController(output,input);
		command = new ShooterArmTeleopCommand(con);
		ctester = new CommandTester(command);
	}
	
	/**
	 * Ensures that the execute will not run if the angle is null.
	 */
	@SuppressWarnings("null")
	@Test
	public void testNullExecution() {
		thrown.expect(NullPointerException.class);
		((MockAngleSensor) con.input.getShooterPotentiometer()).setAngle((Double) null);
		ctester.step(20);
	}
	
	/**
	 * Uses the CommandTester object to run the command as if it
	 * was in the queue. Ensures that it never returns true to terminate.
	 */
	@Test
	@Repeat(times = 1000)
	public void testDoesNotTerminate() {
		assertFalse(ctester.step(20));
	}

	/**
	 * Sets the angle to an acceptable degree and changes the joystick to a different pitch. 
	 * It should set the motor speed to the pitch
	 */
	@Test
	public void testNormalJoystickInput() {
		((MockAngleSensor) con.input.getShooterPotentiometer()).setAngle(10.0);
		((MockContinuousRange) ( con.input.getOperatorStick()).getPitch()).write(0.5);
		ctester.step(20);
		assertTrue(Double.toString(con.systems.getMotor().getSpeed()), con.systems.getMotor().getSpeed() > 40.0);
	}
}
