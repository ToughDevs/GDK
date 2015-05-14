package gdk;

import gdk.land.Land;
import gdk.animal.Animal;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;

public class World {
    public ArrayList<Animal> animals;
    public Land landscape;
    int mapSize;
    Random random;
    LocalDateTime lastUpdate ;

    public void update() {
        if( lastUpdate.until(LocalDateTime.now(), ChronoUnit.MILLIS) > 20 ) {
            lastUpdate = LocalDateTime.now() ;
            updateAnimals1();
            updateLandscape();
            updateAnimals2();
        }
    }

    private void updateAnimals1() {
        for( int i = 0 ; i < animals.size() ; ++i ) {
            animals.get(i).energy -= animals.get(i).entityEnergy;
            if (animals.get(i).energy <= 0) {
                animals.remove(i);
                --i;
            }
        }
    }

    private void updateAnimals2() {
        double d ;
        for (Animal animal : animals) {
            d = Math.sqrt( animal.speed.x * animal.speed.x + animal.speed.y * animal.speed.y ) ;
            if( d * animal.moveEnergy <= animal.power &&
                    animal.energy > animal.entityEnergy ) {
                animal.moveEnergy -= animal.power ;
                if (animal.coords.x < 0) {
                    animal.coords.x = 0;
                    animal.speed.x *= -1;
                }
                if (animal.coords.x > mapSize - 1) {
                    animal.coords.x = mapSize - 1;
                    animal.speed.x *= -1;
                }
                if (animal.coords.y < 0) {
                    animal.coords.y = 0;
                    animal.speed.y *= -1;
                }
                if (animal.coords.y > mapSize - 1) {
                    animal.coords.y = mapSize - 1;
                    animal.speed.y *= -1;
                }
                if (random.nextFloat() < 0.1) {
                    animal.speed.x = 1 - random.nextInt(9) / 4.0f;
                    animal.speed.y = 1 - random.nextInt(9) / 4.0f;
                }
            }
            else {
                animal.speed = new Point2D.Float(0, 0);
                if (landscape.getCellVegetation((int) animal.coords.x, (int) animal.coords.y) > 0) {

                }
            }
        }
    }

    private void updateLandscape() {
        updateLandscapeFood() ;
    }

    private void updateLandscapeFood() {
        for(int i = 0 ; i < landscape.getDepth() ; ++i)
            for(int j = 0 ; j < landscape.getWidth() ; ++j )
                landscape.setCellVegetation(
                        i, j,
                        Math.min( 5, landscape.getCellVegetation(i, j) + landscape.getCellFertility(i,j) )
                );
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
                    new Point2D.Float(0, 0),
                    new Point2D.Float(landscape.getDepth()-1, landscape.getWidth()-1),
                    new Point2D.Float(1 - random.nextInt(9)/4.0f, 1 - random.nextInt(9)/4.0f)
            ));
        }

        lastUpdate = LocalDateTime.now() ;
    }
}