package main.strategy.pFStrategy;

/*
 * represents location and angle of an object.
 */
public class Pos {
	private final Point location;
	private final double angle;

	/**
	 * constructor of Pos class
	 * 
	 * @param loc
	 *            location of the entity.
	 * @param angle
	 *            orientation of the entity.
	 */
	public Pos(Point loc, double angle) {
		this.location = loc;
		this.angle = angle;
	}

	/**
	 * gets angle of the pos.
	 * 
	 * @return angle of the pos.
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * gets location of the pos.
	 * 
	 * @return location of the pos.
	 */
	public Point getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return location.toString() + "," + angle;

	}
}
