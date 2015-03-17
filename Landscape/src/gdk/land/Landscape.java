package gdk.land;

public class Landscape {
    /**
     * Test launch
     */
    public static void main(String args[]) {
        Landscape landscape = new Landscape();
        landscape.generateNew();

        for (int i = 1; i <= LAND_W; ++i) {
            for (int j = 1; j <= LAND_D; ++j)
                System.out.printf("% 06.2f ", landscape.HeightMap[i][j]);
            System.out.print("\n");
        }
    }

    final static int LAND_W = 32 + 1; // land width
    final static int LAND_D = 32 + 1; // land depth

    private double[][] HeightMap = new double[LAND_W + 1][LAND_D + 1];

    public Landscape() {

    }

    public double getPointHeight(int x, int y) {
        return HeightMap[x][y];
    }

    public void generateNew() {
        diamondSquareGen(1, 1, LAND_W, LAND_D);
    }

    /**
     * Diamond-square algorithm implementation
     */

    private final double diamondHeightStep = 10.0d;

    /**
     * Generates rectangular height map with corners in (xStart,yStart), (xFinish,yFinish)
     */

    private void diamondSquareGen(int xStart, int yStart, int xFinish, int yFinish) {
        HeightMap[xStart][yStart] = mRandom(-1, 1) * diamondHeightStep;
        HeightMap[xStart][yFinish] = mRandom(-1, 1) * diamondHeightStep;
        HeightMap[xFinish][yStart] = mRandom(-1, 1) * diamondHeightStep;
        HeightMap[xFinish][yFinish] = mRandom(-1, 1) * diamondHeightStep;
        diamondSquareBlockGen(xStart, yStart, xFinish, yFinish);
    }

    private void diamondSquareBlockGen(int xStart, int yStart, int xFinish, int yFinish) {
        if (xStart >= xFinish - 1 && yStart >= yFinish - 1)
            return;
        int xMiddle = (xStart + xFinish) / 2, yMiddle = (yStart + yFinish) / 2;

        HeightMap[xStart][yMiddle] = (HeightMap[xStart][yStart] + HeightMap[xStart][yFinish]) / 2;
        HeightMap[xFinish][yMiddle] = (HeightMap[xFinish][yStart] + HeightMap[xFinish][yFinish]) / 2;
        HeightMap[xMiddle][yStart] = (HeightMap[xStart][yStart] + HeightMap[xFinish][yStart]) / 2;
        HeightMap[xMiddle][yFinish] = (HeightMap[xStart][yFinish] + HeightMap[xFinish][yFinish]) / 2;

        HeightMap[xMiddle][yMiddle] = (
                HeightMap[xStart][yMiddle]
                        + HeightMap[xFinish][yMiddle]
                        + HeightMap[xMiddle][yStart]
                        + HeightMap[xMiddle][yFinish]) / 4
                + mRandom(-1, 1) * diamondHeightStep;

        diamondSquareBlockGen(xStart, yStart, xMiddle, yMiddle);
        diamondSquareBlockGen(xMiddle, yStart, xFinish, yMiddle);
        diamondSquareBlockGen(xStart, yMiddle, xMiddle, yFinish);
        diamondSquareBlockGen(xMiddle, yMiddle, xFinish, yFinish);
    }

    /**
     * Accessory functions
     */

    private double mRandom(double from, double to) {
        if (from > to)
            return 0;
        return from + Math.random() * (to - from);
    }
}
