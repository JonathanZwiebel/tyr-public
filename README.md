This code will be competing in the 2016 First Robotics Competition: Stronghold

Language: Java

Libraries: WPILibJ2016, Strongback v1.1.3, JUnit 4

# TYR

# Code Structure
## testsrc package
This contains the JUnit tests for the code in src, as well as mocks not included in Strongback to support these.
The unit tests are split into packages based on the subsystem they pertain to, in addition to JUnit suites intended to test many features at once.

## src package
The src package contains the robot code that runs on the physical robot during operation

### Input
 * InputSystems is an interface to represent an object containing all robot sensors and hardware the robot receives input from.
 * InputHardware and MockInputHardware contain the actual instantiation of these objects.

### Subsystems
The robot has four subsystems.
 * Accumulator: Spins a pair of wheels at the front of the robot to intake or expel a boulder.
 * Breacher: An arm that lifts up or down to manipulate defenses such as the portcullis.
 * Drivetrain: Moves the robot using 6 pneumatic wheels.
 * Shooter: A pneumatic shooter that can rotate up and down and has a two latch firing system to shoot a boulder at the high-goal.

Each subsystem has its own package. The package contains
 * Hardware: Instantiates the hardware specific to the subsystem that are operated by the code (TalonSRX, etc). Extends the interface `SubsystemSystems` for JUnit mocks to function.
 * Controller: The interface with the overall `RobotController` that holds together the input/output that a subsystem handles
 * Commands subpackage: Various commands that run on the subsystem
 
## Libraries
WPILibJ provides the interface for Java code to run on the roboRIO and interface with robot hardware.
[Strongback](https://strongback.org) is built on WPILibJ and provides JUnit integration and a structure to run a command-based robot more easily.
JUnit comes as part of Strongback and is used to check the quality of the code beyond passing compilation.
