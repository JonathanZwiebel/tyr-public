package shootertests;

import static org.junit.Assert.*; 

import org.junit.*;
import com.palyrobotics.subsystem.shooter.ShooterConstants;

public class TestShooterConstants {
	
	@Test
	public void testArmAnglesOrdered() {
		assertTrue(ShooterConstants.MAX_ARM_ANGLE > ShooterConstants.MIN_ARM_ANGLE);
	}
	
	@Test
	public void testMaxArmAngleReasonable() {
		assertTrue(ShooterConstants.MAX_ARM_ANGLE < 180.0f);
	}
	
	@Test
	public void testMinArmAngleReasonable() {
		assertTrue(ShooterConstants.MIN_ARM_ANGLE > -180.0f);
	}
	
	@Test
	public void testArmProportionalMEReasonable() {
		assertTrue(ShooterConstants.ARM_PROPORTIONAL_ME > 0.0f);
		assertTrue(ShooterConstants.ARM_PROPORTIONAL_ME < 100.0f);
	}
	
	@Test
	public void testArmDerivativeMEReasonable() {
		assertTrue(ShooterConstants.ARM_DERIVATIVE_ME > 0.0f);
		assertTrue(ShooterConstants.ARM_DERIVATIVE_ME < 100.0f);
	}
	
	@Test
	public void testProportionConstantSet() {
		assertNotEquals(ShooterConstants.ARM_kP, ShooterConstants.BLANK_DOUBLE);
	}
	
	@Test
	public void testDerivativeConstantSet() {
		assertNotEquals(ShooterConstants.ARM_kD, ShooterConstants.BLANK_DOUBLE);
	}
	
	@Test
	public void testNoButtonConflicts() {
		assertNotEquals(ShooterConstants.FIRE_SEQUENCE_START_OPERATOR_STICK_BUTTON, ShooterConstants.LOAD_SEQUENCE_START_OPERATOR_STICK_BUTTON);
		assertNotEquals(ShooterConstants.FIRE_SEQUENCE_START_OPERATOR_STICK_BUTTON, ShooterConstants.LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON);
		assertNotEquals(ShooterConstants.FIRE_SEQUENCE_START_OPERATOR_STICK_BUTTON, ShooterConstants.LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON);
		assertNotEquals(ShooterConstants.FIRE_SEQUENCE_START_OPERATOR_STICK_BUTTON, ShooterConstants.LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON);
		assertNotEquals(ShooterConstants.FIRE_SEQUENCE_START_OPERATOR_STICK_BUTTON, ShooterConstants.LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON);
		assertNotEquals(ShooterConstants.LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON, ShooterConstants.LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON);
	}
	
	@Test
	public void testShooterLoadingButtonValid() {
		Assert.assertTrue(ShooterConstants.LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON >= 0);
		Assert.assertTrue(ShooterConstants.LOADING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON <= 12);
	}
	
	@Test
	public void testShooterLockingButtonValid() {
		Assert.assertTrue(ShooterConstants.LOCKING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON >= 0);
		Assert.assertTrue(ShooterConstants.LOCKING_ACTUATOR_TOGGLE_OPERATOR_STICK_BUTTON <= 12);
	}
	
	@Test
	public void testShooterLoadSequenceButtonValid() {
		Assert.assertTrue(ShooterConstants.LOAD_SEQUENCE_START_OPERATOR_STICK_BUTTON >= 0);
		Assert.assertTrue(ShooterConstants.LOAD_SEQUENCE_START_OPERATOR_STICK_BUTTON <= 12);
	}
	
	@Test
	public void testShooterFireSequenceButtonValid() {
		Assert.assertTrue(ShooterConstants.FIRE_SEQUENCE_START_OPERATOR_STICK_BUTTON >= 0);
		Assert.assertTrue(ShooterConstants.FIRE_SEQUENCE_START_OPERATOR_STICK_BUTTON <= 12);
	}
}
