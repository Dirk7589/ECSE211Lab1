package wallFollower;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class PController implements UltrasonicController {

	private final int bandCenter, bandwidth;
	private final int motorStraight = 200, FILTER_OUT = 20;
	private EV3LargeRegulatedMotor leftMotor, rightMotor;
	private int distance;
	private int filterControl;
	private final int DISTANCE_THRESHOLD = 255;

	public PController(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
					   int bandCenter, int bandwidth) {
		//Default Constructor
		this.bandCenter = bandCenter;
		this.bandwidth = bandwidth;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		leftMotor.setSpeed(motorStraight);					// Initalize motor rolling forward
		rightMotor.setSpeed(motorStraight);
		leftMotor.forward();
		rightMotor.forward();
		filterControl = 0;
	}

	@Override
	public void processUSData(int distance) {
		distance = lowPassFilter(distance)


		// TODO: process a movement based on the us distance passed in (P style)
	}


	@Override
	public int readUSDistance() {
		return this.distance;
	}

	public int lowPassFilter(int distance) {
		// rudimentary filter - toss out invalid samples corresponding to null signal.
		// if the values are greater than our threshold value, than there are two options
		// 	1. we're seeing a gap
		//    so we increment filterControl and do not accept the values
		//    once we have passed our gap, then we reset the filter control
		//  2. we're not seeing a gap, and we're seeing a corner
		//    this should be handled in the code itself
		// otherwise, the values that we are seeing are below threshold value
		// these are true values
		// (n.b. this was not included in the Bang-bang controller, but easily could have).
		if (distance >= DISTANCE_THRESHOLD && filterControl < FILTER_OUT) {
			filterControl ++;
		} else if (filterControl > FILTER_OUT) {
			this.distance = distance;
		} else {
			filterControl = 0;
			this.distance = distance;
		}
	}

}
