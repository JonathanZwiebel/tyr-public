package com.palyrobotics.test.simulations;

import com.palyrobotics.robot.RobotController;

public abstract class Simulation {
	
	private RobotController robot;
	
	public void setRobot(RobotController robot) {
		this.robot = robot;
	}
	
	public abstract void init();
	public abstract void update();
	
	public abstract void testInit();
	public abstract void testUpdate();
	
	public abstract double getTotalTime();
}
