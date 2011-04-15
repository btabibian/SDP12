package main.strategy.pFStrategy;

/**
 * basic support for basic vector operations.
 */

public class Vector extends Point {
	/**
	 * vector constructor.
	 * 
	 * @param x
	 *            x value of the vector
	 * 
	 * @param y
	 *            y value of the vector
	 */
	public Vector(double x, double y) {
		super(x, y);

	}

	/**
	 * vector constructor
	 * 
	 * @param point
	 *            an instance of a point to construct the vector with
	 */
	public Vector(Point point) {
		super(point.getX(), point.getY());
	}

	/**
	 * vectors addition operation
	 * 
	 * @param vector
	 *            second vector to add with.
	 * 
	 * @return result addition result.
	 */
	public Vector add(Vector vector) {
		return new Vector(this.getX() + vector.getX(), this.getY()
				+ vector.getY());
	}

	/**
	 * vectors subtraction operation
	 * 
	 * @param vector
	 *            second vector to subtract from this vector.
	 * 
	 * @return result subtracted vector.
	 */
	public Vector subtract(Vector vector) {
		return new Vector(this.getX() - vector.getX(), this.getY()
				- vector.getY());
	}

	/**
	 * scalar multiplication.
	 * 
	 * @param scalar
	 *            scalar value to multiply this vector with.
	 * 
	 * @return multiplied vector.
	 */
	public Vector mult(double scalar) {
		return new Vector(this.getX() * scalar, this.getY() * scalar);
	}

	/**
	 * normalized angle of this vector with origin.
	 * 
	 * @return normalized angle value.
	 */
	public double normalAngle() {
		if (Math.atan2(getY(), getX()) >= 0)
			return Math.atan2(getY(), getX());
		else
			return 2 * Math.PI + Math.atan2(getY(), getX());
	}

	/**
	 * this function returns size of the vector.
	 * 
	 * @return size of vector.
	 */
	public double size() {
		return Math.sqrt(getY() * getY() + getX() * getX());
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
