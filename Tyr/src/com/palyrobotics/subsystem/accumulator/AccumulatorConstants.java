package com.palyrobotics.subsystem.accumulator;

public class AccumulatorConstants {
	private static final Double DOUBLE = (Double) null;
	private static final Integer INTEGER = (Integer) null;

	//Speed at which the accumulator is run
	public static final double ACCUMULATOR_POWER = DOUBLE;
	
	//Buttons for intaking and expelling balls
<<<<<<< Upstream, based on yellow-master
	public static final int ACCUMULATE_BUTTON  = INTEGER;
	public static final int EXPEL_BUTTON = INTEGER;
	public static final int STOP_BUTTON = INTEGER;
	
	//TODO: Period of time the accumulator will keep expelling
	public static final double EXPEL_TIME = DOUBLE;
=======
	public static final int ACCUMULATE_BUTTON  = 1;
	public static final int EXPEL_BUTTON = 2;
	public static final double EXPEL_TIME = 3000;
	public static final int STOP_BUTTON = 3;
>>>>>>> 8076b8a Fixed accumulator formatting
}