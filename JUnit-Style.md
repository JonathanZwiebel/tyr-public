#Team 8 JUnit Styleguide

----------
**Examples below all the definitions.**

Annotations:

`@Test`:
-- Most basic annotation, shows that a method is a test and will be run by the test runners.

`@Before/@After`: 
-- Annotates a method to be run before and after every test in the class.

`@BeforeClass/AfterClass`:
	-- Annotates a method to be run in the beginning or end of the class.

`@RunWith(“Runner”.class)`
--	An annotation that changes which runner is used to run the tests (ex. “Suite.class”)

`@Category(“Category”.class)`
	--Annotates a test to add it to a category, which is a class or interface. You can run all of the classes in a category with the  `@IncludeCategory` annotation

`@Suite.SuiteClasses(A.class,B.class)`
--	Adds all of the listed test classes to the suite of tests.

`@SuiteClasses(A.class,B.class)`
	-- Adds all of the listed test classes to the current suite, primarily used when running a category of tests

`@Rule`
--	Defines a rule that applies to all of the test methods. 


#Examples

***@Test***
```
@Test	
public void isOne(){

}

```

***@Rule***

```
@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Rule
	public TestRule globalTimeout = new Timeout(3000);
	@Test
	public void test() {
		thrown.expect(ArrayIndexOutOfBounds.class);		
		int[] a = {1,2,3};
		int x = a[10]; //This should throw the indexOutOfBounds exception
    				  
}

```