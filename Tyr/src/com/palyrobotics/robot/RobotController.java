package com.palyrobotics.robot;

import org.strongback.Strongback;

import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainHardware;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterHardware;
import com.palyrobotics.subsystem.shooter.ShooterSystems;

import edu.wpi.first.wpilibj.IterativeRobot;


public class RobotController extends IterativeRobot {
	private DrivetrainController drivetrain;
	private AccumulatorController accumulator;
	private ShooterController shooter;
	private ShooterSystems shooter_systems;
	private DrivetrainSystems drivetrainSystems;
	private InputSystems input;
	
    @Override
    public void robotInit() {
    	input = new InputHardware(); // when this is mock it will 
    	shooter_systems = new ShooterHardware(); // when this is mock it will not be shooter hardware

    	try {
    		Strongback.start();
    	}
    	catch(Throwable thrown) {
    		System.out.println("Strongback failed");
    	}
    	
    	input = new InputHardware(); // when this is mock it will 
    	shooter_systems = new ShooterHardware(); // when this is mock it will not be shooter hardware
    	drivetrainSystems = new DrivetrainHardware();
    	drivetrain = new DrivetrainController(drivetrainSystems, input);
    	accumulator = new AccumulatorController();
    	shooter = new ShooterController(shooter_systems, input);
    }

    @Override
    public void teleopInit() {
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
    }
}
