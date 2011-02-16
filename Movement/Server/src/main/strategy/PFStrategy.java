package main.strategy;

import main.Strategy;
import main.data.Location;

import main.strategy.pFStrategy.*;

public class PFStrategy extends AbstractStrategy implements Strategy {

	private PFPlanning planner;
	VelocityVec current;
	public PFStrategy(double b,double r)
	{
		RobotConf conf=new RobotConf(b, r);
		planner=new PFPlanning(conf,5*100000.0,Double.MAX_VALUE,0.05,10);
		current=new VelocityVec(0, 0);
		
	}
	//updateLocation called in each cycle with fresh location updates.
	public void updateLocation(Location data) {
		//putting new data into data structures.
		Pos current= new Pos(new Point(data.getRobotA().getX(), data.getRobotA().getY()), data.getRobotA().getT());
		Pos opponent= new Pos(new Point(data.getRobotB().getX(), data.getRobotB().getY()), data.getRobotB().getT());
		Point ball=new Point(data.getBall().getX(),data.getBall().getY());
		//getting new velocity vectors
		VelocityVec vector= planner.update(current,opponent, ball, false, false);
		//Converting Radians/sec to Degrees/sec
		int left = (int) Math.toDegrees(vector.getLeft());
		int right = (int) Math.toDegrees(vector.getRight());
		//Caps for produced velocities, This has to be removed once the algorithm is tuned.
		this.current=vector;
		//if (left < 0 && Math.abs(left - right)>400){
		//	this.current = new VelocityVec(0, this.current.getRight());
		//}
		//if ( right < 0 && Math.abs(left - right)>400){
		//	this.current = new VelocityVec(this.current.getLeft(), 0);
		//}
//		int max_speed=10;
//		if ( Math.abs(left) > max_speed ){
//			right=(int)(Math.signum(right)*(max_speed*Math.abs(right)/Math.abs(left)));
//			left=(int) (Math.signum(max_speed)*max_speed);
//			this.current = new VelocityVec(Math.toRadians(left), Math.toRadians(right));
//		}
//		if ( Math.abs(right) > max_speed ){
//			left=(int)(Math.signum(right)*(max_speed*Math.abs(left)/Math.abs(right)));
//			right=(int) Math.signum(max_speed)*max_speed;
//			this.current = new VelocityVec(Math.toRadians(left), Math.toRadians(right));
//		}
//		vector = this.current;
		//Applying  produced velocities to the executer.
		executor.rotateWheels(data.getRobotA(),(int) Math.toDegrees(vector.getLeft()), (int) Math.toDegrees(vector.getRight()));
		
		}

	@Override
	public VelocityVec getVelocity() {
		// TODO Auto-generated method stub
		return current;
	}
	

}
