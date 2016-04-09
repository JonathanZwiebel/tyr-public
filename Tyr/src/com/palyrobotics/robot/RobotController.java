package com.palyrobotics.robot;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.Strongback;
import org.strongback.hardware.Hardware;

import com.palyrobotics.robot.InputSystems.ControlScheme;
import com.palyrobotics.robot.autonomous.CompetitionLowBarAuto;
import com.palyrobotics.subsystem.accumulator.AccumulatorConstants;
import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorController.AccumulatorState;
import com.palyrobotics.subsystem.accumulator.AccumulatorHardware;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MacroBreacherState;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;
import com.palyrobotics.subsystem.breacher.BreacherHardware;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.drivetrain.DrivetrainConstants;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController.DrivetrainState;
import com.palyrobotics.subsystem.drivetrain.DrivetrainHardware;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterController.ShooterState;
import com.palyrobotics.subsystem.shooter.ShooterHardware;
import com.palyrobotics.subsystem.shooter.ShooterSystems;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterArmController.ShooterArmState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLoadingActuatorController.ShooterLoadingActuatorState;
import com.palyrobotics.subsystem.shooter.subcontrollers.ShooterLockingActuatorController.ShooterLockingActuatorState;
import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.grabber.GrabberController.GrabberState;
import com.palyrobotics.subsystem.grabber.GrabberController.MicroGrabberState;
import com.palyrobotics.subsystem.grabber.GrabberHardware;
import com.palyrobotics.subsystem.grabber.GrabberSystems;
import com.palyrobotics.xbox.Converter;
import com.palyrobotics.xbox.MockFlightStick;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotController extends IterativeRobot {
	private DrivetrainController drivetrain;
	private DrivetrainSystems drivetrainSystems;

	private AccumulatorController accumulator;
	private AccumulatorSystems accumulatorSystems;

	private BreacherController breacher;
	private BreacherSystems breacherSystems;

	private ShooterController shooter;
	private ShooterSystems shooterSystems;

	private GrabberController grabber;
	private GrabberSystems grabberSystems;

	private InputSystems input;

	private SendableChooser chooser;

	@Override
	public void robotInit() {
		try {
			Strongback.configure().recordNoData().recordNoEvents().initialize();
			Strongback.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		chooser = new SendableChooser();

		// Uses a SendableChooser to determine if an XBox is being used.
		chooser.addDefault("XBox", 2);
		chooser.addObject("Joysticks", 1);

		SmartDashboard.putData("Control Scheme", chooser);

		// Input and SendableChooser
		input = new InputHardware();

		setTyrConstants();

		// Hardware system
		accumulatorSystems = new AccumulatorHardware();
		shooterSystems = new ShooterHardware();
		drivetrainSystems = new DrivetrainHardware();
		breacherSystems = new BreacherHardware();
		grabberSystems = new GrabberHardware();

		// Subsystem controllers
		accumulator = new AccumulatorController(accumulatorSystems, input);
		drivetrain = new DrivetrainController(drivetrainSystems, input);
		shooter = new ShooterController(shooterSystems, input);
		breacher = new BreacherController(breacherSystems, input);
		grabber = new GrabberController(grabberSystems, input);

		// Begin logging
		startLogging();
		Logger.getLogger("Central").log(Level.INFO, "The RobotController was initialized.");

		input.getGyroscope().calibrate();
	}

	/**
	 * Creates a new logger that operates program-wide. Sends output to both a
	 * file and to System.err. The logger is named "Central" should be used all
	 * over the program, and should be initialized through this static method.
	 * However, on any call of Logger.getLogger("Central") it will
	 * autoinitialize.
	 */

	private static Logger startLogging() {
		Logger logger = Logger.getLogger("Central");
		ConsoleHandler console;
		FileHandler file = null;
		console = new ConsoleHandler();
		logger.addHandler(console);
		try {
			file = new FileHandler("%t/records.log");
		} catch (SecurityException | IOException e) {
			logger.log(Level.WARNING, "Error in creating log file", e);
		}
		logger.addHandler(file);
		logger.setLevel(Level.ALL);
		return logger;
	}

	@Override
	public void autonomousInit() {
		Logger.getLogger("Central").log(Level.FINE, "Autonomous init called. ");
		drivetrain.init();
		// accumulator.init();
		shooter.init();
		// breacher.init();
		// grabber.init();

		breacher.setMacroState(MacroBreacherState.AUTO);
		breacher.setMicroState(MicroBreacherState.IDLE);
		Strongback.submit(new CompetitionLowBarAuto(drivetrain, Integer.MAX_VALUE));

	}

	@Override
	public void autonomousPeriodic() {
		Logger.getLogger("Central").log(Level.INFO, "Autonomous periodic being called. ");
	}

	@Override
	public void teleopInit() {
		Logger.getLogger("Central").log(Level.INFO, "Start of teleopInit. ");
		Strongback.killAllCommands();
		Logger.getLogger("Central").log(Level.INFO, "All Strongback commands killed. ");

		// Set the control scheme
		if (chooser.getSelected().equals(1)) {
			input.setControlScheme(ControlScheme.JOYSTICKS);
			Logger.getLogger("Central").log(Level.INFO, "Using joystick control scheme. ");
		}

		else {
			input.setControlScheme(ControlScheme.XBOX);
			Logger.getLogger("Central").log(Level.INFO, "Using xbox control scheme. ");
		}

		drivetrain.init();
		accumulator.init();
		shooter.init();
		breacher.init();
		grabber.init();
		Logger.getLogger("Central").log(Level.INFO, "All subsystems initialized in teleopInit.");
		breacher.setMacroState(MacroBreacherState.TELEOP);
		breacher.setMicroState(MicroBreacherState.IDLE);
	}

	@Override
	public void teleopPeriodic() {
		Logger.getLogger("Central").log(Level.FINE, "teleopPeriodic called");
		// If we are using an xbox, convert the input from the xbox to two
		// mockjoysticks so that it can be used with all the commands
		if (input.getControlScheme().equals(ControlScheme.XBOX)) {
			Converter.convert(input.getXBox(), (MockFlightStick) input.getShooterStick(),
					(MockFlightStick) input.getSecondaryStick());
		}

		drivetrain.update();
		accumulator.update();
		shooter.update();
		breacher.update();
		grabber.update();

		updateDashboard();
		System.out.println("Gyro: " + input.getGyroscope().getAngle());

		Logger.getLogger("Central").log(Level.FINE, "Left Encoder: " + input.getLeftDriveEncoder().getAngle());
		Logger.getLogger("Central").log(Level.FINE, "Right Encoder: " + input.getRightDriveEncoder().getAngle());

		Logger.getLogger("Central").log(Level.FINE, "Gyroscope: " + input.getGyroscope().getAngle());
		Logger.getLogger("Central").log(Level.FINE,
				"Accelerometer X: " + input.getAccelerometer().getXDirection().getAcceleration());
		Logger.getLogger("Central").log(Level.FINE,
				"Accelerometer Y: " + input.getAccelerometer().getYDirection().getAcceleration());
		Logger.getLogger("Central").log(Level.FINE,
				"Accelerometer Z: " + input.getAccelerometer().getZDirection().getAcceleration());

		// State logging
		Logger.getLogger("Central").log(Level.FINE, "Drivetrain state: " + drivetrain.getDrivetrainState().toString());
		Logger.getLogger("Central").log(Level.FINE, "Accumulator state: " + accumulator.getState().toString());
		Logger.getLogger("Central").log(Level.FINE, "Shooter state: " + shooter.getState().toString());
		Logger.getLogger("Central").log(Level.FINE,
				"Shooter arm controller state: " + shooter.getShooterArmController().getState().toString());
		Logger.getLogger("Central").log(Level.FINE, "Shooter loading actuator state: "
				+ shooter.getShooterLoadingActuatorController().getState().toString());
		Logger.getLogger("Central").log(Level.FINE, "Shooter locking actuator state: "
				+ shooter.getShooterLockingActuatorController().getState().toString());
		Logger.getLogger("Central").log(Level.FINE, "Breacher macro state: " + breacher.getMacroState().toString());
		Logger.getLogger("Central").log(Level.FINE, "Breacher micro state: " + breacher.getMicroState().toString());

	}

	@Override
	public void disabledInit() {
		Logger.getLogger("Central").log(Level.INFO, "Disabled init called. ");

		try {
			Strongback.disable();
			drivetrain.disable();
			accumulator.disable();
			shooter.disable();
			breacher.setMacroState(MacroBreacherState.DISABLED);
			breacher.disable();
			grabber.disable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkDisabledStates();
	}

	/**
	 * Checks the states of subsystems and ensures they are disabled when they should be
	 */
	public void checkDisabledStates() {
		if (drivetrain.getDrivetrainState() != DrivetrainState.DISABLED) {
			Logger.getLogger("Central").log(Level.SEVERE,
					"Drivetrain state is not disabled, but is: " + drivetrain.getDrivetrainState().toString());
		}

		if (accumulator.getState() != AccumulatorState.DISABLED) {
			Logger.getLogger("Central").log(Level.SEVERE,
					"Accumulator state is not disabled, but is: " + accumulator.getState().toString());
		}

		if (shooter.getState() != ShooterState.DISABLED) {
			Logger.getLogger("Central").log(Level.SEVERE,
					"Shooter state is not disabled, but is: " + shooter.getState().toString());
		}

		if (breacher.getMacroState() != MacroBreacherState.DISABLED) {
			Logger.getLogger("Central").log(Level.SEVERE,
					"Breacher macro state is not disabled, but is: " + breacher.getMacroState().toString());
		}
		// Other breacher/grabber states not included do to their macro/micro states and inconsistency
	}

	public void setTyrConstants() {
		Logger.getLogger("Central").log(Level.INFO, "Constants set for Tyr. ");
		RobotConstants.NAME = "Tyr";
		Ports.LEFT_BACK_TALON_DEVICE_ID = Ports.LEFT_BACK_TALON_DEVICE_ID_TYR;
		Ports.LEFT_FRONT_TALON_DEVICE_ID = Ports.LEFT_FRONT_TALON_DEVICE_ID_TYR;
		Ports.RIGHT_BACK_TALON_DEVICE_ID = Ports.RIGHT_BACK_TALON_DEVICE_ID_TYR;
		Ports.RIGHT_FRONT_TALON_DEVICE_ID = Ports.RIGHT_FRONT_TALON_DEVICE_ID_TYR;
		Ports.SHOOTER_ARM_TALON_DEVICE_ID = Ports.SHOOTER_ARM_TALON_DEVICE_ID_TYR;
		Ports.BREACHER_TALON_DEVICE_ID = Ports.BREACHER_TALON_DEVICE_ID_TYR;
		Ports.GEAR_ACTUATOR_EXTEND_VALVE = Ports.GEAR_ACTUATOR_EXTEND_VALVE_TYR;
		Ports.GEAR_ACTUATOR_RETRACT_VALVE = Ports.GEAR_ACTUATOR_RETRACT_VALVE_TYR;
		Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE = Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE_TYR;
		Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE = Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE_TYR;
		Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE = Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE_TYR;
		Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE = Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE_TYR;
		Ports.GRABBER_EXTEND_VALVE = Ports.GRABBER_EXTEND_VALVE_TYR;
		Ports.GRABBER_RETRACT_VALVE = Ports.GRABBER_RETRACT_VALVE_TYR;
		SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET_TYR;
		SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_TYR;
		SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET_TYR;
		SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_TYR;
		SensorConstants.LEFT_DRIVETRAIN_DPP = SensorConstants.LEFT_DRIVETRAIN_DPP_TYR;
		SensorConstants.RIGHT_DRIVETRAIN_DPP = SensorConstants.RIGHT_DRIVETRAIN_DPP_TYR;
		AccumulatorConstants.ACCUMULATOR_POWER = 1;
	}

	public void setDericConstants() {
		Logger.getLogger("Central").log(Level.INFO, "Constants set for Deric. ");
		RobotConstants.NAME = "Deric";
		Ports.LEFT_BACK_TALON_DEVICE_ID = Ports.LEFT_BACK_TALON_DEVICE_ID_DERIC;
		Ports.LEFT_FRONT_TALON_DEVICE_ID = Ports.LEFT_FRONT_TALON_DEVICE_ID_DERIC;
		Ports.RIGHT_BACK_TALON_DEVICE_ID = Ports.RIGHT_BACK_TALON_DEVICE_ID_DERIC;
		Ports.RIGHT_FRONT_TALON_DEVICE_ID = Ports.RIGHT_FRONT_TALON_DEVICE_ID_DERIC;
		Ports.SHOOTER_ARM_TALON_DEVICE_ID = Ports.SHOOTER_ARM_TALON_DEVICE_ID_DERIC;
		Ports.BREACHER_TALON_DEVICE_ID = Ports.BREACHER_TALON_DEVICE_ID_DERIC;
		Ports.GEAR_ACTUATOR_EXTEND_VALVE = Ports.GEAR_ACTUATOR_EXTEND_VALVE_DERIC;
		Ports.GEAR_ACTUATOR_RETRACT_VALVE = Ports.GEAR_ACTUATOR_RETRACT_VALVE_DERIC;
		Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE = Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE_DERIC;
		Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE = Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE_DERIC;
		Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE = Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE_DERIC;
		Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE = Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE_DERIC;
		Ports.GRABBER_EXTEND_VALVE = Ports.GRABBER_EXTEND_VALVE_DERIC;
		Ports.GRABBER_RETRACT_VALVE = Ports.GRABBER_RETRACT_VALVE_DERIC;
		SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET_DERIC;
		SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_DERIC;
		SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET_DERIC;
		SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_DERIC;
		SensorConstants.LEFT_DRIVETRAIN_DPP = SensorConstants.LEFT_DRIVETRAIN_DPP_DERIC;
		SensorConstants.RIGHT_DRIVETRAIN_DPP = SensorConstants.RIGHT_DRIVETRAIN_DPP_DERIC;
		AccumulatorConstants.ACCUMULATOR_POWER = -1;
	}

	@Override
	public void disabledPeriodic() {
		Logger.getLogger("Central").log(Level.FINE, "Disabled periodic being called. ");
	}

	public void updateDashboard() {
		Logger.getLogger("Central").log(Level.FINE, "Dashboard updated. ");
		DrivetrainState driveState = drivetrain.getDrivetrainState();
		AccumulatorState accumulatorState = accumulator.getState();
		MicroBreacherState breacherState = breacher.getMicroState();
		GrabberState grabberState = grabber.getGrabberState();
		MicroGrabberState microGrabberState = grabber.getMicroGrabberState();
		ShooterState shooterControllerState = shooter.getState();
		ShooterArmState shooterArmState = shooter.armController.state;
		ShooterLoadingActuatorState shooterLoadingActuatorState = shooter.loadingActuatorController.state;
		ShooterLockingActuatorState shooterLockingActuatorState = shooter.lockingActuatorController.state;
		try {
			SmartDashboard.putNumber("Shooter Potentiometer", input.getShooterArmPotentiometer().getAngle());

			SmartDashboard.putNumber("Breacher Potentiometer", input.getBreacherPotentiometer().getAngle());
		} catch (Exception e) {
			System.err.println("No potentiometers");
		}
		SmartDashboard.putBoolean("Compressor status",
				Hardware.pneumaticsModule().compressorRunningSwitch().isTriggered());

		SmartDashboard.putNumber("Left Encoder", input.getLeftDriveEncoder().getAngle());

		SmartDashboard.putNumber("Right Encoder", input.getRightDriveEncoder().getAngle());

		SmartDashboard.putNumber("Gyro", input.getGyroscope().getAngle());

		if (shooter.lockingActuatorController.isLocked()) {
			SmartDashboard.putString("Shooter Lock", "Locked");
		}

		else {
			SmartDashboard.putString("Shooter Lock", "Unlocked");
		}

		if (shooter.loadingActuatorController.isFullyRetracted()) {
			SmartDashboard.putString("Shooter Loading Actuator", "Retracted");
		}

		else {
			SmartDashboard.putString("Shooter Loading Actuator", "Extended");
		}

		if (DrivetrainConstants.TELEOP_ORIENTATION == 1) {
			SmartDashboard.putString("Drivetrain Orientation", "Shooter Forward");
		}

		else {
			SmartDashboard.putString("Drivetrain Orientation", "Breacher Forward");
		}

		if (drivetrain.getOutput().getSolenoid().isRetracting()) {
			SmartDashboard.putString("Drivetrain Gear", "Low Gear");
		}

		else {
			SmartDashboard.putString("Drivetrain Gear", "High Gear");
		}

		switch (microGrabberState) {
		case LOWERED:
			SmartDashboard.putString("Grabber Position", "Lowered");
			break;
		case RAISED:
			SmartDashboard.putString("Grabber Position", "Raised");
			break;
		default:
			break;
		}

		switch (grabberState) {
		case IDLE:
			SmartDashboard.putString("Grabber State", "Idle");
			break;
		case TELEOP:
			SmartDashboard.putString("Grabber State", "Teleop");
			break;
		default:
			break;
		}

		switch (breacherState) {
		case BOUNCING:
			SmartDashboard.putString("Breacher State", "Bouncing");
			break;
		case CLOSING:
			SmartDashboard.putString("Breacher State", "Closing");
			break;
		case IDLE:
			SmartDashboard.putString("Breacher State", "Idle");
			break;
		case JOYSTICK_CONTROL:
			SmartDashboard.putString("Breacher State", "Teleop");
			break;
		case OPENING:
			SmartDashboard.putString("Breacher State", "Opening");
			break;
		case SETTING_ANGLE:
			SmartDashboard.putString("Breacher State", "Setting Angle");
			break;
		default:
			break;
		}

		switch (accumulatorState) {
		case ACCUMULATING:
			SmartDashboard.putString("Accumulator State", "Accumulating");
			break;
		case EJECTING:
			SmartDashboard.putString("Accumulator State", "Ejecting");
			break;
		case HOLDING:
			SmartDashboard.putString("Accumulator State", "Holding");
			break;
		case IDLE:
			SmartDashboard.putString("Accumulator State", "Idle");
			break;
		case RELEASING:
			SmartDashboard.putString("Accumulator State", "Releasing");
			break;
		default:
			break;
		}

		switch (driveState) {
		case DISABLED:
			SmartDashboard.putString("Drivetrain State", "Disabled");
			break;
		case DRIVING_DISTANCE:
			SmartDashboard.putString("Drivetrain State", "Driving Distance");
			break;
		case DRIVING_TELEOP:
			SmartDashboard.putString("Drivetrain State", "Driving Teleop");
			break;
		case IDLE:
			SmartDashboard.putString("Drivetrain State", "Idle");
			break;
		case SHOOTER_ALIGN:
			SmartDashboard.putString("Drivetrain State", "Shooter Align");
			break;
		case TURNING_ANGLE:
			SmartDashboard.putString("Drivetrain State", "Turning Angle");
			break;
		default:
			break;
		}

		switch (shooterControllerState) {
		case DISABLED:
			SmartDashboard.putString("Shooter Controller State", "Disabled");
			break;
		case FIRE:
			SmartDashboard.putString("Shooter Controller State", "Fire");
			break;
		case HOLD:
			SmartDashboard.putString("Shooter Controller State", "Hold");
			break;
		case IDLE:
			SmartDashboard.putString("Shooter Controller State", "Idle");
			break;
		case LOAD:
			SmartDashboard.putString("Shooter Controller State", "Load");
			break;
		default:
			break;
		}

		switch (shooterArmState) {
		case DISABLED:
			SmartDashboard.putString("Shooter Arm State", "Disabled");
			break;
		case HOLD:
			SmartDashboard.putString("Shooter Arm State", "Hold");
			break;
		case IDLE:
			SmartDashboard.putString("Shooter Arm State", "Idle");
			break;
		case SET_ANGLE:
			SmartDashboard.putString("Shooter Arm State", "Set Angle");
			break;
		case TELEOP:
			SmartDashboard.putString("Shooter Arm State", "Teleop");
			break;
		default:
			break;
		}

		switch (shooterLoadingActuatorState) {
		case DISABLED:
			SmartDashboard.putString("Shooter Loading Actuator State", "Disabled");
			break;
		case EXTEND:
			SmartDashboard.putString("Shooter Loading Actuator State", "Extend");
			break;
		case IDLE:
			SmartDashboard.putString("Shooter Loading Actuator State", "Idle");
			break;
		case RETRACT:
			SmartDashboard.putString("Shooter Loading Actuator State", "Retract");
			break;
		default:
			break;
		}

		switch (shooterLockingActuatorState) {
		case DISABLED:
			SmartDashboard.putString("Shooter Locking Actuator State", "Disabled");
			break;
		case IDLE:
			SmartDashboard.putString("Shooter Loading Actuator State", "Idle");
			break;
		case LOCK:
			SmartDashboard.putString("Shooter Loading Actuator State", "Lock");
			break;
		case UNLOCK:
			SmartDashboard.putString("Shooter Loading Actuator State", "Unlock");
			break;
		default:
			break;
		}
	}
}