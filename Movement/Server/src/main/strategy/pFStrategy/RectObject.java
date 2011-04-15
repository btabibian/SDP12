package main.strategy.pFStrategy;

/**
 * A rectangular object which calculates repulsive vector.
 * 
 * @author Behzad
 * 
 */
public class RectObject implements Object {

	private final double power;
	private final double infl_distance;
	private final Point p1;
	private final Point p2;

	/**
	 * constructs a rectangular object.
	 * 
	 * @param p1
	 *            top left of the rectangle.
	 * @param p2
	 *            bottom right of the rectangle
	 * @param power
	 *            repulsive power of the object.
	 * @param infl_distance
	 *            influance distance that this object can affect.
	 */
	public RectObject(Point p1, Point p2, double power, double infl_distance) {
		this.p1 = p1;
		this.p2 = p2;
		this.power = power;
		this.infl_distance = infl_distance;

	}

	/**
	 * gets top left point of the object.
	 * 
	 * @return top left point of the object.
	 */
	public Point getp1() {
		return p1;
	}

	/**
	 * gets bottom right of the object.
	 * 
	 * @return bottom right of the object.
	 */
	public Point getp2() {
		return p2;
	}

	@Override
	public Vector getVector(Point point, boolean repulsive) {

		if ((point.getX() < p1.getX() && point.getX() > p2.getX())
				|| (point.getX() > p1.getX() && point.getX() < p2.getX())) {
			PointObject obj = new PointObject(point.getX(), (p1.getY() + p2
					.getY()) / 2, power, infl_distance);
			return obj.getVector(point, repulsive);
		} else if ((point.getY() < p1.getY() && point.getY() > p2.getY())
				|| (point.getY() > p1.getY() && point.getY() < p2.getY())) {
			PointObject obj = new PointObject((p1.getX() + p2.getX()) / 2,
					point.getY(), power, infl_distance);
			return obj.getVector(point, repulsive);
		} else {
			return new Vector(0, 0);

		}
	}

	@Override
	public String toString() {
		return "Point1: " + p1.toString() + ",Point2: " + p2.toString();
	}

	public Vector getVector(Pos point, boolean repulsive) {

		return getVector(point.getLocation(), repulsive);

	}

}
