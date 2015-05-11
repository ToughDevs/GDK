import land.Land;
import animal.Animal;

import java.util.Random;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;

public class World {
    ArrayList<Animal> animals;
    ArrayList<Point> animalsSpeed;
    Land land;
    int mapSize;
    Random random;

    public void tick() {
        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).coords.x += animalsSpeed.get(i).x;
            animals.get(i).coords.y += animalsSpeed.get(i).y;
            if (animals.get(i).coords.x < 0) {
                animals.get(i).coords.x = 0;
                animalsSpeed.get(i).x *= -1;
            }
            if (animals.get(i).coords.x > mapSize - 1) {
                animals.get(i).coords.x = mapSize - 1;
                animalsSpeed.get(i).x *= -1;
            }
            if (animals.get(i).coords.y < 0) {
                animals.get(i).coords.y = 0;
                animalsSpeed.get(i).y *= -1;
            }
            if (animals.get(i).coords.y > mapSize - 1) {
                animals.get(i).coords.y = mapSize - 1;
                animalsSpeed.get(i).y *= -1;
            }    
            if (random.nextFloat() < 0.1) {
                animalsSpeed.get(i).x = random.nextInt(3) - 1;
                animalsSpeed.get(i).y = random.nextInt(3) - 1;
            }
        }   
    } 

    public void generate(int biomesCount, int biomeSize, int animalsCount) {
        random = new Random();

        mapSize = biomesCount*biomeSize;

        animals = new ArrayList<Animal>();
        for (int i = 0; i < animalsCount; i++) {
            animals.add(new Animal(
                    new Point(mapSize, mapSize)
            ));
            animalsSpeed.add(new Point(
                 random.nextInt(3)-1
                ,random.nextInt(3)-1)
            );
        }

        land = new Land();
        land.BIOME_SIZE = biomeSize;
        land.BIOME_MAP_SIZE = biomeSize;

        land.generateNew();
    }
}