package src;

import java.awt.Point;
import java.util.Random;

public class EvoAnimal {
	public Point coords;

	public EvoAnimal(Point maxCoords) {
		Random random = new Random();
		this.coords = new Point(random.nextInt((int)maxCoords.getX()), random.nextInt((int)maxCoords.getY()));
	}

	public EvoAnimal(Point minCoords, Point maxCoords) {
		Random random = new Random();
		this.coords = new Point((int)minCoords.getX() +
								random.nextInt((int)maxCoords.getX() - (int)minCoords.getX()),
								(int)minCoords.getY() +
								random.nextInt((int)maxCoords.getY() -(int) minCoords.getY()));
	}
}