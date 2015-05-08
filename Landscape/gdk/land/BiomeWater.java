package com.vova.land;

import java.awt.*;

public class BiomeWater extends Biome {
    BiomeWater() {
        BIOME_ID = Biome.BIOME_WATER ;

        scaleRateMin = 0.05 ;
        scaleRateMax = 0.1 ;
        temperatureMedian = new LandTemperature(10) ;
        fertilityMedian = 0.1 ;
        humidityMedian = 0.9 ;
        medianCrop = 0.2 ;
        maxCrop = 0.3 ;
    }

    BiomeWater( Biome biome ) {
        this() ;
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE ;
        COLD = biome.COLD ;
        DEFAULT_COLOR = new CellColor(Color.BLUE);
    }
}
