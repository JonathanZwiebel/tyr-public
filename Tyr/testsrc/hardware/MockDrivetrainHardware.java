package hardware;

import org.strongback.components.Motor;
import org.strongback.mock.Mock;

import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class MockDrivetrainHardware implements DrivetrainSystems {
	Motor leftMotor = Mock.stoppedMotor();
	Motor rightMotor = Mock.stoppedMotor();
	
	
	@Override
	public Motor getLeftMotor() {
		return leftMotor;
	}
	
	@Override
	public Motor getRightMotor() {
		return rightMotor;
	}

	@Override
	public Compressor getCompressor() {
		return null;
	}

	@Override
	public DoubleSolenoid getSolenoid() {
		return null;
	}
}