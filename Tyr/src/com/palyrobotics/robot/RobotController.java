package com.palyrobotics.robot;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.strongback.Strongback;

import com.palyrobotics.robot.InputSystems.ControlScheme;
import com.palyrobotics.robot.autonomous.CompetitionBD;
import com.palyrobotics.robot.autonomous.CompetitionBackTouch;
import com.palyrobotics.robot.autonomous.CompetitionLowBar;
import com.palyrobotics.robot.autonomous.CompetitionMoatTwentyPointAuto;
import com.palyrobotics.robot.autonomous.CompetitionTwentyPointAuto;
import com.palyrobotics.robot.autonomous.CompetitionWaitBD;
import com.palyrobotics.robot.autonomous.CompetitionWaitBackTouch;
import com.palyrobotics.robot.autonomous.CompetitionWaitLowBar;
import com.palyrobotics.subsystem.accumulator.AccumulatorConstants;
import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorHardware;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MacroBreacherState;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;
import com.palyrobotics.subsystem.breacher.BreacherHardware;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.drivetrain.DrivetrainConstants;
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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RobotController extends IterativeRobot {
	private DriverStation ds;
	
	private Dashboard dashboard;
	
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
	
	private NetworkTable robotTable;
	
	private String autoPath;
	
	protected enum autonomousPaths {
		LOWBAR,
		WAITLOWBAR,
		BD,
		WAITBD,
		BACKTOUCH,
		WAITBACKTOUCH,
		TWENTYPT, //cant call it 20pt lol
		MOAT20PT,
		WRONG, 
		NONE
	}
	
	private autonomousPaths behavior_manager;
	
    @Override
    public void robotInit() {
    	try {
        	Strongback.configure().recordNoData().recordNoEvents().initialize();
        	Strongback.start();
        }
        catch(Exception e) {
        	e.printStackTrace();
        } 
	   
	    setTyrConstants();
    	
	    //Input and SendableChooser
    	input = new InputHardware();
    	
    	//Hardware system
    	accumulatorSystems = new AccumulatorHardware();
    	shooterSystems = new ShooterHardware();
    	drivetrainSystems = new DrivetrainHardware();
    	breacherSystems = new BreacherHardware();
    	grabberSystems = new GrabberHardware();
    	
    	//Subsystem controllers
    	accumulator = new AccumulatorController(accumulatorSystems, input);
    	drivetrain = new DrivetrainController(drivetrainSystems, input);
    	shooter = new ShooterController(shooterSystems, input);
    	breacher = new BreacherController(breacherSystems, input);
    	grabber = new GrabberController(grabberSystems, input);
    	
    	robotTable = NetworkTable.getTable("RobotTable");
    	ds = DriverStation.getInstance();
    	behavior_manager = autonomousPaths.NONE;
    	
    	dashboard = new Dashboard(ds, accumulator, drivetrain, shooter, breacher, grabber, behavior_manager);
    	dashboard.initDashboard();
    	
    	//Begin logging
    	startLogging();
    	
    	input.getGyroscope().calibrate();
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
    
    @Override
    public void autonomousInit() {
       	switch(behavior_manager) {
		case BACKTOUCH:
			Strongback.submit(new CompetitionBackTouch(drivetrain, shooter));
			break;
		case BD:
			Strongback.submit(new CompetitionBD(drivetrain, shooter));
			break;
		case LOWBAR:
			Strongback.submit(new CompetitionLowBar(drivetrain, shooter));
			break;
		case MOAT20PT:
			Strongback.submit(new CompetitionMoatTwentyPointAuto(drivetrain, shooter, grabber, accumulator));
			break;
		case NONE:
			//no auto :(
			break;
		case TWENTYPT:
			Strongback.submit(new CompetitionTwentyPointAuto(drivetrain, shooter, grabber, accumulator));
			break;
		case WAITBACKTOUCH:
			Strongback.submit(new CompetitionWaitBackTouch(drivetrain, shooter));
			break;
		case WAITBD:
			Strongback.submit(new CompetitionWaitBD(drivetrain, shooter));
			break;
		case WAITLOWBAR:
			Strongback.submit(new CompetitionWaitLowBar(drivetrain, shooter));
			break;
		case WRONG:
			//happens if you enter an invalid auto path :(
			break;
		default:
			break;
       	}
    }
    
    @Override
    public void teleopInit() {
    	Strongback.killAllCommands();
    	
    	//Set the control scheme
    	input.setControlScheme(ControlScheme.XBOX);
    	
	    drivetrain.init();
	    accumulator.init();
	    shooter.init();
	    breacher.init();
	    grabber.init();
    	
	    breacher.setMacroState(MacroBreacherState.TELEOP);
	    breacher.setMicroState(MicroBreacherState.IDLE);
    }

    @Override
    public void teleopPeriodic() {
    	//If we are using an xbox, convert the input from the xbox to two mockjoysticks so that it can be used with all the commands
    	if(input.getControlScheme().equals(ControlScheme.XBOX)) {
    		Converter.convert(input.getXBox(), (MockFlightStick)input.getShooterStick(), (MockFlightStick)input.getSecondaryStick());
    	}
    	
    	//updateDashboard();
    	dashboard.updateDashboard();
    	
    	drivetrain.update();
    	accumulator.update();
    	shooter.update();
    	breacher.update();
    	grabber.update();
    	
    	
    	
    	Logger.getLogger("Central").log(Level.INFO, "Left Encoder: " + input.getLeftDriveEncoder().getAngle());
    	Logger.getLogger("Central").log(Level.INFO, "Right Encoder: " + input.getRightDriveEncoder().getAngle());
   }

    @Override
    public void disabledInit() {
    	
    	try {
	    	Strongback.disable();
	    	drivetrain.disable();
	    	accumulator.disable();
	    	shooter.disable();
	    	breacher.setMacroState(MacroBreacherState.DISABLED);
	    	breacher.disable();
	    	grabber.disable();
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
    	Ports.GRABBER_EXTEND_VALVE = Ports.GRABBER_EXTEND_VALVE_TYR;
    	Ports.GRABBER_RETRACT_VALVE = Ports.GRABBER_RETRACT_VALVE_TYR;
    	Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE = Ports.SHOOTER_LOADING_ACTUATOR_EXTEND_VALVE_TYR;
    	Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE = Ports.SHOOTER_LOADING_ACTUATOR_RETRACT_VALVE_TYR;
    	Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE = Ports.SHOOTER_LOCKING_ACTUATOR_EXTEND_VALVE_TYR;
    	Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE  = Ports.SHOOTER_LOCKING_ACTUATOR_RETRACT_VALVE_TYR;
    	Ports.DRIVETRAIN_EXTEND_VALVE = Ports.DRIVETRAIN_EXTEND_VALVE_TYR;
    	Ports.DRIVETRAIN_RETRACT_VALVE = Ports.DRIVETRAIN_RETRACT_VALVE_TYR;
    	SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.BREACHER_POTENTIOMETER_DEGREE_OFFSET_TYR;
    	SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.BREACHER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_TYR;
    	SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET = SensorConstants.SHOOTER_POTENTIOMETER_DEGREE_OFFSET_TYR;
    	SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES = SensorConstants.SHOOTER_POTENTIOMETER_FULL_VOLTAGE_RANGE_TO_DEGREES_TYR;
    	SensorConstants.LEFT_DRIVETRAIN_DPP = SensorConstants.LEFT_DRIVETRAIN_DPP_TYR;
    	SensorConstants.RIGHT_DRIVETRAIN_DPP = SensorConstants.RIGHT_DRIVETRAIN_DPP_TYR;
    	DrivetrainConstants.DEGREE_TO_DISTANCE = DrivetrainConstants.TYR_DEGREE_TO_DISTANCE;
    	AccumulatorConstants.ACCUMULATOR_POWER = 1;
    }
    
    public void setDericConstants() {
    	RobotConstants.NAME = "Deric";	
    	Ports.LEFT_BACK_TALON_DEVICE_ID = Ports.LEFT_BACK_TALON_DEVICE_ID_DERIC;
    	Ports.LEFT_FRONT_TALON_DEVICE_ID = Ports.LEFT_FRONT_TALON_DEVICE_ID_DERIC;
    	Ports.RIGHT_BACK_TALON_DEVICE_ID = Ports.RIGHT_BACK_TALON_DEVICE_ID_DERIC;
    	Ports.RIGHT_FRONT_TALON_DEVICE_ID = Ports.RIGHT_FRONT_TALON_DEVICE_ID_DERIC;
    	Ports.SHOOTER_ARM_TALON_DEVICE_ID = Ports.SHOOTER_ARM_TALON_DEVICE_ID_DERIC;
    	Ports.BREACHER_TALON_DEVICE_ID = Ports.BREACHER_TALON_DEVICE_ID_DERIC;
      	Ports.GRABBER_EXTEND_VALVE = Ports.GEAR_ACTUATOR_EXTEND_VALVE_DERIC;
    	Ports.GRABBER_RETRACT_VALVE = Ports.GEAR_ACTUATOR_RETRACT_VALVE_DERIC;
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
    	DrivetrainConstants.DEGREE_TO_DISTANCE = DrivetrainConstants.DERIC_DEGREE_TO_DISTANCE;
    	AccumulatorConstants.ACCUMULATOR_POWER = 1;
    }
    	
    @Override
    public void disabledPeriodic() {
    	autoPath = robotTable.getString("autopath", "none");
       	switch(autoPath) {
       	case "lowbar":
       		behavior_manager = autonomousPaths.LOWBAR;
       		break;
       	case "waitlowbar":
       		behavior_manager = autonomousPaths.WAITLOWBAR;
       		break;
       	case "bd":
       		behavior_manager = autonomousPaths.BD;
       		break;
       	case "waitbd":
       		behavior_manager = autonomousPaths.WAITBD;
       		break;
       	case "backtouch":
       		behavior_manager = autonomousPaths.BACKTOUCH;
       		break;
       	case "waitbacktouch":
       		behavior_manager = autonomousPaths.WAITBACKTOUCH;
       		break;
       	case "20pt":
       		behavior_manager = autonomousPaths.TWENTYPT;
       		break;
       	case "moat20pt":
       		behavior_manager = autonomousPaths.MOAT20PT;
       		break;
       	case "none":
       		behavior_manager = autonomousPaths.NONE;
       		break;
       	default:
       		behavior_manager = autonomousPaths.WRONG;
       		break;
       	}
       	
    	dashboard.updateDashboard();
    }
}