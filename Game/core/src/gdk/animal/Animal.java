package gdk.animal;

import java.awt.geom.Point2D;

public class Animal {
    public enum AnimalFoodType { GRASS, MEAT }
    public enum AnimalStandartType { COW, TIGER };

    public Point2D.Float coords;
    public Point2D.Float speed;
    public double maxSpeed;
    public double energy ; // need to live and to perform any action
    public double power ; // need to move
    public double digestionCoeff ; // how efficiently food is absorbed - [0.3;0.9]
    public double entityEnergy ; // energy spent each time unit - [0.05;0.1]
    public double moveEnergy ; // energy need to move for 1 c.u. ~ [0.1;5]
    public double climbingEnergy; // energy need to move upon the hills [0.5; 25]
    public AnimalFoodType foodType; 
    public double vision; // distance that shows how far animal can see objects
    public double fearness; // shows how big chance to start escape from enemy
    public double rapacity; // shows how big chance to start attack the prey
    public double diffusion; // shows how big chance to start escape from friend
    public double energySaving; //show how big chance to stay at the same place

    public Animal(Point2D.Float minCoords, Point2D.Float maxCoords) {
        this.energy = 1 + Math.random() * 4 ;
        this.power = 1 + Math.random() * 4 ;
        this.digestionCoeff = 0.3 + Math.random() * 0.6 ;
        this.entityEnergy = 0.05 + Math.random() * 0.05 ;
        this.moveEnergy = 0.1 + Math.random() * 4.9 ;
        this.climbingEnergy = 0.5 + Math.random() * 24.5;

        this.vision = 0.0f;
        this.maxSpeed = 0.0f;
        this.coords = new Point2D.Float(
                minCoords.x + (float) Math.random() * (maxCoords.x - minCoords.x),
                minCoords.y + (float) Math.random() * (maxCoords.y - minCoords.y)
        );
        this.foodType = AnimalFoodType.GRASS;        

    }

    public Animal(Point2D.Float minCoords, Point2D.Float maxCoords, Point2D.Float speed) {
        this(minCoords, maxCoords);
        this.speed = speed;

    }

    public Animal(Point2D.Float minCoords, Point2D.Float maxCoords, Point2D.Float speed, AnimalFoodType aft) {
        this(minCoords, maxCoords, speed);
        this.foodType = aft;
    }

    public Animal(Point2D.Float minCoords, Point2D.Float maxCoords, AnimalStandartType ast) {
        this(minCoords, maxCoords);
        this.vision = 3;
        this.energy = 100;
        switch (ast) {
            case COW: {
                this.foodType = AnimalFoodType.GRASS;
                this.maxSpeed = 0.5;
                this.fearness = 0.8;
                this.diffusion = 0.1;
                this.rapacity = 0.0;
                this.energySaving = 0.4;
                this.entityEnergy = 0.3;
                this.moveEnergy = 0.1;
            } break;
            case TIGER: {
                this.foodType = AnimalFoodType.MEAT;
                this.maxSpeed = 0.6;
                this.fearness = 0.0;
                this.diffusion = 0.4;
                this.rapacity = 0.4;
                this.energySaving = 0.2;
                this.entityEnergy = 0.3;
                this.moveEnergy = 0.1;
            } break;
        }
    }
}
