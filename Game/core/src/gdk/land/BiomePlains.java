package gdk.land;

public class BiomePlains extends Biome {
    BiomePlains() {
        BIOME_ID = Biome.BIOME_PLAINS ;

        scaleRateMin = 0.20 ;
        scaleRateMax = 0.25 ;
        temperatureMedian = new CellTemperature(25);
        fertilityMedian = 0.5 ;
        humidityMedian = 0.3 ;
        virulenceMedian = 0.02 ;
        vegetationMedian = 0.4 ;
        DEFAULT_COLOR = (new CellColor(80, 150, 80)) ;
    }

    BiomePlains( Biome biome ) {
        this() ;
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE ;
        COLD = biome.COLD ;
    }
}
