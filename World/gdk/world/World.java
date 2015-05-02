package gdk.world;

import gdk.land.Landscape;
import gdk.animal.Animal ;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;

public class World {
    ArrayList<Animal> animals;
    Landscape landscape;

    public void tick() {

    }

    public void generate(int width, int height, int animalsCount) {
        animals = new ArrayList<Animal>();
        for (int i = 0; i < animalsCount; i++) {
            animals.add(new Animal(
                    new Point(width, height)
            ));
        }

        landscape = new Landscape();
        landscape.generateNew();
        landscape.normalizeHeight();
        landscape.averageHeight();
    }
}