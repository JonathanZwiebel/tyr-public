package hardware;

import org.strongback.components.AngleSensor;
import org.strongback.components.Motor;
import org.strongback.mock.Mock;
import org.strongback.mock.MockMotor;

import com.palyrobotics.subsystem.shooter.ShooterSystems;

public class MockShooterHardware implements ShooterSystems {
	Motor m = Mock.stoppedMotor();
	
	@Override
	public Motor getMotor() {
		// TODO Auto-generated method stub
		return m;
	}

	@Override
	public void setMotor(Motor motor) {
		this.m = motor;
	}

	@Override
	public AngleSensor getArmEncoder() {
		// TODO Auto-generated method stub
		return null;
	}
	
}