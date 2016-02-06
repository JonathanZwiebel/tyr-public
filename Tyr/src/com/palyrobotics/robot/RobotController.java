package com.palyrobotics.robot;

import org.strongback.Strongback;

import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorHardware;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.shooter.ShooterHardware;
import com.palyrobotics.subsystem.shooter.ShooterSystems;
import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

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
