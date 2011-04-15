package main.strategy.pFStrategy;

/**
 * Object interface.
 * 
 * @author Behzad
 * 
 */
public interface Object {
	/**
	 * calculates repulsive/attractive forces from this object using potential
	 * field algorithm.
	 * 
	 * @param point
	 *            the point that potential vectors will be computed against.
	 * @param repulsive
	 *            if set repulsive vector is calculated.
	 * @return calculated vector.
	 */
	public Vector getVector(Point point, boolean repulsive);

	/**
	 * calculates repulsive/attractive forces from this object using extended
	 * potential field algorithm.
	 * 
	 * @param point
	 *            the point that potential vectors will be computed against.
	 * @param repulsive
	 *            if set repulsive vector is calculated.
	 * @return calculated vector.
	 */
	public Vector getVector(Pos point, boolean repulsive);

}
