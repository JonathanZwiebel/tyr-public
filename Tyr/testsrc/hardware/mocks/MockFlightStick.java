package hardware.mocks;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.components.ui.FlightStick;
import org.strongback.mock.Mock;
import org.strongback.mock.MockSwitch;

public class MockFlightStick implements FlightStick {
	private MockSwitch thumb = Mock.notTriggeredSwitch();
	private MockSwitch button = Mock.notTriggeredSwitch();
	private MockSwitch trigger = Mock.notTriggeredSwitch();
	private MockDirectionalAxis dPad;
	private MockContinuousRange axis;
	private MockContinuousRange pitch;
	private MockContinuousRange roll;
	private MockContinuousRange throttle;
	private MockContinuousRange yaw;
	
	public MockFlightStick(int indPad, double inaxis, double inpitch, double inroll, 
			double inthrottle, double inyaw) {
		dPad = new MockDirectionalAxis(indPad);
		axis = new MockContinuousRange(inaxis);
		pitch = new MockContinuousRange(inpitch);
		roll = new MockContinuousRange(inroll);
		throttle = new MockContinuousRange(inthrottle);
		yaw = new MockContinuousRange(inyaw);
	}
	@Override
	public ContinuousRange getAxis(int arg0) {
		return axis;
	}

	@Override
	public Switch getButton(int arg0) {
		return button;
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
	public Switch getThumb() {
		return thumb;
	}

	@Override
	public Switch getTrigger() {
		return trigger;
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
}