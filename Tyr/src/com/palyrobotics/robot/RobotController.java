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
import com.palyrobotics.subsystem.breacher.BreacherController.Macro;
import com.palyrobotics.subsystem.breacher.BreacherController.Micro;
import com.palyrobotics.subsystem.breacher.BreacherHardware;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainHardware;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterHardware;
import com.palyrobotics.subsystem.shooter.ShooterSystems;

import edu.wpi.first.wpilibj.IterativeRobot;

public class RobotController extends IterativeRobot {
	private DrivetrainController drivetrain;
	private DrivetrainSystems drivetrainSystems;

	private AccumulatorController accumulator;
	private AccumulatorSystems accumulatorSystems;

	private BreacherController breacher;
	private BreacherSystems breacherSystems;
	
	private ShooterController shooter;
	private ShooterSystems shooterSystems;

	private InputSystems input;
	
    @Override
    public void robotInit() {
    	try {
        	Strongback.start();
        	Strongback.configure().initialize();
        }
        catch(Throwable thrown) {
        	System.err.println(thrown);
        }

    	// Hardware system
    	input = new InputHardware(); 
    	accumulatorSystems = new AccumulatorHardware();
    	shooterSystems = new ShooterHardware();
    	drivetrainSystems = new DrivetrainHardware();
    	breacherSystems = new BreacherHardware();
    	
    	//Subsystem controllers
    	drivetrain = new DrivetrainController(drivetrainSystems, input);
    	accumulator = new AccumulatorController(accumulatorSystems, input);
    	shooter = new ShooterController(shooterSystems, input);
    	breacher = new BreacherController(breacherSystems, input);
    	
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

    @Override
    public void autonomousInit() {
    	drivetrain.init();
    	accumulator.init();
    	shooter.init();
    	breacher.init();
    	
    	breacher.setMacroState(Macro.AUTO);
    	breacher.setMicroState(Micro.IDLE);
    }
    
    @Override
    public void teleopInit() {
    	breacher.setMacroState(Macro.TELEOP);
    	breacher.setMicroState(Micro.IDLE);
    }

    @Override
    public void teleopPeriodic() {
    	drivetrain.update();
    	accumulator.update();
    	shooter.update();
    	breacher.update();
    }

    @Override
    public void disabledInit() {
    	drivetrain.disable();
    	accumulator.disable();
    	shooter.disable();
    	breacher.disable();
    	
    	breacher.setMacroState(Macro.DISABLED);
    	
        Strongback.disable();
        Logger.getLogger("Central").log(Level.INFO, "The RobotController was disabled.");
    }
}
