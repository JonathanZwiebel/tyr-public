package com.palyrobotics.robot;


import org.strongback.Strongback;

import com.palyrobotics.subsystem.accumulator.AccumulatorController;
import com.palyrobotics.subsystem.accumulator.AccumulatorHardware;
import com.palyrobotics.subsystem.accumulator.AccumulatorSystems;
import com.palyrobotics.subsystem.breacher.BreacherController;
import com.palyrobotics.subsystem.breacher.BreacherController.MacroBreacherState;
import com.palyrobotics.subsystem.breacher.BreacherController.MicroBreacherState;
import com.palyrobotics.subsystem.breacher.BreacherHardware;
import com.palyrobotics.subsystem.breacher.BreacherSystems;
import com.palyrobotics.subsystem.drivetrain.DrivetrainController;
import com.palyrobotics.subsystem.drivetrain.DrivetrainHardware;
import com.palyrobotics.subsystem.drivetrain.DrivetrainSystems;
import com.palyrobotics.subsystem.shooter.ShooterController;
import com.palyrobotics.subsystem.shooter.ShooterHardware;
import com.palyrobotics.subsystem.shooter.ShooterSystems;
import com.palyrobotics.subsystem.grabber.GrabberController;
import com.palyrobotics.subsystem.grabber.GrabberHardware;
import com.palyrobotics.subsystem.grabber.GrabberSystems;

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

	private GrabberController grabber;
	private GrabberSystems grabberSystems;
	
	private InputSystems input;
	
    @Override
    public void robotInit() {
    	try {
        	Strongback.configure().recordNoData().recordNoEvents().initialize();
        	Strongback.start();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }

    	// Hardware system
    	input = new InputHardware(); 
    	accumulatorSystems = new AccumulatorHardware();
    	shooterSystems = new ShooterHardware();
    	drivetrainSystems = new DrivetrainHardware();
    	breacherSystems = new BreacherHardware();
    	grabberSystems = new GrabberHardware();
    	
    	//Subsystem controllers
    	drivetrain = new DrivetrainController(drivetrainSystems, input);
    	accumulator = new AccumulatorController(accumulatorSystems, input);
    	shooter = new ShooterController(shooterSystems, input);
    	breacher = new BreacherController(breacherSystems, input);
    	grabber = new GrabberController(grabberSystems, input);
    }
    @Override
    public void autonomousInit() {
    	drivetrain.init();
    	accumulator.init();
    	shooter.init();
    	breacher.init();
    	grabber.init();
    	
    	breacher.setMacroState(MacroBreacherState.AUTO);
    	breacher.setMicroState(MicroBreacherState.IDLE);
    }
    
    @Override
    public void teleopInit() {
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
    	drivetrain.update();
    	accumulator.update();
    	shooter.update();
    	breacher.update();
    	grabber.update();
    	
    	System.out.println("Left Encoder: " + input.getLeftDriveEncoder().getAngle());
    	System.out.println("Right Encoder: " + input.getRightDriveEncoder().getAngle());

    	System.out.println("Breacher Potentiometer: " + input.getBreacherPotentiometer().getAngle());	
    	System.out.println("Shooter Potentiometer: " + input.getShooterArmPotentiometer().getAngle());
    	
    	System.out.println("Gyroscope: " + input.getGyroscope().getAngle());
    	System.out.println("Accelerometer X: " + input.getAccelerometer().getXDirection().getAcceleration());
    	System.out.println("Accelerometer Y: " + input.getAccelerometer().getYDirection().getAcceleration());
    	System.out.println("Accelerometer Z: " + input.getAccelerometer().getZDirection().getAcceleration());    	
    }

    @Override
    public void disabledInit() {
    	drivetrain.disable();
    	accumulator.disable();
    	shooter.disable();
    	breacher.disable();
    	grabber.disable();
    	
    	breacher.setMacroState(MacroBreacherState.DISABLED);
    	try {
    	Strongback.disable();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
 