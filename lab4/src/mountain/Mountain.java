package mountain;

import fractal.Fractal;
import fractal.TurtleGraphics;

public class Mountain extends Fractal {
	
	private int length;
	
	public Mountain(int length) {
		super();
		this.length = length;
	}

	@Override
	public String getTitle() {
		return "Mountain haha!!!";
	}

	@Override
	public void draw(TurtleGraphics turtle) {
		Point p1 = new Point(200,11);
		Point p2 = new Point(450, 450);
		Point p3 = new Point(10,450);
				
		fractalLine(turtle,order,p1,p2,p3);
		
	}	
	
	/** recursice method: draw a triangle*/
	private void fractalLine(TurtleGraphics turtle, int order, Point p1, Point p2, Point p3) {
		if(order ==0) {
			turtle.penDown();
			turtle.moveTo(p1.getX(),p1.getY());
			turtle.forwardTo(p2.getX(),p2.getY());
			turtle.forwardTo(p3.getX(),p3.getY());
			turtle.forwardTo(p1.getX(),p1.getY());
		} else {
			Point m1 = new Point(
					p2.getX() + (p1.getX() - p2.getX()) / 2,
					p2.getY() + (p1.getY() - p2.getY()) / 2
					);
			Point m2 = new Point(
					p3.getX() + (p2.getX() - p3.getX()) / 2,
					p3.getY() + (p2.getY() - p3.getY()) / 2
					);
			Point m3 = new Point(
					p1.getX() + (p3.getX() - p1.getX()) / 2,
					p1.getY() + (p3.getY() - p1.getY()) / 2
					);
			
			
			fractalLine(turtle,order-1,m1,m2,m3);
			fractalLine(turtle,order-1,p1,m1,m3);
			fractalLine(turtle,order-1,p2,m1,m2);
			fractalLine(turtle,order-1,p3,m2,m3);
		}
	}
}
