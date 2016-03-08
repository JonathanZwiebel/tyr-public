package com.palyrobotics.xbox;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.components.ui.FlightStick;

public class MockFlightStick implements FlightStick {
	private MockDirectionalAxis dPad;
	private MockContinuousRange axis;
	private MockContinuousRange pitch;
	private MockContinuousRange roll;
	private MockContinuousRange throttle;
	private MockContinuousRange yaw;
	
	private MockSwitch button1;
	private MockSwitch button2;
	private MockSwitch button3;
	private MockSwitch button4;
	private MockSwitch button5;
	private MockSwitch button6;
	private MockSwitch button7;
	private MockSwitch button8;
	private MockSwitch button9;
	private MockSwitch button10;
	private MockSwitch button11;
	private MockSwitch button12;
	
	public MockFlightStick(int indPad, double inaxis, double inpitch, double inroll, 
			double inthrottle, double inyaw) {
		dPad = new MockDirectionalAxis(indPad);
		axis = new MockContinuousRange(inaxis);
		pitch = new MockContinuousRange(inpitch);
		roll = new MockContinuousRange(inroll);
		throttle = new MockContinuousRange(inthrottle);
		yaw = new MockContinuousRange(inyaw);
		
		button1 = new MockSwitch();
		button2 = new MockSwitch();
		button3 = new MockSwitch();
		button4 = new MockSwitch();
		button5 = new MockSwitch();
		button6 = new MockSwitch();
		button7 = new MockSwitch();
		button8 = new MockSwitch();
		button9 = new MockSwitch();
		button10 = new MockSwitch();
		button11 = new MockSwitch();
		button12 = new MockSwitch();

	}
	
	@Override
	public ContinuousRange getAxis(int arg0) {
		return axis;
	}

	@Override
	public DirectionalAxis getDPad(int arg0) {
		return dPad;
	}

	@Override
	public ContinuousRange getPitch() {
		return pitch;
	}

	@Override
	public ContinuousRange getRoll() {
		return roll;
	}

	@Override
	public ContinuousRange getThrottle() {
		return throttle;
	}
		
	@Override
	public ContinuousRange getYaw() {
		return yaw;
	}
	
	public void setAxis(double a) {
		axis.write(a);
	}

	public void setDPad(int a) {
		dPad.set(a);
	}

	public void setPitch(double a) {
		pitch.write(a);
	}

	public void setRoll(double a) {
		roll.write(a);
	}

	public void setThrottle(double a) {
		throttle.write(a);
	}

	public void setYaw(double a) {
		yaw.write(a);
	}
	
	public void setButton(int buttonNumber, boolean triggered) {
		try{
		if(buttonNumber == 1) {
			button1.setTriggered(triggered);
		}
		
		if(buttonNumber == 2) {
			button2.setTriggered(triggered);
		}
		
		if(buttonNumber == 3) {
			button3.setTriggered(triggered);
		}
		
		if(buttonNumber == 4) {
			button4.setTriggered(triggered);
		}
		
		if(buttonNumber == 5) {
			button5.setTriggered(triggered);
		}
		
		if(buttonNumber == 6) {
			button6.setTriggered(triggered);
		}
		
		if(buttonNumber == 7) {
			button7.setTriggered(triggered);
		}
		
		if(buttonNumber == 8) {
			button8.setTriggered(triggered);
		}
		
		if(buttonNumber == 9) {
			button9.setTriggered(triggered);
		}
		
		if(buttonNumber == 10) {
			button10.setTriggered(triggered);
		}
		
		if(buttonNumber == 11) {
			button11.setTriggered(triggered);
		}
		
		if(buttonNumber == 12) {
			button12.setTriggered(triggered);
		}
		}
		catch(Exception e) {
			//System.out.println(buttonNumber);
		}
	}
	
	public void setTrigger(boolean triggered) {
		button1.setTriggered(triggered);
	}
	
	@Override
	public Switch getButton(int buttonNumber) {
		
		MockSwitch button = new MockSwitch();
		
		if(buttonNumber == 1) {
			button = button1;
		}
		
		if(buttonNumber == 2) {
			button = button2;
		}
		
		if(buttonNumber == 3) {
			button = button3;
		}
		
		if(buttonNumber == 4) {
			button = button4;
		}
		
		if(buttonNumber == 5) {
			button = button5;
		}
		
		if(buttonNumber == 6) {
			button = button6;
		}
		
		if(buttonNumber == 7) {
			button = button7;
		}
		
		if(buttonNumber == 8) {
			button = button8;
		}
		
		if(buttonNumber == 9) {
			button = button9;
		}
		
		if(buttonNumber == 10) {
			button = button10;
		}
		
		if(buttonNumber == 11) {
			button = button11;
		}
		
		if(buttonNumber == 12) {
			button = button12;
		}
		
		return button;
	}
	
	@Override
	public Switch getTrigger() {
		return button1;
	}
	
	@Override
	public Switch getThumb() {
		return button2;
	}
}