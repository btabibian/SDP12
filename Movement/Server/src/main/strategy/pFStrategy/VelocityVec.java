package main.strategy.pFStrategy;

/**
 * Velocity vector class includes left and right wheels.
 */
public class VelocityVec extends Vector {
	/**
	 * constructor of the vector class which left and right wheels velocity.
	 */
	public VelocityVec(double left, double right) {
		super(left, right);

	}

	/**
	 * @returns left wheel velocity
	 */
	public double getLeft() {
		return this.getX();
	}

	/**
	 * @returns right wheel velocity
	 */
	public double getRight() {
		return this.getY();
	}

}
