package com.palyrobotics.xbox;

import org.strongback.components.ui.ContinuousRange;

public class MockContinuousRange implements ContinuousRange {
	private volatile double x = 0;
	
	public MockContinuousRange(double x) {
		this.x = x;
	}
	@Override
	public double read() {
		return x;
	}
	
	public void write(double d) {
		this.x = d;
	}
}