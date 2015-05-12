package land;

import java.awt.*;

public class BiomeDesert extends Biome {
    BiomeDesert() {
        BIOME_ID = Biome.BIOME_DESERT ;

        scaleRateMin = 0.2 ;
        scaleRateMax = 0.25 ;
        temperatureMedian = new CellTemperature(40) ;
        fertilityMedian = 0.1 ;
        humidityMedian = 0.1 ;
        medianCrop = 0.2 ;
        maxCrop = 0.6 ;
        DEFAULT_COLOR = new CellColor(Color.ORANGE);
    }

    BiomeDesert( Biome biome ) {
        this();
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE;
        COLD = biome.COLD;
    }
}
