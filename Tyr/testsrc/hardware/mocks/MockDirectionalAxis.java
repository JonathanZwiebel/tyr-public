package hardware.mocks;

import org.strongback.components.ui.DirectionalAxis;

public class MockDirectionalAxis implements DirectionalAxis {
	private volatile int x = 0;
	
	public MockDirectionalAxis(int x) {
		this.x = x;
	}
	
	@Override
	public int getDirection() {
		return x;
	}
	
	public void set(int x) {
		this.x = x;
	}
}