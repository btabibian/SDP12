package main.strategy.pFStrategy;

import main.Runner;

/**
 * basic Point based Object class includes x,y
 * 
 * @author Behzad
 * 
 */
public class PointObject extends Vector implements Object {

	private final double power;
	private final double infl_distance;
	private final double alpha;

	/**
	 * constructs a point based object.
	 * 
	 * @param x
	 *            x position of the object.
	 * @param y
	 *            y position of the object.
	 * @param power
	 *            repulsive/attractive power of the object.
	 * @param infl_distance
	 *            influence distance of the object when it is an obstacle.
	 */
	public PointObject(double x, double y, double power, double infl_distance) {
		super(x, y);
		this.power = power;
		this.infl_distance = infl_distance;
		this.alpha = 1;
	}

	/**
	 * constructs a point based object.
	 * 
	 * @param x
	 *            x position of the object.
	 * @param y
	 *            y position of the object.
	 * @param power
	 *            repulsive/attractive power of the object.
	 * @param infl_distance
	 *            influence distance of the object when it is an obstacle.
	 * @param alpha
	 *            alpha parameter for extended potential field algorithm.
	 */
	public PointObject(double x, double y, double power, double infl_distance,
			double alpha) {
		super(x, y);
		this.power = power;
		this.infl_distance = infl_distance;
		this.alpha = alpha;
	}

	/**
	 * Constructs a point based object.
	 * 
	 * @param point
	 *            position of the obstacle.
	 * @param power
	 *            repulsive/attractive power of the object.
	 * @param infl_distance
	 *            influence distance of the object when it is an obstacle.
	 */
	public PointObject(Point point, double power, double infl_distance) {
		this(point.getX(), point.getY(), power, infl_distance);
	}

	/**
	 * Constructs a point based object.
	 * 
	 * @param point
	 *            position of the obstacle.
	 * @param power
	 *            repulsive/attractive power of the object.
	 * @param infl_distance
	 *            influence distance of the object when it is an obstacle.
	 * @param alpha
	 *            alpha parameter for extended potential field algorithm.
	 */
	public PointObject(Point point, double power, double infl_distance,
			double alpha) {
		this(point.getX(), point.getY(), power, infl_distance, alpha);
	}

	@Override
	public Vector getVector(Point point, boolean repulsive) {
		if (repulsive) {

			double distance = Math.sqrt((this.getX() - point.getX())
					* (this.getX() - point.getX())
					+ (this.getY() - point.getY())
					* (this.getY() - point.getY()));
			if (distance < infl_distance) {

				double p = power * (1 / distance - 1 / infl_distance) * 1
						/ (distance * distance) * 1 / distance;
				Vector out_point = new Vector(point);
				return out_point.subtract(this).mult(p);
			} else
				return new Vector(new Point(0, 0));
		} else {

			Vector out_point = new Vector(point);
			return out_point.subtract(this).mult(power * -1);

		}

	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public Vector getVector(Pos point, boolean repulsive) {
		if (repulsive) {
			double distance = Math.sqrt((this.getX() - point.getLocation()
					.getX())
					* (this.getX() - point.getLocation().getX())
					+ (this.getY() - point.getLocation().getY())
					* (this.getY() - point.getLocation().getY()));
			if (Runner.DEBUG) {
				System.out.println("obstacle Distance:" + distance);
				System.out.println("PFPlanning::PointObject::inf_distance:"
						+ infl_distance);
			}
			if (distance < infl_distance & power != 0) {
				try {
					Vector out_point = new Vector(point.getLocation());
					out_point = this.subtract(out_point);
					double angle = Math.atan2(out_point.getY(), out_point
							.getX());
					double diffAngle = Math.abs(angle - point.getAngle());
					double norm = Util.map2Pi(diffAngle);
					System.out.println("Angle: " + norm);
					double p = power * (1 / distance - 1 / infl_distance)
							* (1 / (distance * distance)) * (1 / distance)
							* ((Math.PI - norm) / (Math.PI)) * alpha;
					return out_point.mult(-1 * p);
				} catch (Exception ex) {
					System.out
							.println("PFPlanning::PointObject::Exception thrown");
					return new Vector(new Point(0, 0));
				}
			} else
				return new Vector(new Point(0, 0));
		} else {

			Vector out_point = new Vector(point.getLocation());
			Vector res = out_point.subtract(this);
			System.out.println("goal Distance:" + res.size());
			Vector final_res = res.mult(power * -1);
			if (Runner.DEBUG) {
				System.out.println("PFPlanning::PointObject::attractive Force:"
						+ final_res);
				System.out.println("PFPlanning::PointObject::current:"
						+ out_point);
				System.out.println("PFPlanning::PointObject::destination:"
						+ this);
			}
			return final_res;
		}
	}
}
