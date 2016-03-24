package hardware.mocks;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.mock.MockSwitch;

import com.palyrobotics.xbox.XBoxController;

public class MockXBoxController implements XBoxController {
	Switch button = new MockSwitch();
	Switch leftTriggerSwitch = new MockSwitch();
	Switch rightTriggerSwitch = new MockSwitch();
	Switch a = new MockSwitch();
	Switch b = new MockSwitch();
	Switch x = new MockSwitch();
	Switch y = new MockSwitch();
	Switch leftBumper = new MockSwitch();
	Switch rightBumper = new MockSwitch();
	Switch leftStickPressed= new MockSwitch();
	Switch rightStickPressed = new MockSwitch();
	ContinuousRange axis;
	DirectionalAxis dPad;
	ContinuousRange leftTrigger;
	ContinuousRange rightTrigger;
	ContinuousRange leftX;
	ContinuousRange rightX;
	ContinuousRange leftY;
	ContinuousRange rightY;
	
	public MockXBoxController(int inaxis, int inpad, int inlefttrigger,
			int inrighttrigger, int inleftx, int inlefty, int inrightx, int inrighty) {
		axis = new MockContinuousRange(inaxis);
		dPad = new MockDirectionalAxis(inpad);
		leftTrigger = new MockContinuousRange(inlefttrigger);
		rightTrigger = new MockContinuousRange(inrighttrigger);
		leftX= new MockContinuousRange(inleftx);
		rightX = new MockContinuousRange(inrightx);
		leftY = new MockContinuousRange(inlefty);
		rightY = new MockContinuousRange(inrighty);
	}
	
	@Override
	public ContinuousRange getAxis(int arg0) {
		return axis;
	}
	
	public void setAxis(int a) {
		((MockContinuousRange) axis).write(a);
	}

	@Override
	public Switch getButton(int arg0) {
		return button;
	}

	@Override
	public DirectionalAxis getDPad(int arg0) {
		return dPad;
	}
	
	public void setDPad(int a) {
		((MockDirectionalAxis) dPad).set(a);
	}

	@Override
	public ContinuousRange getLeftTrigger() {
		return leftTrigger;
	}
	
	public void setLeftTrigger(int a) {
		((MockDirectionalAxis) leftTrigger).set(a);
	}

	@Override
	public ContinuousRange getRightTrigger() {
		return rightTrigger;
	}
	
	public void setRightTrigger(int a) {
		((MockDirectionalAxis) rightTrigger).set(a);
	}

	@Override
	public Switch getLeftTriggerSwitch() {
		return leftTriggerSwitch;
	}

	@Override
	public Switch getRightTriggerSwitch() {
		return rightTriggerSwitch;
	}

	@Override
	public Switch getLeftBumper() {
		return leftBumper;
	}

	@Override
	public Switch getRightBumper() {
		return rightBumper;
	}

	@Override
	public Switch getA() {
		return a;
	}

	@Override
	public Switch getB() {
		return b;
	}

	@Override
	public Switch getX() {
		return x;
	}

	@Override
	public Switch getY() {
		return y;
	}

	@Override
	public Switch getLeftStickPressed() {
		return leftStickPressed;
	}

	@Override
	public Switch getRightStickPressed() {
		return rightStickPressed;
	}

	@Override
	public ContinuousRange getLeftX() {
		return leftX;
	}

	@Override
	public ContinuousRange getLeftY() {
		return leftY;
	}

	@Override
	public ContinuousRange getRightX() {
		return rightX;
	}

	@Override
	public ContinuousRange getRightY() {
		return rightY;
	}
	
}