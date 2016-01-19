package com.palyrobotics.robot;

import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import com.palyrobotics.subsystem.drivetrain.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private Motor leftFrontMotor = Hardware.Motors.talon(0);
	private Motor leftBackMotor = Hardware.Motors.talon(1);
	
	private Motor rightFrontMotor = Hardware.Motors.talon(2);
	private Motor rightBackMotor = Hardware.Motors.talon(3);
	
	private Motor left = Motor.compose(leftFrontMotor, leftBackMotor);
	private Motor right = Motor.compose(rightFrontMotor, rightBackMotor);
	
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
