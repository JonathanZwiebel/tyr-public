package com.palyrobotics.robot;

import org.strongback.Strongback;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;

import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.shooter.ShooterController;

import edu.wpi.first.wpilibj.IterativeRobot;

import static com.palyrobotics.robot.Ports.*;

public class RobotController extends IterativeRobot implements Tyr {
	
	public FlightStick driveStick;
	public FlightStick turnStick;
	public FlightStick operatorStick;
	
	private DrivetrainController drivetrain;
	private AccumulatorController accumulator;
	private ShooterController shooter;
	
    @Override
    public void robotInit() {
    	drivetrain = new DrivetrainController();
    	accumulator = new AccumulatorController();
    	shooter = new ShooterController();
    	
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
