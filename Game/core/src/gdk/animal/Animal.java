package gdk.animal;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

public class Animal {
    public Point2D.Float coords;
    public Point2D.Float speed;
    public double energy ; // need to live and to perform any action
    public double power ; // need to move
    public double digestionCoeff ; // how efficiently food is absorbed - [0.3;0.9]
    public double entityEnergy ; // energy spent each time unit - [0.05;0.1]
    public double moveEnergy ; // energy need to move for 1 c.u. ~ [0.1;5]

    public Animal(Point2D.Float minCoords, Point2D.Float maxCoords, Point2D.Float speed) {
        Random random = new Random();
        this.energy = 1 + Math.random() * 4 ;
        this.power = 1 + Math.random() * 4 ;
        this.digestionCoeff = 0.3 + Math.random() * 0.6 ;
        this.entityEnergy = 0.05 + Math.random() * 0.05 ;
        this.moveEnergy = 0.1 + Math.random() * 4.9 ;

        this.speed = speed;
        this.coords = new Point2D.Float(
                minCoords.x + random.nextFloat() * (maxCoords.x - minCoords.x),
                minCoords.y + random.nextFloat() * (maxCoords.y - minCoords.y)
        );
    }
}
