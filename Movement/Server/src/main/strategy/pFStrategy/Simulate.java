package main.strategy.pFStrategy;

/**
 * Basic forward kinematic implementation of diffrential drive robot for
 * simulating robot movement.
 * 
 * @author Behzad
 * 
 */
public class Simulate {
	private double b;
	private Pos position;

	/**
	 * constructor for the simulator.
	 * 
	 * @param initPosition
	 *            initial position of robot.
	 * @param b
	 *            distance between two wheels
	 */
	public Simulate(Pos initPosition, double b) {
		position = initPosition;
		this.b = b;
	}

	/**
	 * constructor for the simulator assuming that initial position is (0,0).
	 * 
	 * @param b
	 *            distance between tow wheels of robot.
	 */
	public Simulate(double b) {
		this(new Pos(new Point(0, 0), 0), b);
	}

	/**
	 * computes next position of the robot.
	 * 
	 * @param leftV
	 *            left wheel velocity
	 * @param rightV
	 *            right wheel velocity.
	 * @param time
	 *            time step.
	 * @return next position of the robot.
	 */
	public Pos move(double leftV, double rightV, double time) {
		double theta = (rightV - leftV) * time / b + position.getAngle();
		double S = (rightV + leftV) / 2;
		double x = S * Math.cos(theta) * time + position.getLocation().getX();
		double y = S * Math.sin(theta) * time + position.getLocation().getY();
		position = new Pos(new Point(x, y), theta);
		return position;

	}
}
