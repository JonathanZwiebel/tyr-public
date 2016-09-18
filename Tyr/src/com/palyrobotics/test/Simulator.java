package com.palyrobotics.test;

import com.palyrobotics.robot.RobotController;
import com.palyrobotics.test.SimulatorConstants;
import com.palyrobotics.test.simulations.Simulation;

public class Simulator {
	
	private RobotController robot;
	
	// TODO: Implement system for inputs
	public Simulator() {
		this.robot = new RobotController();
	}
	
	// TODO: Implement the actual WPI state machine at http://www.frc.ri.cmu.edu/project/frcsoftware/wordpress/?p=32
	public void simulate(Simulation simulation) {
		// TODO: Come up with a more elegant way to do this
		simulation.setRobot(robot);
		
		int updates = 0;
		double time = 0.0;
		
		simulation.init();
		simulation.testInit();
		
		while(time < simulation.getTotalTime()) {
			updates ++;
			time += 1.0 / SimulatorConstants.UPDATES_PER_SECOND;
			
			simulation.update();
			simulation.testUpdate();
		}
	}
}