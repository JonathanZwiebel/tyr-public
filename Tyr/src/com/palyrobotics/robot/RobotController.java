package com.palyrobotics.robot;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.Strongback;
import org.strongback.hardware.Hardware;

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
	private SendableChooser robotChooser;
	
	private static boolean usingXBox = true;
	
    @Override
    public void robotInit() {
    	try {
        	Strongback.configure().recordNoData().recordNoEvents().initialize();
        	Strongback.start();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }

    	//Input and SendableChooser
    	input = new InputHardware(); 
    	
    	chooser = new SendableChooser();
    	robotChooser = new SendableChooser();
	   
    	//Uses a SendableChooser to determine if an XBox is being used.
	    chooser.addDefault("XBox", input.getXBox());
	    chooser.addObject("Joysticks", 1);
	    
	    robotChooser.addDefault("Tyr", "Tyr");
	    robotChooser.addObject("Deric", "Deric");
	    	
	    SmartDashboard.putData("Control Scheme", chooser);
	    SmartDashboard.putData("Robot Chooser", robotChooser);
	    
	    if(robotChooser.getSelected().equals("Tyr")) {
    		setTyrConstants();
    	}
    	
    	if(robotChooser.getSelected().equals("Deric")) {
    		setDericConstants();
    	}
	    
	    //Hardware system
    	accumulatorSystems = new AccumulatorHardware();
    	shooterSystems = new ShooterHardware();
    	drivetrainSystems = new DrivetrainHardware();
    	breacherSystems = new BreacherHardware();
    	grabberSystems = new GrabberHardware();
    	
    	//Subsystem controllers
    	drivetrain = new DrivetrainController(drivetrainSystems, input);
    	accumulator = new AccumulatorController(accumulatorSystems, input);
    	shooter = new ShooterController(shooterSystems, input);
    	breacher = new BreacherController(breacherSystems, input);
    	grabber = new GrabberController(grabberSystems, input);
    	
    	//Begin logging
    	startLogging();
    	Logger.getLogger("Central").log(Level.INFO, "The RobotController was initialized.");
    }
    
    /**
     * Creates a new logger that operates program-wide.
     * Sends output to both a file and to System.err.
     * The logger is named "Central" should be used all over the program,
     * and should be initialized through this static method.
     * However, on any call of Logger.getLogger("Central") it will autoinitialize.
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
    
    public static boolean usingXBox() {
    	return usingXBox;
    }
    
    @Override
    public void autonomousInit() {
    	drivetrain.init();
    	accumulator.init();
    	shooter.init();
    	breacher.init();
    	grabber.init();
    }
    
    @Override
    public void teleopInit() {
    	Strongback.killAllCommands();
       	drivetrain.init();
    	accumulator.init();
    	shooter.init();
    	breacher.init();
    	grabber.init();
        
    	breacher.setMacroState(MacroBreacherState.TELEOP);
    	breacher.setMicroState(MicroBreacherState.IDLE);
    	
    	if(chooser.getSelected().equals(1)) {
    		usingXBox = false;
    	}
    	
    	else {
    		usingXBox = true;
    	}
    }

    @Override
    public void teleopPeriodic() {
    	//If we are using an xbox, convert the input from the xbox to two mockjoysticks so that it can be used with all the commands
    	if(usingXBox) {
    		Converter.convert(input.getXBox(), (MockFlightStick)input.getShooterStick(), (MockFlightStick)input.getSecondaryStick());
    	}
    	
    	updateDashboard();
    	
    	drivetrain.update();
    	accumulator.update();
    	shooter.update();
    	breacher.update();
    	grabber.update();
    	
    	Logger.getLogger("Central").log(Level.INFO, "Left Encoder: " + input.getLeftDriveEncoder().getAngle());
    	Logger.getLogger("Central").log(Level.INFO, "Right Encoder: " + input.getRightDriveEncoder().getAngle());

    	Logger.getLogger("Central").log(Level.INFO, "Gyroscope: " + input.getGyroscope().getAngle());
    	Logger.getLogger("Central").log(Level.INFO, "Accelerometer X: " + input.getAccelerometer().getXDirection().getAcceleration());
    	Logger.getLogger("Central").log(Level.INFO, "Accelerometer Y: " + input.getAccelerometer().getYDirection().getAcceleration());
    	Logger.getLogger("Central").log(Level.INFO, "Accelerometer Z: " + input.getAccelerometer().getZDirection().getAcceleration());
    }

    @Override
    public void disabledInit() {
    	drivetrain.disable();
    	accumulator.disable();
    	shooter.disable();
    	breacher.setMacroState(MacroBreacherState.DISABLED);
    	breacher.disable();
    	grabber.disable();
    	
    	try {
    	Strongback.disable();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void setTyrConstants() {
    	RobotConstants.NAME = "Tyr";
    	Ports.LEFT_BACK_TALON_DEVICE_ID = Ports.LEFT_BACK_TALON_DEVICE_ID_TYR;
    	Ports.LEFT_FRONT_TALON_DEVICE_ID = Ports.LEFT_FRONT_TALON_DEVICE_ID_TYR;
    	Ports.RIGHT_BACK_TALON_DEVICE_ID = Ports.RIGHT_BACK_TALON_DEVICE_ID_TYR;
    	Ports.RIGHT_FRONT_TALON_DEVICE_ID = Ports.RIGHT_FRONT_TALON_DEVICE_ID_TYR;
    	Ports.SHOOTER_ARM_TALON_DEVICE_ID = Ports.SHOOTER_ARM_TALON_DEVICE_ID_TYR;
    	Ports.BREACHER_TALON_DEVICE_ID = Ports.BREACHER_TALON_DEVICE_ID_TYR;
    	Ports.GEAR_ACTUATOR_EXTEND_VALVE = Ports.GEAR_ACTUATOR_EXTEND_VALVE_TYR;
    	Ports.GEAR_ACTUATOR_RETRACT_VALVE = Ports.GEAR_ACTUATOR_EXTEND_VALVE_TYR;
    	Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE = Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE_TYR;
    	Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE = Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE_TYR;
    	Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE = Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE_TYR;
    	Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE  = Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE_TYR;
    	SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET_TYR;
    	SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_TYR;
    	SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET_TYR;
    	SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_TYR;
    	SensorConstants.LEFT_DRIVETRAIN_DPP = SensorConstants.LEFT_DRIVETRAIN_DPP_TYR;
    	SensorConstants.RIGHT_DRIVETRAIN_DPP = SensorConstants.RIGHT_DRIVETRAIN_DPP_TYR;
    }
    
    public void setDericConstants() {
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
    	Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE  = Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE_DERIC;
    	SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET_DERIC;
    	SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_DERIC;
    	SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET_DERIC;
    	SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_DERIC;
    	SensorConstants.LEFT_DRIVETRAIN_DPP = SensorConstants.LEFT_DRIVETRAIN_DPP_DERIC;
    	SensorConstants.RIGHT_DRIVETRAIN_DPP = SensorConstants.RIGHT_DRIVETRAIN_DPP_DERIC;
    }
    	
    @Override
    public void disabledPeriodic() {
    }
    
    public void updateDashboard() {
    	DrivetrainState driveState = drivetrain.getDrivetrainState();
    	AccumulatorState accumulatorState = accumulator.getState();
    	MicroBreacherState breacherState = breacher.getMicroState();
    	GrabberState grabberState = grabber.getGrabberState();
    	ShooterState shooterControllerState = shooter.getState();
    	ShooterArmState shooterArmState = shooter.armController.state;
    	ShooterLoadingActuatorState shooterLoadingActuatorState = shooter.loadingActuatorController.state;
    	ShooterLockingActuatorState shooterLockingActuatorState = shooter.lockingActuatorController.state;
    	
 	    SmartDashboard.putNumber("Shooter Potentiometer", input.getShooterArmPotentiometer().getAngle());
 	    
 	    SmartDashboard.putNumber("Breacher Potentiometer", input.getBreacherPotentiometer().getAngle());
 	  
 	    SmartDashboard.putBoolean("Compressor status", Hardware.pneumaticsModule().compressorRunningSwitch().isTriggered());
 	    
 	    SmartDashboard.putNumber("Left Encoder", input.getLeftDriveEncoder().getAngle());
 	    
 	    SmartDashboard.putNumber("Right Encoder", input.getRightDriveEncoder().getAngle());
 	    
 	    SmartDashboard.putNumber("Gyro", input.getGyroscope().getAngle());
 	    
 	    SmartDashboard.putNumber("Grabber", grabberSystems.getRightServo().get());
 	    
 	    if(DrivetrainConstants.TELEOP_ORIENTATION == 1) {
 	    	SmartDashboard.putString("Drivetrain Orientation", "Shooter Forward");
 	    }
 	    
 	    else {
 	    	SmartDashboard.putString("Drivetrain Orientation", "Breacher Forward");
 	    }
 	    
 	    if(drivetrain.getOutput().getSolenoid().isRetracting()) {
 	    	SmartDashboard.putString("Drivetrain Gear", "Low Gear");
 	    }
 	    
 	    else {
 	    	SmartDashboard.putString("Drivetrain Gear", "High Gear");
 	    }
 	    
 	    switch(grabberState) {
			case IDLE:
				SmartDashboard.putString("Grabber State", "Idle");
				break;
			case TELEOP:
				SmartDashboard.putString("Grabber State", "Teleop");
				break;
			default:
				break;
 	    }
 	    
 	    switch(breacherState) {
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
 	    
 	    switch(accumulatorState) {
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
 	    
 	    switch(driveState) {
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
 	    
 	    switch(shooterControllerState) {
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
			case TELEOP:
				SmartDashboard.putString("Shooter Controller State", "Teleop");
				break;
			default:
				break;
 	    }
 	    
 	    switch(shooterArmState) {
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
 	    
 	    switch(shooterLoadingActuatorState) {
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
 	    
 	    switch(shooterLockingActuatorState) {
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