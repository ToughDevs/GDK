package gdk.land;

public class LandTemperature {
    public static final double LOWEST = -10 ; // ice
    public static final double HIGHEST = 100 ; // vapor

    private double c ;

    LandTemperature(double c) {
        this.c = c ;
    }

    public double getTemperature() {
        return c ;
    }
}
