package main.strategy.pFStrategy;

/**
 * basic representation of a Point in 2D environment.
 * 
 * @author Behzad
 * 
 */
public class Point {
	private final double x;
	private final double y;

	/**
	 * Constructs a point.
	 * 
	 * @param x
	 *            x parameter of the point on 2D environment.
	 * @param y
	 *            y parameter of the point on 2D environment.
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * gets x parameter of the point.
	 * 
	 * @return x parameter of the point.
	 */
	public double getX() {
		return x;

	}

	/**
	 * gets y parameter of the point.
	 * 
	 * @return y parameter of the point.
	 */
	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "X: " + String.valueOf(x) + ", Y:" + String.valueOf(y);
	}
}
