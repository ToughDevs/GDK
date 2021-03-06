package gdk;

import gdk.animal.Animal;
import gdk.land.Land;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class World {
    public ArrayList<Animal> animals;
    public Land landscape;
    int mapSize;
    Random random;
    LocalDateTime lastUpdate ;

    public void update() {
        if( lastUpdate.until(LocalDateTime.now(), ChronoUnit.MILLIS) > 40 ) {
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
        double dx ,dy, d, dr2;
        double px , py;
        double mx, my;
        for (Animal animal : animals) {
            double chToRun = 0;
            mx = 0;
            my = 0;
            px = animal.coords.x;
            py = animal.coords.y;

            for (Animal secAnimal : animals) {
                if (animal != secAnimal) {
                    dx = secAnimal.coords.x - px;
                    dy = secAnimal.coords.y - py;
                    dr2 = dx*dx + dy*dy;
                    if (dr2 == 0.0) {
                        dx = 0.01;
                        dy = 0.01;
                    }
                    if (animal.vision * animal.vision >= dr2) {
                        switch (secAnimal.foodType) {
                            case MEAT:
                                switch (animal.foodType) {
                                    case GRASS: {
                                        double fear = animal.fearness / dr2;
                                        chToRun += fear;
                                        mx -= fear * dx;
                                        my -= fear * dy;
                                    } break;
                                    case MEAT: {
                                        double diff = animal.diffusion / dr2;
                                        chToRun += diff;
                                        mx -= diff * dx;
                                        my -= diff * dy;
                                    } break;
                                }
                                break;
                            case GRASS: {
                                switch (animal.foodType) {
                                    case GRASS: {
                                        double diff = animal.diffusion / dr2;
                                        chToRun += diff;
                                        mx -= diff * dx;
                                        my -= diff * dy;
                                    } break;
                                    case MEAT: {
                                        double rap = animal.rapacity / dr2;
                                        chToRun += rap;
                                        mx += rap * dx;
                                        my += rap * dy;
                                    } break;
                                }
                            } break;
                        }
                    }
                }
            }
            double chToStay = animal.energySaving *animal.entityEnergy / animal.energy;
            double decision = Math.random()*(chToRun + chToStay);
            if (decision < chToRun) {
                double mr = Math.sqrt(mx*mx+my*my);
                double nx = px + mx/mr*animal.maxSpeed;
                double ny = py + my/mr*animal.maxSpeed;
                if (nx < 1) {
                    nx = 1;
                }
                if (nx >= mapSize-1) {
                    nx = mapSize-1;
                }
                if (ny < 1) {
                    ny = 1;
                }
                if (ny >= mapSize-1) {
                    ny = mapSize - 1;
                }
                double nr2 = Math.sqrt((nx-px)*(nx-px) + (ny-py)* (ny-py));
                animal.energy -= nr2*animal.moveEnergy;
                animal.coords.x = (float)nx;
                animal.coords.y = (float)ny;
            } else {
                double nx = px + (0.5 - Math.random() ) * Math.random() * 2;
                double ny = py + (0.5 - Math.random() ) * Math.random() * 2;
                if (nx < 1) {
                    nx = 1;
                }
                if (nx >= mapSize-1) {
                    nx = mapSize-1;
                }
                if (ny < 1) {
                    ny = 1;
                }
                if (ny >= mapSize-1) {
                    ny = mapSize - 1;
                }
                animal.coords.x = (float)nx;
                animal.coords.y = (float)ny;
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
            Animal.AnimalStandartType tp;
            if (Math.random() < 0.5) {
                tp = Animal.AnimalStandartType.COW;
            } else {
                tp = Animal.AnimalStandartType.TIGER;
            }
            animals.add(new Animal(
                            new Point2D.Float(0, 0),
                            new Point2D.Float(landscape.getDepth()-1, landscape.getWidth()-1),
                            tp)
            );
        }

        lastUpdate = LocalDateTime.now() ;
    }
}