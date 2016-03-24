package hardware;

import org.strongback.components.Motor;
import org.strongback.mock.Mock;

import com.palyrobotics.subsystem.breacher.BreacherSystems;

public class MockBreacherHardware implements BreacherSystems {

	private Motor motor = Mock.stoppedMotor();

	public void setMotor(Motor motor) {
		this.motor = motor;
	}

	@Override
	public Motor getMotor() {
		return motor;
	}
}