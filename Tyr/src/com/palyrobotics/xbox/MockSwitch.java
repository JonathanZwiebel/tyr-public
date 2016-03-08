package com.palyrobotics.xbox;

import org.strongback.components.Switch;

public class MockSwitch implements Switch {

	private boolean triggered;
	
	public MockSwitch() {
		triggered = false;
	}
	
	@Override
	public boolean isTriggered() {
		return triggered;
	}
	
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

}