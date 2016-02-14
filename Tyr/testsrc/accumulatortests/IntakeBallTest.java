package accumulatortests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;
import org.junit.Before;
import org.junit.Test;
import org.strongback.command.CommandTester;
import org.strongback.mock.MockSwitch;
import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.subsystem.accumulator.AccumulatorConstants;
import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.accumulator.IntakeBallAutomatic;
import hardware.MockAccumulatorHardware;
import hardware.MockRobotInput;

public class IntakeBallTest {

	private CommandTester tester;
	private IntakeBallAutomatic command;
	private AccumulatorSystems hardware;
	private AccumulatorController controller;
	private InputSystems input;
	
	
	@Before
	public void beforeMethods() {
		input = new MockRobotInput();
		hardware = new MockAccumulatorHardware();
		controller = new AccumulatorController(hardware,input);
		command = new IntakeBallAutomatic(controller,input);
		tester = new CommandTester(command);
	}

	@Test
	public void testExecute() {
		controller.setState(AccumulatorState.EJECTING);
		controller.systems.getAccumulatorMotors().setSpeed(0);
		command.initialize();
		tester.step(20);
		assertThat("Motors are not running at the correct speed", controller.systems.getAccumulatorMotors().getSpeed(), equalTo(AccumulatorConstants.ACCUMULATOR_POWER));
		assertThat("Controller is in the wrong state", AccumulatorState.ACCUMULATING, equalTo(controller.getState()));
		boolean finished = tester.step(20);
		assertThat("Command terminates early", finished, equalTo(false));
		((MockSwitch) input.getAccumulatorFilledLimitSensor()).setTriggered();
		finished = tester.step(20);
		assertThat("Command does not terminate", finished, equalTo(true));
		assertThat("Controller is the wrong state", controller.getState(), equalTo(AccumulatorState.HOLDING));
	}
	
	@Test
	public void shouldFinish() {
		command.initialize();
		boolean finished;
		((MockSwitch) input.getAccumulatorFilledLimitSensor()).setNotTriggered();
		for (int i = 1; i < 1000; i++){
			finished = tester.step(20);
			assertThat("Command terminated early", finished, equalTo(false));
		}
		((MockSwitch) input.getAccumulatorFilledLimitSensor()).setTriggered();
		finished = tester.step(20);
		assertThat("Command did not terminate", finished, equalTo(false));
	}
	
	@Test
	public void testInitialize() {
		controller.setState(AccumulatorState.IDLE);
		command.initialize();
		assertThat("Command did not initialize correctly", controller.getState(), equalTo(AccumulatorState.ACCUMULATING));
	}
}
