package gdk.land;

import java.awt.*;

public class BiomeForest extends Biome {
    BiomeForest() {
        BIOME_ID = Biome.BIOME_FOREST ;

        scaleRateMin = 0.20 ;
        scaleRateMax = 0.25 ;
        temperatureMedian = new CellTemperature(25) ;
        fertilityMedian = 0.6 ;
        humidityMedian = 0.6 ;
        virulenceMedian = 0.1 ;
        vegetationMedian = 0.5 ;
        DEFAULT_COLOR = (new CellColor(Color.GREEN)).add(new CellColor(Color.DARK_GRAY));
    }

    BiomeForest( Biome biome ) {
        this();
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE;
        COLD = biome.COLD;
    }
}
