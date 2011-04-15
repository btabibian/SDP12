package main.strategy.pFStrategy;

import java.util.ArrayList;
import java.util.List;

import main.Runner;

/**
 * main implementation (Extended) potential field.
 * 
 * @author Behzad
 * 
 */
public class PFPlanning {
	Pos robot;
	PointObject opponent;
	PointObject ball;
	double default_power = 5;
	RobotConf config;
	double Stopdistance = 5;
	List<Object> objects;
	// power for opponent.
	double opponentPower;
	// influence distance for opponent
	double opponentInf;
	// power for goal location.
	double ballPower;
	// power orientation, extended potential field.
	double opponentAlphaPower;

	/**
	 * Constructor for normal Potential Field Planning algorithm
	 * 
	 * @param conf
	 *            Configuration parameters of the robot.
	 * @param opponentPower
	 *            Repulsive power for opponent(obstacle).
	 * @param opponentInf
	 *            Influence distance for opponent(obstacle).
	 * @param targetPower
	 *            Power for goal position.
	 */
	public PFPlanning(RobotConf conf, double opponentPower, double opponentInf,
			double targetPower) {
		this.config = conf;
		objects = new ArrayList<Object>();
		this.opponentPower = opponentPower;
		this.opponentInf = opponentInf;
		this.ballPower = targetPower;
	}

	/**
	 * Constructor for Extended Potential Field Planning algorithm
	 * 
	 * @param conf
	 *            Configuration parameters of the robot.
	 * @param opponentPower
	 *            Repulsive power for opponent(obstacle).
	 * @param opponentInf
	 *            Influence distance for opponent(obstacle).
	 * @param targetPower
	 *            Power for goal position.
	 * @param alpha
	 *            alpha parameter of the Extended Potential Field Algorithm.
	 */
	public PFPlanning(RobotConf conf, double opponentPower, double opponentInf,
			double targetPower, double alpha) {
		this.config = conf;
		objects = new ArrayList<Object>();
		this.opponentPower = opponentPower;
		this.opponentInf = opponentInf;
		this.ballPower = targetPower;
		this.opponentAlphaPower = alpha;
	}

	/**
	 * initialization phase of the algorithm.
	 * 
	 * @param robot
	 *            initial robot position.
	 * @param opponent
	 *            initial opponent(obstacle) position.
	 * @param ball
	 *            goal position.
	 * @param orig
	 *            return original vector produced by PF or velocity vectors for
	 *            robot wheels
	 */
	private void init(Pos robot, Pos opponent, Point ball, boolean orig) {
		PointObject opponentObj = new PointObject(opponent.getLocation(),
				opponentPower, opponentInf, opponentAlphaPower);
		PointObject ballObj = new PointObject(ball, ballPower, Double.MAX_VALUE);
		this.robot = robot;
		this.opponent = opponentObj;
		this.ball = ballObj;

	}

	/**
	 * Calculates velocity vectors of the robot using Extended Potential Field
	 * algorithm.
	 * 
	 * @param robot
	 *            robot position.
	 * @param opponent
	 *            opponent(obstacle) position.
	 * @param ball
	 *            goal(target) position
	 * @param orig
	 *            if set returns original vector produced by PF.
	 * @return Velocity Vector to be applied on the robot.
	 */
	public VelocityVec update(Pos robot, Pos opponent, Point ball, boolean orig) {
		init(robot, opponent, ball, orig);
		List<Object> complList = new ArrayList<Object>(objects);
		complList.add((Object) this.opponent);
		Vector vball = new Vector(ball);
		Vector vrobot = new Vector(robot.getLocation());
		Vector res = GoTo(complList, this.ball, robot.getLocation());
		if (vball.subtract(vrobot).size() < Stopdistance) {
			res = new Vector(0, 0);
		}
		if (Runner.DEBUG) {
			// System.out.println("Result Vector: " + res.toString());
		}
		if (orig)
			return new VelocityVec(res.getX(), res.getY());
		else
			return getVelocity(res, robot);

	}

	/**
	 * Adds static object to list of obstacles.
	 * 
	 * @param r
	 *            object to be added.
	 */
	public void AddObjects(Object r) {
		objects.add(r);
	}

