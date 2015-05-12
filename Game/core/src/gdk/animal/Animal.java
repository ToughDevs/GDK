package gdk.animal;

import java.awt.Point;
import java.util.Random;

public class Animal {
    public Point coords;
    public Point speed ;

    public Animal(Point minCoords, Point maxCoords, Point speed) {
        Random random = new Random();
        this.speed = speed;
        this.coords = new Point(
                minCoords.x + random.nextInt(maxCoords.x - minCoords.x),
                minCoords.y + random.nextInt(maxCoords.y - minCoords.y)
        );
    }
}
