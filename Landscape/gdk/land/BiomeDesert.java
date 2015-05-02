package gdk.land;

public class BiomeDesert extends Biome {
    BiomeDesert() {
        BIOME_ID = Biome.BIOME_DESERT ;

        scaleRateMin = 0.1 ;
        scaleRateMax = 0.15 ;
        temperatureMedian = new LandTemperature(40) ;
        fertilityMedian = 0.1 ;
        humidityMedian = 0.1 ;
        medianCrop = 0.2 ;
        maxCrop = 0.6 ;
    }
}
