package main.strategy;

import main.Strategy;
import main.data.Ball;
import main.data.Location;
import main.data.Robot;
import main.data.VelocityVec;

public class Simple extends AbstractStrategy implements Strategy {
	
	//private variables
	private Robot robot;
	private Robot enemy;
	private Ball ball;
	private Boolean have_ball = false;

	@Override
	public void updateLocation(Location data) {
		//extract coordinates
		ball = data.getBall();
		robot = data.getRobotA();
		enemy = data.getRobotB();
		
		gotoBall(robot, ball);
	}

	/**
	 * Go to a ball
	 * 
	 * @param robot
	 * @param ball
	 */
	private void gotoBall(Robot robot, Ball ball) {
		turnTo(robot, ball.getX(), ball.getY());
		//calculate distance to the ball and use it to calculate the velocity
		double distance = Math.sqrt(robot.getX()*ball.getX()+robot.getY()*ball.getY());
		int velocity = (int) distance%200;
		executor.rotateWheels(robot, velocity, velocity);
	}
	
	/**
	 * Turn to face X,Y
	 * 
	 * @param robot
	 * @param x
	 * @param y
	 */
	private void turnTo(Robot robot, double x, double y) {
		//calculate signed angle
		double angle = Math.atan2(robot.getX()*y - robot.getY()*x, robot.getX()*x + robot.getY()*y);
		
		if (angle > 1 || angle < -1)
			executor.rotate(robot, (int) angle);
	}

	@Override
	public VelocityVec getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

}
