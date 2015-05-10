package animal;

import java.awt.Point;
import java.util.Random;

public class Animal {
    public Point coords;

    public Animal(Point maxCoords) {
        Random random = new Random();
        this.coords = new Point(random.nextInt((int)maxCoords.getX()), random.nextInt((int)maxCoords.getY()));
    }

    public Animal(Point minCoords, Point maxCoords) {
        Random random = new Random();
        this.coords = new Point((int)minCoords.getX() +
                random.nextInt((int)maxCoords.getX() - (int)minCoords.getX()),
                (int)minCoords.getY() +
                        random.nextInt((int)maxCoords.getY() -(int) minCoords.getY()));
    }
}
