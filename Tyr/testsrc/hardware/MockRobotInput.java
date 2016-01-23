package hardware;

import org.strongback.components.AngleSensor;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.Switch;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.mock.*;
import com.palyrobotics.robot.*;

//None of the modules should modify this class
public class MockRobotInput implements InputSystems {
	public final MockFlightStick driveStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockFlightStick turnStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockFlightStick operatorStick = new MockFlightStick(0,0,0,0, 0, 0);
	
	public final MockAngleSensor leftDriveEncoder = Mock.angleSensor();
	public final MockAngleSensor rightDriveEncoder = Mock.angleSensor();
	public final MockGyroscope gyroscope = Mock.gyroscope();
	public final MockThreeAxisAccelerometer accelerometer = Mock.accelerometer3Axis();
	public final MockDistanceSensor leftInfrared = Mock.distanceSensor();
	public final MockDistanceSensor rightInfrared = Mock.distanceSensor();
	
	public final MockAngleSensor accumulatorPotentiometer = Mock.angleSensor();
	public final MockSwitch accumulatorLimitSensor = Mock.notTriggeredSwitch(); // not yet determined if switch or digital HFX
	
	public final ContinuousRange visionInput = null;
	public final MockAngleSensor shooterArmAngleSensor = Mock.angleSensor();
	public final MockSwitch shooterLoadingActuatorRetractedLimitSensor = Mock.notTriggeredSwitch();
	public final MockSwitch shooterLockingActuatorLockedLimitSensor = Mock.notTriggeredSwitch();
	
	public static final MockAngleSensor breacherPotentiometer = Mock.angleSensor();
	
	@Override
	public FlightStick getDriveStick() {
		return driveStick;
	}
	@Override
	public FlightStick getTurnStick() {
		return turnStick;
	}
	@Override
	public FlightStick getOperatorStick() {
		return operatorStick;
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
	public AngleSensor getAccumulatorPotentiometer() {
		return accumulatorPotentiometer;
	}
	@Override
	public Switch getAccumulatorFilledLimitSensor() {
		return accumulatorLimitSensor;
	}
	@Override
<<<<<<< HEAD
	public ContinuousRange getVisionInput() {
		return visionInput;
	}
	@Override
	public AngleSensor getShooterArmAngleSensor() {
		return shooterArmAngleSensor;
	}
	@Override
	public Switch getShooterArmMaximumAngleLimitSensor() {
		return null;
	}
	@Override
	public Switch getShooterArmMinimumAngleLimitSensor() {
		return null;
	}
=======
	public AngleSensor getBreacherPotentiometer() {
		return breacherPotentiometer;
	}
	
	// vision to be added later
>>>>>>> d1b0bd8... Breacher squashed
}