package gdk;

import gdk.land.Land;
import gdk.animal.Animal;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;

public class World {
    ArrayList<Animal> animals;
    Land land;

    public void tick() {

    }

    public void generate(int biomesCount, int biomeSize, int animalsCount) {
        animals = new ArrayList<Animal>();
        for (int i = 0; i < animalsCount; i++) {
            animals.add(new Animal(
                    new Point(width, height)
            ));
        }

        land = new Land();
        land.BIOME_SIZE = biomeSize;
        land.BIOME_MAP_SIZE = biomeSize;

        land.generateNew();
        land.normalizeHeight();
        land.averageHeight();
    }
}