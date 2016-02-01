package hardware;

import org.strongback.components.AngleSensor;
import org.strongback.components.Motor;
import org.strongback.mock.Mock;

import com.palyrobotics.subsystem.shooter.ShooterSystems;

public class MockShooterHardware implements ShooterSystems {
	Motor m = Mock.stoppedMotor();
	AngleSensor s = Mock.angleSensor();
	
	@Override
	public Motor getMotor() {
		return m;
	}

	@Override
	public void setMotor(Motor motor) {
		this.m = motor;
	}

	@Override
	public AngleSensor getArmEncoder() {
		return s;
	}
	
}