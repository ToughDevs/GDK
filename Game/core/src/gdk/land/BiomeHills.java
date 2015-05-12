package gdk.land;

import java.awt.*;

public class BiomeHills extends Biome {
    BiomeHills() {
        BIOME_ID = Biome.BIOME_HILLS ;

        scaleRateMin = 0.3 ;
        scaleRateMax = 0.5 ;
        temperatureMedian = new CellTemperature(20) ;
        fertilityMedian = 0.4 ;
        humidityMedian = 0.2 ;
        virulenceMedian = 0.02 ;
        vegetationMedian = 0.3 ;
        DEFAULT_COLOR = (new CellColor(Color.GREEN)).add(new CellColor(Color.LIGHT_GRAY));
    }

    BiomeHills( Biome biome ) {
        this() ;
        SUBSTANCE_TYPE = biome.SUBSTANCE_TYPE ;
        COLD = biome.COLD ;
    }
}
