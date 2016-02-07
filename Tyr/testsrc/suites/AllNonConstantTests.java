package suites;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import shootertests.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestShooterControllerInterfaceAndSetState.class,
	TestFullShooterTeleopCommand.class,
	TestShooterArmSetAngle.class
})

public class AllNonConstantTests {
  // the class remains empty,
  // used only as a holder for the above annotations
}