package hardware;

import org.strongback.components.AngleSensor;
import org.strongback.components.Motor;
import org.strongback.mock.Mock;
import org.strongback.mock.MockAngleSensor;
import org.strongback.mock.MockMotor;

import com.palyrobotics.subsystem.breacher.BreacherSystems;

public class MockBreacherHardware implements BreacherSystems {

	private MockMotor motor = Mock.stoppedMotor();

	private MockAngleSensor potentiometer = Mock.angleSensor();

	@Override
	public void setMotor(Motor motor) {

	}

	@Override
	public Motor getMotor() {
		return motor;
	}

	@Override
	public void setPotentiometer(AngleSensor potentiometer) {

	}

	@Override
	public AngleSensor getPotentiometer() {
		return potentiometer;
	}

}