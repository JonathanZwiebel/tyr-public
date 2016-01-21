package com.palyrobotics.robot;

import org.strongback.Strongback;

import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.shooter.ShooterController;

import edu.wpi.first.wpilibj.IterativeRobot;


public class RobotController extends IterativeRobot {
	private DrivetrainController drivetrain;
	private AccumulatorController accumulator;
	private ShooterController shooter;
	
    @Override
    public void robotInit() {
    	drivetrain = new DrivetrainController();
    	accumulator = new AccumulatorController();
    	shooter = new ShooterController();
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
    }
}
