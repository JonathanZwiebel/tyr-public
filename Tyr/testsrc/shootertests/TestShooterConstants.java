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
		assertNotEquals(ShooterConstants.SHOOTER_FIRE_SEQUENCE_BUTTON, ShooterConstants.SHOOTER_LOAD_SEQUENCE_BUTTON);
		assertNotEquals(ShooterConstants.SHOOTER_FIRE_SEQUENCE_BUTTON, ShooterConstants.SHOOTER_LOADING_BUTTON);
		assertNotEquals(ShooterConstants.SHOOTER_FIRE_SEQUENCE_BUTTON, ShooterConstants.SHOOTER_LOCKING_BUTTON);
		assertNotEquals(ShooterConstants.SHOOTER_LOAD_SEQUENCE_BUTTON, ShooterConstants.SHOOTER_LOADING_BUTTON);
		assertNotEquals(ShooterConstants.SHOOTER_LOAD_SEQUENCE_BUTTON, ShooterConstants.SHOOTER_LOCKING_BUTTON);
		assertNotEquals(ShooterConstants.SHOOTER_LOADING_BUTTON, ShooterConstants.SHOOTER_LOCKING_BUTTON);
	}
	
	@Test
	public void testShooterLoadingButtonValid() {
		Assert.assertTrue(ShooterConstants.SHOOTER_LOADING_BUTTON >= 0);
		Assert.assertTrue(ShooterConstants.SHOOTER_LOADING_BUTTON <= 12);
	}
	
	@Test
	public void testShooterLockingButtonValid() {
		Assert.assertTrue(ShooterConstants.SHOOTER_LOCKING_BUTTON >= 0);
		Assert.assertTrue(ShooterConstants.SHOOTER_LOCKING_BUTTON <= 12);
	}
	
	@Test
	public void testShooterLoadSequenceButtonValid() {
		Assert.assertTrue(ShooterConstants.SHOOTER_LOAD_SEQUENCE_BUTTON >= 0);
		Assert.assertTrue(ShooterConstants.SHOOTER_LOAD_SEQUENCE_BUTTON <= 12);
	}
	
	@Test
	public void testShooterFireSequenceButtonValid() {
		Assert.assertTrue(ShooterConstants.SHOOTER_FIRE_SEQUENCE_BUTTON >= 0);
		Assert.assertTrue(ShooterConstants.SHOOTER_FIRE_SEQUENCE_BUTTON <= 12);
	}
}
