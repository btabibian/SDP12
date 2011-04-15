package main.strategy.pFStrategy;

/**
 * differential drive robot configuration class.
 * 
 * @author Behzad
 * 
 */
// b: distance between two wheels.
// r: radius of the wheel.
public class RobotConf {
	private double b;
	private double r;

	/**
	 * Robot Configuration constructor.
	 * 
	 * @param b
	 *            distance between two wheels.
	 * @param r
	 *            radius of each wheel.
	 */
	public RobotConf(double b, double r) {
		this.b = b;
		this.r = r;
	}

	/**
	 * gets distance between two wheels
	 * 
	 * @return distance between two wheels of the robot.
	 */
	public double getb() {
		return b;
	}

	/**
	 * gets radius of each wheel of the robot.
	 * 
	 * @return radius of each wheel of the robot.
	 */
	public double getr() {
		return r;
	}
}
