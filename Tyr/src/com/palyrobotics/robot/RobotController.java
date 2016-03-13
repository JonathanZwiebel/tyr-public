package com.palyrobotics.robot;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.Strongback;

import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorHardware;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MacroBreacherState;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;
import com.palyrobotics.subsystem.breacher.BreacherHardware;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainHardware;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterHardware;
import com.palyrobotics.subsystem.shooter.ShooterSystems;
import com.palyrobotics.subsystem.grabber.GrabberController;
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
	    SmartDashboard.putData("Robot Chooer", robotChooser);
	    
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
    	
    	if(robotChooser.getSelected().equals("Tyr")) {
    		setTyrConstants();
    	}
    	
    	if(robotChooser.getSelected().equals("Deric")) {
    		setDericConstants();
    	}
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
    	Ports.GEAR_ACTUATOR_RETRACT_VALVE = Ports.GEAR_ACTUATOR_EXTEND_VALVE_DERIC;
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
}