package gdk.land;

public class Biome {
    public static final int SUBSTANCE_LAND = 0 ;
    public static final int SUBSTANCE_WATER = 1 ;
    public int SUBSTANCE_TYPE ;
    public boolean COLD ;

    public int BIOME_ID ;
    public CellColor DEFAULT_COLOR ;

    public double scaleRateMin ; // 0 for bottom of the river
    public double scaleRateMax ; // 1 for mountain peak
    public CellTemperature temperatureMedian ;
    public double fertilityMedian ;
    public double humidityMedian ;
    public double medianCrop ;
    public double maxCrop ;

    public static final int BIOME_WATER = 0 ;
    public static final int BIOME_DESERT = 1 ;
    public static final int BIOME_PLAINS = 2 ;
    public static final int BIOME_HILLS = 3 ;
    public static final int BIOME_FOREST = 4 ;
    public static final int BIOME_JUNGLE = 5 ;
    public static final int BIOME_TAIGA= 6 ;

    public static BiomeWater biomeWater = new BiomeWater() ;
    public static BiomeDesert biomeDesert = new BiomeDesert() ;
    public static BiomePlains biomePlains = new BiomePlains() ;
    public static BiomeHills biomeHills = new BiomeHills() ;
    public static BiomeForest biomeForest = new BiomeForest() ;

    public static final int[] NORMAL_BIOME_LIST = new int[] {BIOME_DESERT, BIOME_PLAINS, BIOME_HILLS, BIOME_FOREST} ;
    public static final int[] FROZEN_BIOME_LIST = new int[] {BIOME_PLAINS, BIOME_HILLS} ;

    Biome() {
        SUBSTANCE_TYPE = SUBSTANCE_LAND ;
        COLD = false ;
    }

    public static Biome getBiomeById( int ID ) {
        if( ID == BIOME_WATER )
            return biomeWater ;
        else if( ID == BIOME_DESERT )
            return biomeDesert ;
        else if( ID == BIOME_PLAINS )
            return biomePlains ;
        else if( ID == BIOME_HILLS )
            return biomeHills ;
        else if( ID == BIOME_FOREST )
            return biomeForest ;
        return biomeWater ;
    }
}
