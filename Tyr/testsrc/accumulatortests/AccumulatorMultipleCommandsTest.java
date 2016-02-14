package accumulatortests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.strongback.mock.MockSwitch;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.ACCUMULATOR_POWER;
import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.EXPEL_TIME;
import static org.hamcrest.CoreMatchers.*;
import com.palyrobotics.robot.InputHardware;
import com.palyrobotics.subsystem.accumulator.AccumulatorConstants;
import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;
import com.palyrobotics.subsystem.accumulator.AccumulatorHardware;
import com.palyrobotics.subsystem.accumulator.ExpelBall;
import com.palyrobotics.subsystem.accumulator.IntakeBallAutomatic;
import com.palyrobotics.subsystem.accumulator.StopAccumulator;

public class AccumulatorMultipleCommandsTest {

	private ExpelBall expel;
	private IntakeBallAutomatic intake;
	private StopAccumulator stop;
	private AccumulatorController controller;
	private InputHardware input;
	private AccumulatorHardware hardware;
	
	@Before
	public void beforeMethods() {
		hardware = new AccumulatorHardware();
		input = new InputHardware();
		controller = new AccumulatorController(hardware,input);
		stop = new StopAccumulator(controller);
		intake = new IntakeBallAutomatic(controller, input);
		expel = new ExpelBall(controller);
	}
	
	@Test
	public void transitionStopTest() {
		intake.initialize();
		intake.execute();
		intake.interrupted();
		stop.initialize();
		stop.execute();
		assertThat("Stop command failed to make state idle", controller.getState(), equalTo(AccumulatorState.IDLE));
		assertThat("Motors were not stopped", controller.systems.getAccumulatorMotors().getSpeed(), equalTo(0));
		expel.initialize();
		expel.execute();
		expel.interrupted();
		stop.initialize();
		stop.execute();
		assertThat("Stop command failed to make state idle", controller.getState(), equalTo(AccumulatorState.IDLE));
		assertThat("Motors were not stopped", controller.systems.getAccumulatorMotors().getSpeed(), equalTo(0));
	}
	
	@Test
	public void transitionIntakeTest() {
		stop.initialize();
		stop.execute();
		intake.initialize();
		intake.execute();
		assertThat("Motors are not running at the correct speed", controller.systems.getAccumulatorMotors().getSpeed(), equalTo(AccumulatorConstants.ACCUMULATOR_POWER));
		assertThat("Controller is in the wrong state", AccumulatorState.ACCUMULATING, equalTo(controller.getState()));
		boolean finished = intake.execute();
		assertThat("Command terminates early", finished, equalTo(false));
		((MockSwitch) input.getAccumulatorFilledLimitSensor()).setTriggered();
		finished = intake.execute();
		assertThat("Command does not terminate", finished, equalTo(true));
		assertThat("Controller is the wrong state", controller.getState(), equalTo(AccumulatorState.HOLDING));
		expel.initialize();
		expel.execute();
		expel.interrupted();
		intake.initialize();
		intake.execute();
		assertThat("Motors are not running at the correct speed", controller.systems.getAccumulatorMotors().getSpeed(), equalTo(AccumulatorConstants.ACCUMULATOR_POWER));
		assertThat("Controller is in the wrong state", AccumulatorState.ACCUMULATING, equalTo(controller.getState()));
		finished = intake.execute();
		assertThat("Command terminates early", finished, equalTo(false));
		((MockSwitch) input.getAccumulatorFilledLimitSensor()).setTriggered();
		finished = intake.execute();
		assertThat("Command does not terminate", finished, equalTo(true));
		assertThat("Controller is the wrong state", controller.getState(), equalTo(AccumulatorState.HOLDING));
	}
	
	@Test
	public void transitionExpelTest() {
		boolean finished;
		stop.initialize();
		stop.execute();
		stop.interrupted();
		long begin = System.currentTimeMillis();
		expel.initialize();
		while (true){
			finished = expel.execute();
			if (System.currentTimeMillis()-begin >= EXPEL_TIME){
				break;
			}
			assertThat("Motors are not running at the correct speed", controller.systems.getAccumulatorMotors().getSpeed(),equalTo(-ACCUMULATOR_POWER));
			assertThat("Command terminates early", finished, equalTo(false));
			assertThat("Controller is in the wrong state", controller.getState(), equalTo(AccumulatorState.EJECTING));
		}
		finished = expel.execute();
		assertThat("Motors do not stop", controller.systems.getAccumulatorMotors().getSpeed(), equalTo(0));
		assertThat("Command does not terminate",finished, equalTo(true));
		assertThat("Controller does not idle", controller.getState(), equalTo(AccumulatorState.IDLE));
		intake.initialize();
		intake.execute();
		intake.interrupted();
		begin = System.currentTimeMillis();
		expel.initialize();
		while (true){
			finished = expel.execute();
			if (System.currentTimeMillis()-begin >= EXPEL_TIME){
				break;
			}
			assertThat("Motors are not running at the correct speed", controller.systems.getAccumulatorMotors().getSpeed(),equalTo(-ACCUMULATOR_POWER));
			assertThat("Command terminates early", finished, equalTo(false));
			assertThat("Controller is in the wrong state", controller.getState(), equalTo(AccumulatorState.EJECTING));
		}
		finished = expel.execute();
		assertThat("Motors do not stop", controller.systems.getAccumulatorMotors().getSpeed(), equalTo(0));
		assertThat("Command does not terminate",finished, equalTo(true));
		assertThat("Controller does not idle", controller.getState(), equalTo(AccumulatorState.IDLE));
	}
}
