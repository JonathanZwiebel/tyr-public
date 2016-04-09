package com.palyrobotics.subsystem.accumulator;

import org.strongback.command.Command;

import com.palyrobotics.robot.Buttons;
import com.palyrobotics.robot.InputSystems;

import static com.palyrobotics.subsystem.accumulator.AccumulatorConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command that sets up switch reactors to submit commands during teleop
 * Should be called once at the beginning of teleop
 */
public class AccumulatorTeleop extends Command {
	AccumulatorController controller;
	InputSystems input;
	
	/**
	 * Constructor for AccumulatorTeleop
	 * @param controler the AccumulatorController object
	 * @param input the InputSystems object
	 */
	public AccumulatorTeleop (AccumulatorController controller, InputSystems input) {
		//Constructs the command using the super constructor
		super (controller);
		this.controller = controller;
		this.input = input;
	}
	
	@Override
	public void initialize() {
    	Logger.getLogger("Central").log(Level.INFO, "AccumulatorTeleop initalized.");
	}
	
	/**
	 * Execute method for the AccumulatorTeleop command
	 * Sets up switch reactors for teleop
	 * Completes imediately
	 * @see org.strongback.command.Command#execute()
	 */
	@Override
	public boolean execute() {
		if(input.getSecondaryStick().getButton(Buttons.ACCUMULATOR_INTAKE_BUTTON).isTriggered()) {
			controller.systems.getAccumulatorMotors().setSpeed(-ACCUMULATOR_POWER);
		}
		else if(input.getSecondaryStick().getButton(Buttons.ACCUMULATOR_EXPEL_BUTTON).isTriggered()){
			controller.systems.getAccumulatorMotors().setSpeed(ACCUMULATOR_POWER);
		}
		else {
			controller.systems.getAccumulatorMotors().setSpeed(0.0);
		}
    	Logger.getLogger("Central").log(Level.INFO, "AccumulatorTeleop is ending.");
		return false;
	}
	
	@Override
	public void end() {
    	Logger.getLogger("Central").log(Level.INFO, "AccumulatorTeleop ended.");
	}

	@Override
	public void interrupted() {
    	Logger.getLogger("Central").log(Level.INFO, "AccumulatorTeleop interrupted.");
	}
}