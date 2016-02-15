package suites;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import shootertests.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestShooterControllerInterfaceAndSetState.class,
	TestFullShooterTeleopCommand.class,
	TestShooterArmSetAngle.class,
	TestShooterConstants.class
})

public class AllTests {
  // the class remains empty,
  // used only as a holder for the above annotations
}