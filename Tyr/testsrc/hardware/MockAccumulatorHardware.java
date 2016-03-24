package hardware;

import org.strongback.components.Motor;
import org.strongback.mock.Mock;
import org.strongback.mock.MockMotor;

import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;

public class MockAccumulatorHardware implements AccumulatorSystems {
	private MockMotor leftMotor = Mock.stoppedMotor();
	private MockMotor rightMotor = Mock.stoppedMotor();
	//TODO: may need to be the other way around depending on hardware.
	private Motor accumulatorMotors = Motor.compose(leftMotor.invert(), rightMotor);
	@Override
	public Motor getAccumulatorMotors() {
		return accumulatorMotors;
	}
}