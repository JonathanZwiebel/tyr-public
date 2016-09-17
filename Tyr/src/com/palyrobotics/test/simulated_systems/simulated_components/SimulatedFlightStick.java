package com.palyrobotics.test.simulated_systems.simulated_components;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.components.ui.FlightStick;

// TODO: Implement system for reading inputs
public class SimulatedFlightStick implements FlightStick {
	
	@Override
	public ContinuousRange getAxis(int axis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Switch getButton(int button) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirectionalAxis getDPad(int pad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContinuousRange getPitch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContinuousRange getYaw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContinuousRange getRoll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContinuousRange getThrottle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Switch getTrigger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Switch getThumb() {
		// TODO Auto-generated method stub
		return null;
	}
	
}