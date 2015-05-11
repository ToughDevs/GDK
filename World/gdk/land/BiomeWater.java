package land;

import java.awt.*;

public class BiomeWater extends Biome {
    BiomeWater() {
        BIOME_ID = Biome.BIOME_WATER ;

        scaleRateMin = 0.05 ;
        scaleRateMax = 0.1 ;
        temperatureMedian = new CellTemperature(10) ;
        fertilityMedian = 0.1 ;
        humidityMedian = 0.9 ;
        medianCrop = 0.2 ;
        maxCrop = 0.3 ;
        DEFAULT_COLOR = new CellColor(Color.BLUE);
    }

    BiomeWater( Biome biome ) {
        this() ;
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE ;
        COLD = biome.COLD ;
    }
}
