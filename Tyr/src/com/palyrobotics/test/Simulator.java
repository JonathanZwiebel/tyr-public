package com.palyrobotics.test;

import com.palyrobotics.test.SimulatorConstants;
import com.palyrobotics.test.simulations.Simulation;

public class Simulator {
	
	public static void main(String args[]) {
		
	}
	
	private Simulation simulation;

	public Simulator(Simulation simulation) {
		this.simulation = simulation;
	}
	
	// TODO: Implement the actual WPI state machine at http://www.frc.ri.cmu.edu/project/frcsoftware/wordpress/?p=32
	public void simulate(Simulation simulation) {		
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