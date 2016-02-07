package hardware;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.mock.Mock;
import com.palyrobotics.subsystem.shooter.ShooterSystems;

public class MockShooterHardware implements ShooterSystems {
	Motor m = Mock.runningTalonSRX(0.0f);
	Solenoid l = Mock.manualSolenoid();
	Solenoid p = Mock.manualSolenoid();
	
	@Override
	public Motor getArmMotor() {
		return m;
	}

	public void setMotor(Motor motor) {
		this.m = motor;
	}

	@Override
	public Solenoid getLockingActuator() {
		return l;
	}

	public void setLatch(Solenoid latch) {
		l = latch;
	}

	@Override
	public Solenoid getLoadingActuator() {
		return p;
	}

	public void setPiston(Solenoid piston) {
		p = piston;
	}
}