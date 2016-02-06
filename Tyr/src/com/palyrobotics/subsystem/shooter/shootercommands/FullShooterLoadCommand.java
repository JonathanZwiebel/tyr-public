package com.palyrobotics.subsystem.shooter.shootercommands;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.palyrobotics.subsystem.shooter.shootercontrollers.ShooterController;

/**
 * @author Paly Robotics Programming Red Module
 * 
 * Loads the arm when the ball is in the accumulator to shoot
 */
public class FullShooterLoadCommand extends Command {
	public ShooterController controller;
	
	public FullShooterLoadCommand(ShooterController controller) {
		super(controller);
		this.controller = controller;
	}
	
	@Override
	public boolean execute() {
		if (!controller.lockingActuatorController.isLocked()) {
			CommandGroup retractThenLatch = CommandGroup.runSequentially(new ShooterLoadingActuatorRetractCommand(controller),new ShooterLockingActuatorLockCommand(controller),new ShooterLoadingActuatorExtendCommand(controller)); 
			Strongback.submit(retractThenLatch);
			return true;
		} else {
			Strongback.submit(new ShooterLockingActuatorLockCommand(controller));
			return false;
		}

	}
}
