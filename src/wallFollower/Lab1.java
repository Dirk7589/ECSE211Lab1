package wallFollower;

import lejos.hardware.sensor.*;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;

//
// Lab 1:  EV3 Wall Following robot
//
// This is the main class for the wall follower.

public class Lab1 {

// Parameters: adjust these for desired performance

	private static final int bandCenter = 20;			// Offset from the wall (cm)
	private static final int bandWidth = 3;				// Width of dead band (cm)
	private static final int motorLow = 100;			// Speed of slower rotating wheel (deg/sec)
	private static final int motorHigh = 200;			// Speed of the faster rotating wheel (deg/seec)
	
// Static Resources:
//
// Ultrasonic sensor connected to input port S1
// Left motor connected to output A
// Right motor connected to output B
	private static String ultraSonicSensorPortName = "S1";
	private static String leftMotorPort = "A";
	private static String rightMotorPort = "D";
	
	private static final Port ultraSonicSensorPort = LocalEV3.get().getPort(ultraSonicSensorPortName);
	private static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(leftMotorPort));
	private static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(rightMotorPort));
	
// Main entry point - instantiate objects used and set up sensor
	
	public static void main(String [] args) {
		
		int pushedButton = 0;
		Printer.printMainMenu();						// Set up the display on the EV3 screen
		while (pushedButton == 0){								// and wait for a button press.  The button
			pushedButton = Button.waitForAnyPress();			// ID (option) determines what type of control to use
		}
	
		// Setup ultrasonic sensor
		// Note that the EV3 version of leJOS handles sensors a bit differently.
		// There are 4 steps involved:
		// 1. Create a port object attached to a physical port (done already above)
		// 2. Create a sensor instance and attach to port
		// 3. Create a sample provider instance for the above and initialize operating mode
		// 4. Create a buffer for the sensor data
		
		@SuppressWarnings("resource")							    // Because we don't bother to close this resource
		SensorModes ultraSonicSensor = new EV3UltrasonicSensor(ultraSonicSensorPort);		// usSensor is the instance
		SampleProvider ultraSonicDistance = ultraSonicSensor.getMode("Distance");	// usDistance provides samples from this instance
		float[] ultraSonicData = new float[ultraSonicDistance.sampleSize()];		// usData is the buffer in which data are returned
		
		// Setup Printer											// This thread prints status information
		Printer printer = null;										// in the background
		
		// Setup Ultrasonic Poller									// This thread samples the US and invokes
		UltrasonicPoller ultraSonicPoller = null;							// the selected controller on each cycle
				
		// Depending on which button was pressed, invoke the US poller and printer with the
		// appropriate constructor.
		
		switch(pushedButton) {
		case Button.ID_LEFT:
			// Bang-bang control selected
			BangBangController bangbang = new BangBangController(leftMotor, rightMotor,
					 bandCenter, bandWidth, motorLow, motorHigh);
			ultraSonicPoller = new UltrasonicPoller(ultraSonicDistance, ultraSonicData, bangbang);
			printer = new Printer(pushedButton, bangbang);
			break;
		case Button.ID_RIGHT:										// Proportional control selected
			PController p = new PController(leftMotor, rightMotor, bandCenter, bandWidth);
			ultraSonicPoller = new UltrasonicPoller(ultraSonicDistance, ultraSonicData, p);
			printer = new Printer(pushedButton, p);
			break;
		default:
			System.out.println("Error - invalid button");			// None of the above - abort
			System.exit(-1);
			break;
		}
		
		// Start the poller and printer threads
		
		ultraSonicPoller.start();
		printer.start();
		
		//Wait here forever until button pressed to terminate wallfollower
		Button.waitForAnyPress();
		System.exit(0);
		
	}
}
