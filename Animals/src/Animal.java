/**
 * Created by Дзюбка on 15.03.2015.
 */
public class Animal {
    private Coords2d coords;        // animal coordinates
    private boolean hasTarget;
    private Coords2d target;        // animal target coordinates
    private double age;             // age of animal
    private AnimalType animalType;  // class of animal

    public Animal(AnimalType animalType) {
        this.animalType = animalType;
        this.hasTarget = false;
    }

    public Animal setCoords(Coords2d coords) {
        this.coords = coords;
        return this;
    }

    public Animal setCoords(double x, double y) {
        this.coords.x = x;
        this.coords.y = y;
        return this;
    }

    public Coords2d getCoords() {
        return this.coords;
    }

    /**
     *
     * @param dx delta x
     * @param dy delta y
     * @return this animal
     */

    public Animal move(double dx, double dy) {
        this.coords.x += dx;
        this.coords.y += dy;
        return this;
    }

    /**
     *
     * @param angle in radians
     * @param distance distance that animal goes
     * @return this animal
     */
    public Animal moveWithAngle(double angle, double distance) {
        this.coords.x += distance * Math.cos(angle);
        this.coords.y += distance * Math.sin(angle);
        return this;
    }

    public Animal setTarget(double x, double y) {
        this.hasTarget = true;
        this.target.x = x;
        this.target.y = y;
        return this;
    }

    public Animal setTarget(Coords2d coords) {
        this.hasTarget = true;
        this.target = coords;
        return this;
    }

}