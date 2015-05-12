package animal;

import java.awt.Point;
import java.util.Random;

public class Animal {
    public Point coords;

    public Animal(Point maxCoords) {
        Random random = new Random();
        this.coords = new Point(random.nextInt((int)maxCoords.x), random.nextInt((int)maxCoords.y));
    }

    public Animal(Point minCoords, Point maxCoords) {
        Random random = new Random();
        this.coords = new Point((int)minCoords.x +
                random.nextInt((int)maxCoords.x - (int)minCoords.x),
                (int)minCoords.y +
                        random.nextInt((int)maxCoords.y -(int) minCoords.y));
    }
}
