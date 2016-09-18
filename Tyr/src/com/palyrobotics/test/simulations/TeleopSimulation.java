package com.palyrobotics.test.simulations;

import com.palyrobotics.robot.RobotController;
import com.palyrobotics.test.SimulatorConstants;

public class TeleopSimulation extends Simulation {
	
	private RobotController robot;
	
	public void setRobot(RobotController robot) {
		this.robot = robot;
	}

	@Override
	public void init() {
		this.robot.teleopInit();
	}

	@Override
	public void update() {
		this.robot.teleopPeriodic();
	}

	@Override
	public void testInit() {
		// TODO: Write test code to ensure proper initialization here
	}

	@Override
	public void testUpdate() {
		// TODO: Write test code to ensure proper updating here
	}

	@Override
	public double getTotalTime() {
		return SimulatorConstants.TELEOP_TIME;
	}	
}
