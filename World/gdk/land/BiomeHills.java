package land;

import java.awt.*;

public class BiomeHills extends Biome {
    BiomeHills() {
        BIOME_ID = Biome.BIOME_HILLS ;

        scaleRateMin = 0.8 ;
        scaleRateMax = 0.9 ;
        temperatureMedian = new CellTemperature(20) ;
        fertilityMedian = 0.4 ;
        humidityMedian = 0.2 ;
        medianCrop = 0.3 ;
        maxCrop = 0.7 ;
        DEFAULT_COLOR = (new CellColor(Color.GREEN)).add(new CellColor(Color.LIGHT_GRAY));
    }

    BiomeHills( Biome biome ) {
        this() ;
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE ;
        COLD = biome.COLD ;
    }
}
