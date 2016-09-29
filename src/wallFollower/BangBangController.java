package wallFollower;
import lejos.hardware.motor.*;

/**
 * Implements a bang-bang controller based on input from the ultrasonic sensor
 */
public class BangBangController implements UltrasonicController{
	private final int bandCenter, bandwidth;
	private final int motorLow, motorHigh;
	private int distance;
	private EV3LargeRegulatedMotor leftMotor, rightMotor;
	
	/**Provides a bang-bang controller object capable of processing ultraSonic data
	 * @param leftMotor The left motor object
	 * @param rightMotor The right motor
	 * @param bandCenter The center band
	 * @param bandwidth The width of the band
	 * @param motorLow The lowest value of the motor
	 * @param motorHigh The highest value of the motor
	 */
	public BangBangController(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
							  int bandCenter, int bandwidth, int motorLow, int motorHigh) {
		
		this.bandCenter = bandCenter;
		this.bandwidth = bandwidth;
		this.motorLow = motorLow;
		this.motorHigh = motorHigh;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		
		leftMotor.setSpeed(motorHigh);
		rightMotor.setSpeed(motorHigh);
		leftMotor.forward();
		rightMotor.forward();
	}
	
	@Override
	public void processUSData(int distance) {
		this.distance = distance;
		// TODO: process a movement based on the us distance passed in (BANG-BANG style)
	}

	@Override
	public int readUSDistance() {
		return this.distance;
	}
}
