package gdk.land;

public class Biome {
    public static final int SUBSTANCE_LAND = 0 ;
    public static final int SUBSTANCE_WATER = 1 ;
    public int SUBSTANCE_TYPE ;
    public boolean COLD ;

    public int BIOME_ID ;

    public double scaleRateMin ; // 0 for bottom of the river
    public double scaleRateMax ; // 1 for mountain peak
    public LandTemperature temperatureMedian ;
    public double fertilityMedian ;
    public double humidityMedian ;
    public double medianCrop ;
    public double maxCrop ;

    public static final int BIOME_WATER = 0 ;
    public static final int BIOME_DESERT = 1 ;
    public static final int BIOME_JUNGLE = 2 ;
    public static final int BIOME_PLAINS = 3 ;
    public static final int BIOME_HILLS = 4 ;
    public static final int BIOME_FOREST = 5 ;

    public static final int[] NORMAL_BIOME_LIST = new int[] {1, 2, 3, 4, 5} ;
    public static final int[] FROZEN_BIOME_LIST = new int[] {3, 4} ;

    Biome() {
        SUBSTANCE_TYPE = SUBSTANCE_LAND ;
        COLD = false ;
    }
}
