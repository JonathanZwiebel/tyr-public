package hardware;

import org.strongback.components.AngleSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.ThreeAxisAccelerometer;
import org.strongback.components.ui.FlightStick;
import org.strongback.mock.Mock;
import org.strongback.mock.MockAngleSensor;
import org.strongback.mock.MockDistanceSensor;
import org.strongback.mock.MockGyroscope;
import org.strongback.mock.MockSwitch;
import org.strongback.mock.MockThreeAxisAccelerometer;

import com.palyrobotics.robot.InputSystems;
import com.palyrobotics.xbox.XBoxController;

import edu.wpi.first.wpilibj.AnalogGyro;
import hardware.mocks.MockFlightStick;
import hardware.mocks.MockXBoxController;

//None of the modules should modify this class
public class MockRobotInput implements InputSystems {
	public final MockFlightStick driveStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockFlightStick turnStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockFlightStick shooterStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockFlightStick secondaryStick = new MockFlightStick(0,0,0,0, 0, 0);
	public final MockXBoxController xbox = new MockXBoxController(0, 0, 0, 0, 0, 0, 0, 0);
	
	public final MockAngleSensor leftDriveEncoder = Mock.angleSensor();
	public final MockAngleSensor rightDriveEncoder = Mock.angleSensor();
	public final AnalogGyro gyroscope = null;
	public final MockThreeAxisAccelerometer accelerometer = Mock.accelerometer3Axis();
	public final MockDistanceSensor leftInfrared = Mock.distanceSensor();
	public final MockDistanceSensor rightInfrared = Mock.distanceSensor();
	
	public final MockAngleSensor accumulatorPotentiometer = Mock.angleSensor();
	public final MockSwitch accumulatorLimitSensor = Mock.notTriggeredSwitch(); // not yet determined if switch or digital HFX
		
	public final MockAngleSensor shooterArmAngleSensor = Mock.angleSensor();
	public final MockSwitch shooterLoadingActuatorRetractedLimitSensor = Mock.notTriggeredSwitch();
	public final MockSwitch shooterLockingActuatorLockedLimitSensor = Mock.notTriggeredSwitch();
	
	public final MockAngleSensor breacherPotentiometer = Mock.angleSensor();

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
	public AnalogGyro getGyroscope() {
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
		return xbox;
	}
	
	@Override
	public AngleSensor getShooterArmPotentiometer() {
		return shooterArmAngleSensor;
	}
	
	@Override
	public int[] getShooterDisplacement() {
		int[] a = {0,0};
		return a;
	}
	@Override
	public void setControlScheme(ControlScheme control) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ControlScheme getControlScheme() {
		// TODO Auto-generated method stub
		return null;
	}
}