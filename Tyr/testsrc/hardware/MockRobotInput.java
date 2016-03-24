package hardware;

import org.strongback.components.AngleSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.mock.Mock;
import org.strongback.mock.MockAngleSensor;
import org.strongback.mock.MockDistanceSensor;
import org.strongback.mock.MockGyroscope;
import org.strongback.mock.MockSwitch;
import org.strongback.mock.MockThreeAxisAccelerometer;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.xbox.XBoxController;

//None of the modules should modify this class
public class MockRobotInput implements InputSystems {
	public final MockFlightStick driveStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockFlightStick turnStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockFlightStick shooterStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockFlightStick secondaryStick = new MockFlightStick(0,0,0,0, 0, 0);
	
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
	public AngleSensor getBreacherPotentiometer() {
		return breacherPotentiometer;
	}
	@Override
	public FlightStick getShooterStick() {
		return shooterStick;
	}
	@Override
	public FlightStick getSecondaryStick() {
		return secondaryStick;
	}
	@Override
	public XBoxController getXBox() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AngleSensor getShooterArmPotentiometer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int[] getShooterDisplacement() {
		// TODO Auto-generated method stub
		return null;
	}

	// vision to be added later
}