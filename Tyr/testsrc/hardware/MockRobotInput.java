package hardware;

import org.strongback.mock.*;

//None of the modules should modify this class
public class MockRobotInput {
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
	
	// vision to be added later
}