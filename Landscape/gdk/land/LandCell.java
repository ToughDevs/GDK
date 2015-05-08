package gdk.land;


public class LandCell {
    public CellColor cellColor;
    public int BiomeID ;
    public LandTemperature cellTemperature;
    public double cellHeight;
    public double depth ; // for water

    LandCell() {
        cellHeight = 0;
    }
}
