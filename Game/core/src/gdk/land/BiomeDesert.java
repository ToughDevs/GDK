package gdk.land;

import java.awt.*;

public class BiomeDesert extends Biome {
    BiomeDesert() {
        BIOME_ID = Biome.BIOME_DESERT ;

        scaleRateMin = 0.20 ;
        scaleRateMax = 0.25 ;
        temperatureMedian = new CellTemperature(40) ;
        fertilityMedian = 0.1 ;
        humidityMedian = 0.1 ;
        virulenceMedian = 0.01 ;
        vegetationMedian = 0.1 ;
        DEFAULT_COLOR = (new CellColor(Color.WHITE)).add((new CellColor(Color.ORANGE)).add(new CellColor(Color.YELLOW)));
    }

    BiomeDesert( Biome biome ) {
        this();
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE;
        COLD = biome.COLD;
    }
}
