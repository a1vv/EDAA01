package mountain;

import java.util.HashMap;

import fractal.Fractal;
import fractal.TurtleGraphics;

public class Mountain extends Fractal {
	
	private double dev;
	private HashMap<Side, Point> map;
	
	public Mountain(int dev) {
		super();
		this.dev = dev;
		this.map = new HashMap<Side, Point>();
	}

	@Override
	public String getTitle() {
		return "Mountain haha!!!";
	}

	@Override
	public void draw(TurtleGraphics turtle) {
		Point p1 = new Point(300,11);
		Point p2 = new Point(450, 400);
		Point p3 = new Point(10,400);
				
		fractalLine(turtle,order,p1,p2,p3,dev);
		
	}	
	
	/** recursice method: draw a triangle*/
	private void fractalLine(TurtleGraphics turtle, int order, Point p1, Point p2, Point p3, double dev) {
		if(order ==0) {
			turtle.moveTo(p1.getX(),p1.getY());
			turtle.forwardTo(p2.getX(),p2.getY());
			turtle.forwardTo(p3.getX(),p3.getY());
			turtle.forwardTo(p1.getX(),p1.getY());
		} else {
			Point m1 = checkSide(p1,p2,dev);
			Point m2 = checkSide(p2,p3,dev);
			Point m3 = checkSide(p3,p1,dev);

			dev = dev/2;
			fractalLine(turtle,order-1,m1,m2,m3,dev);
			fractalLine(turtle,order-1,p1,m1,m3,dev);
			fractalLine(turtle,order-1,p2,m1,m2,dev);
			fractalLine(turtle,order-1,p3,m2,m3,dev);
		}
	}
	/*
	 * if a side with p1 and p2 exists in the map, reuse it (the midpoint) and remove it from the map 
	 * otherwise create a new midpoint and add to map */
	private Point checkSide(Point startPoint, Point endPoint, double dev) {
		Side side = new Side(startPoint,endPoint);
		Point midPoint;
		if(map.containsKey(side)) {
			midPoint = map.get(side);
			map.remove(side);
		} else {
			midPoint = new Point(
					endPoint.getX() + (startPoint.getX() - endPoint.getX()) / 2,
					(int) (endPoint.getY() + (startPoint.getY() - endPoint.getY()) / 2 - RandomUtilities.randFunc(dev))
					);
			map.put(side,midPoint);
		}
		return midPoint;
	}
}
