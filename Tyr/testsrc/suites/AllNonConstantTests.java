package suites;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import shootertests.*;

@RunWith(Categories.class)
@IncludeCategory(NonConstant.class)
@SuiteClasses({
	TestShooterControllerInterfaceAndSetState.class,
	TestFullShooterTeleopCommand.class,
	TestShooterArmSetAngle.class
})

public class AllNonConstantTests {
  // the class remains empty,
  // used only as a holder for the above annotations
}