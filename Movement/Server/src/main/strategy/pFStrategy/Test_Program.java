package main.strategy.pFStrategy;

import java.io.*;

/**
 * A test program to test various scenarios for the program.
 * 
 * @author Behzad
 * 
 */
public class Test_Program {
	/**
	 * Main function to start the program.
	 * 
	 * @param args
	 *            input arguments.
	 */
	public static void main(String[] args) {
		double default_power = 300;
		double b = 15.2;
		double r = 8.27;
		RobotConf conf = new RobotConf(b, r);
		// 10000, 500.0
		PFPlanning plann = new PFPlanning(conf, 10000000, Double.MAX_VALUE,
				0.05, 500.0);
		Pos initPos = new Pos(new Point(0, 380), 0);
		Pos opponent = new Pos(new Point(300, 550), 0);
		Point ball = new Point(600, 650);

		double wall_Thickness = 0;
		Point top_left = new Point(0, 0);
		Point bottom_right = new Point(50, 50);
		RectObject left_wall = new RectObject(new Point(top_left.getX()
				- wall_Thickness, top_left.getY()), new Point(top_left.getX()
				+ wall_Thickness, bottom_right.getY()), default_power,
				Double.MAX_VALUE);
		System.out.println("Left wall:" + left_wall.toString());
		RectObject right_wall = new RectObject(new Point(bottom_right.getX()
				- wall_Thickness, top_left.getY()), new Point(bottom_right
				.getX()
				+ wall_Thickness, bottom_right.getY()), default_power,
				Double.MAX_VALUE);
		System.out.println("Right wall:" + right_wall.toString());
		RectObject top_wall = new RectObject(new Point(top_left.getX(),
				top_left.getY() - wall_Thickness), new Point(bottom_right
				.getX(), top_left.getY() + wall_Thickness), default_power,
				Double.MAX_VALUE);
		System.out.println("Top wall:" + top_wall.toString());
		RectObject bottom_wall = new RectObject(new Point(top_left.getX(),
				bottom_right.getY() - wall_Thickness), new Point(bottom_right
				.getX(), bottom_right.getY() + wall_Thickness), default_power,
				Double.MAX_VALUE);
		System.out.println("Bottom wall:" + bottom_wall.toString());
		output_path(initPos, 1, ball, opponent, plann, conf);

	}

	/**
	 * This function simulates movement of the robot.
	 * 
	 * @param init
	 *            initial position of the robot.
	 * @param time_step
	 *            time step in which simulation should be done.
	 * @param ball
	 *            goal position(in this case ball position) to reach.
	 * @param opponent
	 *            position of obstacle(in this scenario opponent).
	 * @param planner
	 *            a instance of Potential Field planner.
	 * @param config
	 *            robot configuration.
	 */
	private static void output_path(Pos init, double time_step, Point ball,
			Pos opponent, PFPlanning planner, RobotConf config) {
		File path = new File("pathOutput.txt");
		FileWriter writer;
		try {
			writer = new FileWriter(path);

			Simulate sim = new Simulate(init, config.getb());
			Pos current = init;
			current = new Pos(current.getLocation(), Math.toRadians(current
					.getAngle()));
			double distance = Math.sqrt((current.getLocation().getX() - ball
					.getX())
					* (current.getLocation().getX() - ball.getX())
					+ (current.getLocation().getY() - ball.getY())
					* (current.getLocation().getY() - ball.getY()));
			while (true) {
				VelocityVec res = planner
						.update(current, opponent, ball, false);
				if (res.getLeft() == 0 && res.getRight() == 0)
					break;
				System.out.println("Sending :"
						+ (int) Math.toDegrees(res.getLeft()) + " "
						+ (int) Math.toDegrees(res.getRight()));
				current = sim.move(res.getLeft(), res.getRight(), time_step);
				System.out.println("Current position: "
						+ current.getLocation().getX() + " "
						+ current.getLocation().getY());
				distance = Math.sqrt((current.getLocation().getX() - ball
						.getX())
						* (current.getLocation().getX() - ball.getX())
						+ (current.getLocation().getY() - ball.getY())
						* (current.getLocation().getY() - ball.getY()));
				writer.write(current.getLocation().getX() + ","
						+ current.getLocation().getY() + "," + res.getX() + ","
						+ res.getY() + "\n");
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method produces all potential vectors in the field.
	 * 
	 * @param top_left
	 *            top left point of the screen.
	 * @param bottom_right
	 *            bottom right point of the screen
	 * @param ball
	 *            goal(ball) position
	 * @param opponent
	 *            obstacle(opponent) position.
	 * @param planner
	 *            instance of Potential Field planning object.
	 * @param resolution
	 *            distance between two vectors to compute.
	 */
	private static void ouput_allVectors(Point top_left, Point bottom_right,
			Point ball, Pos opponent, PFPlanning planner, double resolution) {
		File vel = new File("vel-Output.txt");
		FileWriter writer;
		try {
			writer = new FileWriter(vel);

			for (double x = top_left.getX(); x <= bottom_right.getX(); x += resolution) {
				for (double y = top_left.getY(); y <= bottom_right.getY(); y += resolution) {
					Pos robot = new Pos(new Point(x, y), 0);
					Vector vec;
					Point b;

					if (ball != null)
						b = ball;
					else
						b = robot.getLocation();
					if (opponent != null)
						vec = planner.update(robot, opponent, b, true);
					else
						vec = planner.update(robot,
								new Pos(new Point(0, 0), 0), b, true);
					writer.write(String.valueOf(x) + "," + String.valueOf(y)
							+ "," + String.valueOf(vec.getX()) + ","
							+ String.valueOf(vec.getY()) + "\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
