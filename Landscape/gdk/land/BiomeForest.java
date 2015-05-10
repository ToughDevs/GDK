package gdk.land;

import java.awt.*;

public class BiomeForest extends Biome {
    BiomeForest() {
        BIOME_ID = Biome.BIOME_FOREST ;

        scaleRateMin = 0.2 ;
        scaleRateMax = 0.25 ;
        temperatureMedian = new CellTemperature(25) ;
        fertilityMedian = 0.6 ;
        humidityMedian = 0.6 ;
        medianCrop = 0.4 ;
        maxCrop = 0.8 ;
        DEFAULT_COLOR = (new CellColor(Color.GREEN)).add(new CellColor(Color.DARK_GRAY));
    }

    BiomeForest( Biome biome ) {
        this();
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE;
        COLD = biome.COLD;
    }
}
