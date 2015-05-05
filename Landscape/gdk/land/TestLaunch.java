package gdk.land;

public class TestLaunch {
    public static void main(String args[]) {
        Landscape landscape = new Landscape();
        landscape.generateNew();
        //landscape.normalizeHeight(0, 0, landscape.getWidth(), landscape.getDepth());
        //landscape.averageHeight();

        for (int i = 0; i < landscape.getWidth(); ++i) {
            for (int j = 0; j < landscape.getDepth(); ++j)
                System.out.printf("% 06.2f ", landscape.getCellHeight(i, j));
            System.out.print("\n");
        }

        for(int i = 0 ; i < landscape.BIOME_MAP_SIZE ; ++i ) {
            for (int j = 0; j < landscape.BIOME_MAP_SIZE; ++j)
                System.out.print(landscape.BiomeMap[i][j].BIOME_ID);
            System.out.println();
        }
    }
}
