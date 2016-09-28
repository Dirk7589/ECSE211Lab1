package wallFollower;

public class RobotConstants {

	public static final int BAND_CENTER = 20;			// Offset from the wall (cm)
	public static final int BAND_WIDTH = 3;				// Width of dead band (cm)
	public static final int MOTOR_LOW = 100;			// Speed of slower rotating wheel (deg/sec)
	public static final int MOTOR_HIGH = 200;			// Speed of the faster rotating wheel (deg/seec)

	public static String ULTRASONICSENSOR_PORT_NAME = "S1";
	public static String LEFT_MOTOR_PORT = "A";
	public static String RIGHT_MOTOR_PORT = "D";
	
}
