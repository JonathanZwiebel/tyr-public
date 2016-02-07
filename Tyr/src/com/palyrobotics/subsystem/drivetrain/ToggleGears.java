package com.palyrobotics.subsystem.drivetrain;

import org.strongback.command.Command;

import com.palyrobotics.subsystem.drivetrain.DrivetrainController.GearState;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ToggleGears extends Command {

	private DrivetrainController drivetrain;
	private GearState gearState;
	
	
	/**
	 * Initializes command with DrivetrainController reference, as well as 
	 * what the current state of the gear shift is. 
	 * @param drivetrain
	 * @param gearState
	 */
	public ToggleGears(DrivetrainController drivetrain, GearState gearState) {
		this.drivetrain = drivetrain;
		this.gearState = gearState;
	}
	
	@Override
	public void initialize() {
		
	}
	
	/**
	 * Basic gear shifting function for a toggle on/off gearshift
	 * control system. 
	 * @return
	 */
	@Override
	public boolean execute() {
		if(gearState == GearState.LOWERING_GEAR){
			drivetrain.output.getSolenoid().set(DoubleSolenoid.Value.kForward);
			drivetrain.gearState = GearState.RAISING_GEAR;
		} else {
			drivetrain.output.getSolenoid().set(DoubleSolenoid.Value.kReverse);
			drivetrain.gearState = GearState.LOWERING_GEAR;
		}
		return true;
	}
	
	@Override
	public void interrupted() {
		
	}
	
	@Override
	public void end() {
		
	}

}
