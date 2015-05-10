package gdk.land;


public class LandCell {
    public CellColor cellColor;
    public int biomeID;
    public CellTemperature cellTemperature;
    public double cellHeight;
    public double depth ; // for water
    public double fertility ;
    public double humidity ;
    public double virulence ;
    public double vegetation ;
    public double freshMeat ;
    public double rottenMeat ;

    LandCell() {
        cellTemperature = new CellTemperature(0);
        cellHeight = 0;
        depth = 0 ;
        fertility = 0 ;
        humidity = 0 ;
        virulence = 0 ;
        vegetation = 0 ;
        freshMeat = 0 ;
        rottenMeat = 0 ;
    }
}
