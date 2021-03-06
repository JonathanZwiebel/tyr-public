package hardware.subsystems;

import org.strongback.components.Solenoid;
import org.strongback.components.TalonSRX;
import org.strongback.mock.Mock;
import com.palyrobotics.subsystem.shooter.ShooterSystems;

public class MockShooterHardware implements ShooterSystems {
	TalonSRX m = Mock.runningTalonSRX(0.0f);
	Solenoid l = Mock.manualSolenoid();
	Solenoid p = Mock.manualSolenoid();
	
	@Override
	public TalonSRX getArmMotor() {
		return m;
	}

	public void setMotor(TalonSRX motor) {
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