package com.palyrobotics.robot;

import org.strongback.Strongback;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;

import com.palyrobotics.subsystem.accumulator.Accumulator;
import com.palyrobotics.subsystem.drivetrain.Drivetrain;
import com.palyrobotics.subsystem.shooter.Shooter;

import edu.wpi.first.wpilibj.IterativeRobot;

import static com.palyrobotics.robot.Ports.*;

public class Robot extends IterativeRobot {
	
	public FlightStick driveStick;
	public FlightStick turnStick;
	public FlightStick operatorStick;
	
	private Drivetrain drivetrain;
	private Accumulator accumulator;
	private Shooter shooter;
	
    @Override
    public void robotInit() {
    	drivetrain = new Drivetrain();
    	accumulator = new Accumulator();
    	shooter = new Shooter();
    	
    	driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(DRIVE_STICK_PORT);
    	turnStick = Hardware.HumanInterfaceDevices.logitechAttack3D(TURN_STICK_PORT);
    	operatorStick = Hardware.HumanInterfaceDevices.logitechAttack3D(OPERATOR_STICK_PORT);
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
