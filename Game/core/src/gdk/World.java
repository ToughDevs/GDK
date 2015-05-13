package gdk;

import gdk.land.Land;
import gdk.animal.Animal;

import java.util.Random;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;

public class World {
    public ArrayList<Animal> animals;
    public Land landscape;
    int mapSize;
    Random random;

    public void update() {
        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).coords.x += animals.get(i).speed.x;
            animals.get(i).coords.y += animals.get(i).speed.y;
            if (animals.get(i).coords.x < 0) {
                animals.get(i).coords.x = 0;
                animals.get(i).speed.x *= -1;
            }
            if (animals.get(i).coords.x > mapSize - 1) {
                animals.get(i).coords.x = mapSize - 1;
                animals.get(i).speed.x *= -1;
            }
            if (animals.get(i).coords.y < 0) {
                animals.get(i).coords.y = 0;
                animals.get(i).speed.y *= -1;
            }
            if (animals.get(i).coords.y > mapSize - 1) {
                animals.get(i).coords.y = mapSize - 1;
                animals.get(i).speed.y *= -1;
            }
            if (random.nextFloat() < 0.1) {
                animals.get(i).speed.x = random.nextInt(3) - 1;
                animals.get(i).speed.y = random.nextInt(3) - 1;
            }
        }
    } 

    public void generateNew(int biomesCount, int biomeSize, int animalsCount) {
        random = new Random();

        landscape = new Land(biomeSize, biomesCount);
        landscape.setBiomeMutationIterations(100);
        landscape.generateNew();

        mapSize = biomeSize * biomesCount ;

        animals = new ArrayList<Animal>();
        for (int i = 0; i < animalsCount; i++) {
            animals.add(new Animal(
                    new Point(0, 0),
                    new Point(landscape.getDepth()-1, landscape.getWidth()-1),
                    new Point(random.nextInt(3) - 1, random.nextInt(3) - 1)
            ));
        }
    }
}