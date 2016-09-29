package wallFollower;

/**
 * Provides an interface to the ultrasonic controller
 *
 */
public interface UltrasonicController {
	
	/** Process the data provided by the ultrasonic sensor sample by sample
	 * @param distance The distance returned by the ultrasonic sensor
	 */
	public void processUltraSonicData(int distance);
	
	/** Provides the ultrasonic distance
	 * @return the ultrasonic distance
	 */
	public int readUltraSonicDistance();
}
