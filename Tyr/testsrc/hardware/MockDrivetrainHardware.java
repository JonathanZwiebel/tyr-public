package hardware;

import org.strongback.components.Motor;
import org.strongback.components.PneumaticsModule;
import org.strongback.components.Solenoid;
import org.strongback.components.Solenoid.Direction;
import org.strongback.hardware.Hardware;
import org.strongback.mock.Mock;

import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class MockDrivetrainHardware implements DrivetrainSystems {
	Motor leftMotor = Mock.stoppedMotor();
	Motor rightMotor = Mock.stoppedMotor();
	public Solenoid solenoid = Mock.manualSolenoid();
	public PneumaticsModule compressor = Mock.pnuematicsModule();
	
	@Override
	public Motor getLeftMotor() {
		return leftMotor;
	}
	
	@Override
	public Motor getRightMotor() {
		return rightMotor;
	}

	@Override
	public PneumaticsModule getCompressor() {
		return compressor;
	}

	@Override
	public Solenoid getSolenoid() {
		return solenoid;
	}
}