package hardware;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.mock.Mock;
import com.palyrobotics.subsystem.shooter.ShooterSystems;

public class MockShooterHardware implements ShooterSystems {
	Motor m = Mock.stoppedMotor();
	Solenoid l = Mock.manualSolenoid();
	Solenoid p = Mock.manualSolenoid();
	
	@Override
	public Motor getMotor() {
		return m;
	}

	public void setMotor(Motor motor) {
		this.m = motor;
	}

	@Override
	public Solenoid getLatch() {
		return l;
	}

	public void setLatch(Solenoid latch) {
		l = latch;
	}

	@Override
	public Solenoid getPiston() {
		return p;
	}

	public void setPiston(Solenoid piston) {
		p = piston;
	}
}