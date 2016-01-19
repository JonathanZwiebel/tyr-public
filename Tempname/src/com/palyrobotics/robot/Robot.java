package com.palyrobotics.robot;

import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import com.palyrobotics.subsystem.drivetrain.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
	private Drivetrain drivetrain = new Drivetrain();
	
    @Override
    public void robotInit() {
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.start();
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}
