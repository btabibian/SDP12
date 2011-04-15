package main.strategy.pFStrategy;

/**
 * some utility functions.
 * 
 * @author Behzad
 * 
 */
public class Util {
	/**
	 * converts Radians velocity vector to Degrees.
	 * 
	 * @param vector
	 *            velocity vector in Radian to convert
	 * @return new velocity vector in Degrees
	 */
	public static VelocityVec convertVeltoDegree(VelocityVec vector) {

		return new VelocityVec(vector.getLeft() / (Math.PI) * 180, vector
				.getRight()
				/ (Math.PI) * 180);

	}

	/**
	 * this function normalizes input angle
	 * 
	 * @param angle
	 *            input angle to normalize
	 * @return normalized angle between 0 - Pi
	 */
	public static double normalize(double angle) {
		while ((angle < 0) | (angle >= (2 * Math.PI))) {
			if (angle < 0)
				angle += 2 * Math.PI;
			if (angle >= 2 * Math.PI)
				angle -= 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * maps input angle to an angle between -Pi and Pi, this function normalizes
	 * input.
	 * 
	 * @param angle
	 *            input angle to map.
	 * @return mapped angle between -Pi and Pi.
	 */
	public static double map2Pi(double angle) {
		double norm = normalize(angle);
		if (norm > Math.PI)
			norm -= Math.PI;
		// if(norm>Math.PI/2)
		// norm=Math.PI-norm;
		return norm;

	}
}
