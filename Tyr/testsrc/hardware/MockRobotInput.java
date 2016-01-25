package hardware;

import org.strongback.components.AngleSensor;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.Switch;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;
import org.strongback.mock.*;
import com.palyrobotics.robot.*;

//None of the modules should modify this class
public class MockRobotInput implements InputSystems {
	// When build delivers we will define these, until then, do not touch
	
	public static final MockAngleSensor leftDriveEncoder = Mock.angleSensor();
	public static final MockAngleSensor rightDriveEncoder = Mock.angleSensor();
	public static final MockGyroscope gyroscope = Mock.gyroscope();
	public static final MockThreeAxisAccelerometer accelerometer = Mock.accelerometer3Axis();
	public static final MockDistanceSensor leftInfrared = Mock.distanceSensor();
	public static final MockDistanceSensor rightInfrared = Mock.distanceSensor();
	
	public static final MockAngleSensor shooterPotentiometer = Mock.angleSensor();
	public static final MockSwitch shooterLimitSensor = Mock.notTriggeredSwitch(); // not yet determined if switch or digital HFX
	
	public static final MockAngleSensor accumulatorPotentiometer = Mock.angleSensor();
	public static final MockSwitch accumulatorLimitSensor = Mock.notTriggeredSwitch(); // not yet determined if switch or digital HFX
	
	@Override
	public FlightStick getDriveStick() {
		return null;
	}
	@Override
	public FlightStick getTurnStick() {
		return null;
	}
	@Override
	public FlightStick getOperatorStick() {
		return null;
	}
	@Override
	public AngleSensor getLeftDriveEncoder() {
		return leftDriveEncoder;
	}
	@Override
	public AngleSensor getRightDriveEncoder() {
		return rightDriveEncoder;
	}
	@Override
	public Gyroscope getGyroscope() {
		return gyroscope;
	}
	@Override
	public ThreeAxisAccelerometer getAccelerometer() {
		return accelerometer;
	}
	@Override
	public DistanceSensor getLeftInfrared() {
		return leftInfrared;
	}
	@Override
	public DistanceSensor getRightInfrared() {
		return rightInfrared;
	}
	@Override
	public AngleSensor getShooterPotentiometer() {
		return shooterPotentiometer;
	}
	@Override
	public Switch getShooterDrawbackLimitSensor() {
		return shooterLimitSensor;
	}
	@Override
	public AngleSensor getAccumulatorPotentiometer() {
		return accumulatorPotentiometer;
	}
	@Override
	public Switch getAccumulatorFilledLimitSensor() {
		return accumulatorLimitSensor;
	}
	
	// vision to be added later
}