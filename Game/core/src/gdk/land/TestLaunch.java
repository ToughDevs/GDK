package gdk.land;

public class TestLaunch {
    public static void main(String args[]) {
        Land landscape ;

        /**
         * Creating new landscape
         *
         * By default landscape contains 8x8 biomes each 64x64 cells size
         * but you can change those things by calling another constructor
         * Land( NUMBER_OF_BIOMES, SIZE_OF_BIOME )
         */
        landscape = new Land();

        /**
         * Sets the number of biomes mutation iterations
         * If the number is zero, all biomes will be square-like
         * By default the iterations number is 200
         */
        landscape.setBiomeMutationIterations(10);

        /**
         * Generates new landscape with cell heights from 0.0 to 1.0 inclusive
         */
        landscape.generateNew();

        /**
         * Changes the height borders to [0.0, SCALE]
         */
        landscape.setScale(100); //Now cell heights are from 0 to 100

        for (int i = 0; i < landscape.getWidth(); ++i) {
            for (int j = 0; j < landscape.getDepth(); ++j)
                System.out.printf("% 06.2f ", landscape.getCellHeight(i, j));
            System.out.print("\n");
        }
    }
}
