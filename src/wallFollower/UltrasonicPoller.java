package wallFollower;
import lejos.robotics.SampleProvider;

/** Provides a thread to poll the ultrasonic sensor and return data
 * via the UltrasonicController interface
 * 
 * Control of the wall follower is applied periodically by the
 * UltrasonicPoller thread.  The while loop at the bottom executes
 * in a loop.  Assuming that the us.fetchSample, and cont.processUSData
 * methods operate in about 20mS, and that the thread sleeps for
 * 50 mS at the end of each loop, then one cycle through the loop
 * is approximately 70 mS.  This corresponds to a sampling rate
 * of 1/70mS or about 14 Hz.
 */
public class UltrasonicPoller extends Thread{
	private SampleProvider ultraSonicSampleProvider;
	private UltrasonicController controller;
	private float[] ultraSonicData;
	private final int SLEEP_TIME = 50;
	/**
	 * @param ultraSonicSampleProvider
	 * @param ultraSonicData
	 * @param ultraSonicController
	 */
	public UltrasonicPoller(SampleProvider ultraSonicSampleProvider, float[] ultraSonicData, 
			UltrasonicController ultraSonicController) {
		this.ultraSonicSampleProvider = ultraSonicSampleProvider;
		this.controller = ultraSonicController;
		this.ultraSonicData = ultraSonicData;
	}

	/* Runs the ultrasonic poller thread
	 * Sensors now return floats using a uniform protocol.
	 * Need to convert US result to an integer [0,255]
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		int distance;
		while (true) {
			ultraSonicSampleProvider.fetchSample(ultraSonicData,0);	// acquire data
			distance=(int)(ultraSonicData[0]*100.0);	// extract from buffer, cast to int
			controller.processUltraSonicData(distance);	// now take action depending on value
			try { 
				Thread.sleep(SLEEP_TIME); 
			} 
			catch(Exception e){
				Printer.printGeneralErrorMessage(e);
			}
		}
	}

}
