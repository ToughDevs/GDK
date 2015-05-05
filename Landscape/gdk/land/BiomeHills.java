package gdk.land;

public class BiomeHills extends Biome {
    BiomeHills() {
        BIOME_ID = Biome.BIOME_HILLS ;

        scaleRateMin = 0.5 ;
        scaleRateMax = 0.8 ;
        temperatureMedian = new LandTemperature(20) ;
        fertilityMedian = 0.4 ;
        humidityMedian = 0.2 ;
        medianCrop = 0.3 ;
        maxCrop = 0.7 ;
    }

    BiomeHills( Biome biome ) {
        this() ;
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE ;
        COLD = biome.COLD ;
    }
}