	/**
	 * computes velocity vector for a single step toward goal position using
	 * normal potential field algorithm.
	 * 
	 * @param obstacles
	 *            list of obstacles.
	 * @param dest_obj
	 *            destination position.
	 * @param start_point
	 *            current position.
	 * @return velocity to be applied on the robot.
	 */
	private Vector GoTo(List<Object> obstacles, PointObject dest_obj,
			Point start_point) {
		// calculate distance so if we reached the target position we stop.
		double dist = Math.sqrt((start_point.getX() - dest_obj.getX())
				* (start_point.getX() - dest_obj.getX())
				+ (start_point.getY() - dest_obj.getY())
				* (start_point.getY() - dest_obj.getY()));
		if (dist < Stopdistance) {
			return new Vector(0, 0);
		}

		Vector rep = new Vector(0, 0);
		// iterate through all obstacles and compute sum of all repulsive
		// vectors
		for (int i = 0; i < obstacles.size(); i++) {
			rep = rep.add(obstacles.get(i).getVector(start_point, true));
		}
		// Compute attractive vector.
		Vector att = dest_obj.getVector(start_point, false);

		return att.add(rep);

	}

	/**
	 * computes velocity vector for a single step toward goal position using
	 * extended potential field algorithm.
	 * 
	 * @param obstacles
	 *            list of obstacles.
	 * @param dest_obj
	 *            destination position.
	 * @param start_point
	 *            current position.
	 * @return velocity to be applied on the robot.
	 */
	private Vector GoTo(List<Object> obstacles, PointObject dest_obj,
			Pos start_point) {
		// calculate distance so if we reached the target position we stop.
		double dist = Math.sqrt((start_point.getLocation().getX() - dest_obj
				.getX())
				* (start_point.getLocation().getX() - dest_obj.getX())
				+ (start_point.getLocation().getY() - dest_obj.getY())
				* (start_point.getLocation().getY() - dest_obj.getY()));
		if (dist < Stopdistance) {
			return new Vector(0, 0);
		}

		Vector rep = new Vector(0, 0);
		// iterate through all obstacles and compute sum of all repulsive
		// vectors
		for (int i = 0; i < obstacles.size(); i++) {
			rep = rep.add(obstacles.get(i).getVector(start_point, true));
		}
		// Compute attractive vector.
		if (Runner.DEBUG) {
			// System.out.println("PFPlanning::PointObject::attractive Force, Clac. attractive forc");
		}
		Vector att = dest_obj.getVector(start_point, false);

		return att.add(rep);

	}

	/**
	 * converts linear and angular velocities to left and right wheels velocity.
	 * 
	 * @param Vlin
	 *            linear velocity
	 * @param VAng
	 *            angular velocity.
	 * @param r
	 *            wheels radius
	 * @return left/right wheels velocity.
	 */
	private VelocityVec CvtVelocity(double Vlin, double VAng, double r) {
		double left = Vlin - r * Math.sin(VAng);
		double right = Vlin + r * Math.sin(VAng);
		VelocityVec vector = new VelocityVec(left, right);
		// System.out.println("r: " + r+" left" + left + "right" + right);
		return vector;
	}

	/**
	 * This function computes linear and angular velocities for a differential
	 * drive robot and return left/right wheels velocity.
	 * 
	 * @param inputVel
	 *            input velocity to apply.
	 * @param current
	 *            current position of the robot.
	 * @return returns left/right wheels velocity.
	 */
	private VelocityVec getVelocity(Vector inputVel, Pos current) {

		double size = inputVel.size();
		if (size == 0)
			return new VelocityVec(0, 0);
		double alpha = inputVel.normalAngle();
		double dist_alpha = alpha - current.getAngle();
		if (Runner.DEBUG) {
			// System.out.println("Current T: "+current.getAngle()+", dist_alpha="+dist_alpha);
		}
		if (dist_alpha > Math.PI)
			dist_alpha = -1 * (2 * Math.PI - dist_alpha);
		else if (dist_alpha < -1 * Math.PI)
			dist_alpha = 2 * Math.PI + dist_alpha;
		if (Runner.DEBUG) {
			// System.out.println("Final dist_alpha="+dist_alpha);
		}
		double Vlin = Math.cos(dist_alpha) * size;
		double angSize = 1 / size;
		// if(*angSizeangSize>10)
		// angSize=10;
		double Vang = 0.4 * dist_alpha / Math.PI;
		// double threshold=0.1;
		// if(Vang>threshold)
		// Vang=threshold;
		// if(Vang<-1*threshold)
		// Vang=-1*threshold;

		if (Runner.DEBUG) {
			// System.out.println(Vlin + " " + Vang);
		}
		return CvtVelocity(Vlin, Vang, config.getr());
	}

}
