/**
 * Created by Дзюбка on 16.03.2015.
 */
public class EcoSystem {
    private Animal[] animals;
    private int animalNum;

    public EcoSystem(int maxAnimalsCount) {
        this.animals = new Animal[maxAnimalsCount];
        this.animalNum = 0;
    }

    public boolean addAnimal(AnimalType animalType) {
        if (animalNum < animals.length) {
            animals[animalNum] = new Animal(animalType);
            animalNum++;
            return true;
        }

        return false;
    }

    public boolean addAnimal(Animal animal) {
        if (animalNum < animals.length) {
            animals[animalNum] = animal;
            animalNum++;
            return true;
        }

        return false;
    }

}
