package land;

public class CellTemperature {
    public static final double LOWEST = -10 ; // ice
    public static final double HIGHEST = 100 ; // vapor

    private double c ;

    CellTemperature(double c) {
        this.c = c ;
    }

    public double getTemperature() {
        return c ;
    }
}
