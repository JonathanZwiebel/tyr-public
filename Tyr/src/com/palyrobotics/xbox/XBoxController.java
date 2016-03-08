package com.palyrobotics.xbox;

import java.util.function.IntToDoubleFunction;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.components.ui.InputDevice;
import org.strongback.function.IntToBooleanFunction;
import org.strongback.function.IntToIntFunction;

public interface XBoxController extends InputDevice {
	ContinuousRange getLeftTrigger();

	ContinuousRange getRightTrigger();
	
	Switch getLeftTriggerSwitch();
	
	Switch getRightTriggerSwitch();
	
	Switch getLeftBumper();
	
	Switch getRightBumper();
	
	Switch getA();
	
	Switch getB();
	
	Switch getX();
	
	Switch getY();

	ContinuousRange getLeftX();

	ContinuousRange getLeftY();

	ContinuousRange getRightX();

	ContinuousRange getRightY();

	//Overloaded method for creating an XBox. This differs from the other method in that the triggers are ContinuousRanges instead of Switches.
	public static XBoxController create(IntToDoubleFunction axisToValue, IntToBooleanFunction buttonNumberToSwitch,
            IntToIntFunction padToValue, ContinuousRange leftX, ContinuousRange leftY, ContinuousRange rightX,
            ContinuousRange rightY, ContinuousRange leftTrigger, ContinuousRange rightTrigger, Switch leftBumper, Switch rightBumper, Switch a, Switch b, Switch x, Switch y) {
        return new XBoxController() {
            @Override
            public ContinuousRange getAxis(int axis) {
                return () -> axisToValue.applyAsDouble(axis);
            }

            @Override
            public Switch getButton(int button) {
                return () -> buttonNumberToSwitch.applyAsBoolean(button);
            }

            @Override
            public DirectionalAxis getDPad(int pad) {
                return () -> padToValue.applyAsInt(pad);
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

            @Override
            public ContinuousRange getLeftTrigger() {
                return leftTrigger;
            }

            @Override
            public ContinuousRange getRightTrigger() {
                return rightTrigger;
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
			//Returns null as this method does not use Switches as triggers
			public Switch getLeftTriggerSwitch() {
				return null;
			}

			@Override
			//Returns null as this method does not use Switches as triggers
			public Switch getRightTriggerSwitch() {
				return null;
			}
            
        };
	}
	
	//Overloaded method for creating an XBox. This differs from the other method in that the triggers are Switches instead of ContinuousRanges.
	public static XBoxController create(IntToDoubleFunction axisToValue, IntToBooleanFunction buttonNumberToSwitch,
            IntToIntFunction padToValue, ContinuousRange leftX, ContinuousRange leftY, ContinuousRange rightX,
            ContinuousRange rightY, Switch leftTrigger, Switch rightTrigger, Switch leftBumper, Switch rightBumper, Switch a, Switch b, Switch x, Switch y) {
        return new XBoxController() {
            @Override
            public ContinuousRange getAxis(int axis) {
                return () -> axisToValue.applyAsDouble(axis);
            }

            @Override
            public Switch getButton(int button) {
                return () -> buttonNumberToSwitch.applyAsBoolean(button);
            }

            @Override
            public DirectionalAxis getDPad(int pad) {
                return () -> padToValue.applyAsInt(pad);
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

            @Override
          //Returns null as this method does not use ContinuousRanges as triggers
            public ContinuousRange getLeftTrigger() {
                return null;
            }

            @Override
          //Returns null as this method does not use ContinuousRanges as triggers
            public ContinuousRange getRightTrigger() {
                return null;
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
			public Switch getLeftTriggerSwitch() {
				return leftTrigger;
			}

			@Override
			public Switch getRightTriggerSwitch() {
				return rightTrigger;
			}
            
        };
	}
}