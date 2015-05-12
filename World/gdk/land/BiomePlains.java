package land;

import java.awt.*;

public class BiomePlains extends Biome {
    BiomePlains() {
        BIOME_ID = Biome.BIOME_PLAINS ;

        scaleRateMin = 0.15 ;
        scaleRateMax = 0.25 ;
        temperatureMedian = new CellTemperature(25);
        fertilityMedian = 0.5 ;
        humidityMedian = 0.3 ;
        medianCrop = 0.5 ;
        maxCrop = 0.8 ;
        DEFAULT_COLOR = new CellColor(Color.GREEN);
    }

    BiomePlains( Biome biome ) {
        this() ;
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE ;
        COLD = biome.COLD ;
    }
}
