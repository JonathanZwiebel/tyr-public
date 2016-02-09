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
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterHardware;
import com.palyrobotics.subsystem.shooter.ShooterSystems;

import edu.wpi.first.wpilibj.IterativeRobot;


public class RobotController extends IterativeRobot {
	private DrivetrainController drivetrain;
	
	private AccumulatorController accumulator;
	private AccumulatorSystems accumulatorSystems;
	
	private ShooterController shooter;
	private ShooterSystems shooterSystems;
	private InputSystems input;
	
    @Override
    public void robotInit() {
    	input = new InputHardware(); // when this is mock it will
    	//Hardware systems
    	accumulatorSystems = new AccumulatorHardware();
    	shooterSystems = new ShooterHardware(); // when this is mock it will not be shooter hardware
    	
    	//Subsystem controllers
    	drivetrain = new DrivetrainController();
    	accumulator = new AccumulatorController(accumulatorSystems, input);
    	shooter = new ShooterController(shooterSystems, input);
    	
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
    public void teleopInit() {
        Strongback.start();
    	drivetrain.init();
    	accumulator.init();
    	shooter.init();
    }

    @Override
    public void teleopPeriodic() {
    	drivetrain.update();
    	accumulator.update();
    	shooter.update();
    }

    @Override
    public void disabledInit() {
    	drivetrain.disable();
    	accumulator.disable();
    	shooter.disable();
        Strongback.disable();
    	Logger.getLogger("Central").log(Level.INFO, "The RobotController was disabled.");
    }
}
