/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TurtleSoup {
	/**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	if(sideLength<=0) {
    		throw new RuntimeException("implement me!");
    	}
    	turtle.forward(sideLength);
    	turtle.turn(90);//每一次旋转90度即可
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	
//    	turtle.turn(30);
//    	turtle.forward(sideLength);
//    	turtle.turn(300);
//    	turtle.forward(sideLength);
    	
    	
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        if(sides<=2) {
        	throw new RuntimeException("implement me!");
        }
        return (double)((sides-2)*180)/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	return (int) Math.round(360/(180-angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        if(sides<=2) {
        	throw new RuntimeException("implement me!");
        }
        turtle.turn(90);
        for(int i = 0;i<sides;i++) {
        	turtle.forward(sideLength);
            turtle.turn(180+calculateRegularPolygonAngle(sides));
        }
    	
    	
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	if(targetY-currentY==0 && targetX-currentX==0) {
    		return 0;
    	}
    	double angle=0;
    	double targetBearing=0;
    	int X= targetX-currentX;
    	int Y= targetY-currentY;
    	double distance =Math.sqrt(Math.pow(X,2)+Math.pow(Y,2));
    	currentBearing = 90-currentBearing >=0  ? 90-currentBearing : 360+90-currentBearing;
    	if(targetY-currentY>=0) {
    		targetBearing = Math.acos(X/distance)*180/Math.PI;
    	}
    	else{
    		targetBearing = Math.acos(X/distance)*180/Math.PI+180;
    	}
    	angle = currentBearing-targetBearing >=0 ? currentBearing-targetBearing :currentBearing-targetBearing+360;
    	return angle;
	}
    
    public static double calculateBearingToPoint(double currentBearing, double currentX, double currentY,
            double targetX, double targetY) {
		if(targetY-currentY==0 && targetX-currentX==0) {
			return 0;
		}
		double angle=0;
		double targetBearing=0;
		double X= targetX-currentX;
		double Y= targetY-currentY;
		double distance =Math.sqrt(Math.pow(X,2)+Math.pow(Y,2));
		currentBearing = 90-currentBearing >=0  ? 90-currentBearing : 360+90-currentBearing;
		if(targetY-currentY>=0) {
			targetBearing = Math.acos(X/distance)*180/Math.PI;
		}
		else{
			targetBearing = Math.acos(X/distance)*180/Math.PI+2*(180-Math.acos(X/distance)*180/Math.PI);
		}
		angle = currentBearing-targetBearing >=0 ? currentBearing-targetBearing :currentBearing-targetBearing+360;
		return angle;
    }	

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> angles = new ArrayList<>();
    	if(xCoords.size()!=yCoords.size()) {
    		return null;
    	}
    	int length = xCoords.size();
    	angles.add(calculateBearingToPoint(0, xCoords.get(0), yCoords.get(0), xCoords.get(1), yCoords.get(1)));
    	for(int i=1;i<length-1;i++) {
    		angles.add(calculateBearingToPoint(angles.get(i-1), xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1)));       	
    	}
        return angles;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	if(points.isEmpty() || points.size()==1) {
    		return points;
    	}
    	Set<Point> ConvexPoints = new HashSet<Point>();
    	Point PresentPoint = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    	Point FirstPoint = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    	Point NextPoint =new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    	double min=Double.POSITIVE_INFINITY;
    	double tangle=0;
    	double tempt=0;
    	for(Point k : points) {
    		if(k.x()<min || (k.x()==min && k.y()<PresentPoint.y())) {
    			PresentPoint=k;
    			min=k.x();
    		}
    	}
    	FirstPoint=PresentPoint;
    	ConvexPoints.add(FirstPoint);
    	while(!NextPoint.equals(FirstPoint)) {
    		min=380;
    		for(Point k : points) {
    			tempt=calculateBearingToPoint(tangle,PresentPoint.x(),PresentPoint.y(),k.x(),k.y());
    			if(!k.equals(PresentPoint)) {
    				if( tempt<min   ||   (tempt==min && k.x()>NextPoint.x()) || (tempt==min && k.y()>NextPoint.y())) {
        				min=tempt;
        				NextPoint=k;
        			}
    			}
    		}
    		tangle=tangle+min;
    		PresentPoint=NextPoint;
    		if(NextPoint.x()<Double.POSITIVE_INFINITY) {
    			ConvexPoints.add(NextPoint);
    			System.out.println(NextPoint.x()+","+NextPoint.y());
    		}
    	}
        return ConvexPoints;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        turtle.color(PenColor.PINK);
        int length=100;
        turtle.forward(length);
        turtle.turn(180);
        turtle.forward(length/2);
        turtle.turn(270);
        turtle.forward(length/2);
        turtle.turn(270);
        turtle.forward(length/2);
        turtle.turn(180);
        turtle.forward(length);
        turtle.turn(270);
        turtle.color(PenColor.GREEN);
        turtle.forward(10);
        turtle.color(PenColor.BLUE);
        turtle.forward(length/2);
        turtle.turn(270);
        turtle.forward(length);
        turtle.turn(270);
        turtle.forward(length/2);
        turtle.turn(180);
        turtle.forward(length);
        turtle.turn(180);
        turtle.forward(length/2);
        turtle.turn(270);
        
        turtle.forward(length);
        turtle.turn(270);
        turtle.forward(length/2);
        turtle.color(PenColor.GREEN);
        turtle.forward(10);
        turtle.forward(length/2);
        turtle.color(PenColor.GRAY);
        turtle.turn(270);
        turtle.forward(length);
        turtle.turn(270);
        turtle.forward(length/2);
        turtle.turn(180);
        turtle.forward(length);
        turtle.draw();
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        drawPersonalArt(turtle);
        calculateBearingToPoint(1.0,4,5,4,6);
        HashSet<Point> a= new HashSet<Point>();
        HashSet<Point> b= new HashSet<Point>();
		Point p110 = new Point(1, 10);
		Point p11 = new Point(1, 1);
		Point p1010 = new Point(10, 10);
		Point p12 = new Point(1, 2);
		a.add(p11);
		a.add(p110);
		a.add(p1010);
		a.add(p12);
		
		System.out.println(b.size());
		System.out.println(calculateBearingToPoint(90,10,10,1,1));
		System.out.println(calculateBearingToPoint(90,10,10,1,2));
		System.out.println(calculateBearingToPoint(0,1,1,1,10));
		for(Point k : b) {
			System.out.println(k.x()+","+k.y());
		}

		System.out.println(calculatePolygonSidesFromAngle(60));
//		System.out.println(b.size());
        // draw the window
        turtle.draw();
    }

}
