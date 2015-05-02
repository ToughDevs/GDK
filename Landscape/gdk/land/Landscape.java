package gdk.land;

public class Landscape {
    private int LAND_W = 16; // land width
    private int LAND_D = 16; // land depth
    private int LAND_SCALE = 1; // land height scale
    private double[][] HeightMap = new double[LAND_W + 1][LAND_D + 1]; // height map

    public Landscape() {

    }

    public int getWidth() {
        return LAND_W;
    }

    public int getDepth() {
        return LAND_D;
    }

    public void setWidth(int newWidth) {
        LAND_W = newWidth;
    }

    public void setDepth(int newDepth) {
        LAND_D = newDepth;
    }

    public void setSize(int newWidth, int newDepth) {
        setWidth(newWidth);
        setDepth(newDepth);
    }

    public int getScale() {
        return LAND_SCALE;
    }

    public void setScale(int newScale) {
        LAND_SCALE = newScale;
    }

    public double getPointHeight(int x, int y) {
        return HeightMap[x][y];
    }

    public void generateNew() {
        diamondSquareGen(0, 0, getWidth(), getDepth());
    }

    /**
     * Accessory landscape functions
     */
    public void normalizeHeight() {
        double minHeight = 0;
        for (int i = 0; i < getWidth(); ++i)
            for (int j = 0; j < getDepth(); ++j)
                minHeight = Math.min(minHeight, HeightMap[i][j]);
        for (int i = 0; i < getWidth(); ++i)
            for (int j = 0; j < getDepth(); ++j)
                HeightMap[i][j] -= minHeight;

        double maxHeight = 0;
        for (int i = 0; i < getWidth(); ++i)
            for (int j = 0; j < getDepth(); ++j)
                maxHeight = Math.max(maxHeight, HeightMap[i][j]);
        for (int i = 0; i < getWidth(); ++i)
            for (int j = 0; j < getDepth(); ++j)
                HeightMap[i][j] /= maxHeight;
    }

    public void averageHeight() {
        averageHeight(3);
    }

    public void averageHeight(int avgMagnitude) {
        double[][] averageHeightMap = new double[LAND_W + 1][LAND_D + 1];
        for (int i = 0; i < getWidth(); ++i)
            for (int j = 0; j < getDepth(); ++j) {
                averageHeightMap[i][j] += HeightMap[i][j];
                if (i > 1)
                    averageHeightMap[i - 1][j] += HeightMap[i][j];
                if (j > 1)
                    averageHeightMap[i][j - 1] += HeightMap[i][j];
                if (i < getWidth())
                    averageHeightMap[i + 1][j] += HeightMap[i][j];
                if (j < getDepth())
                    averageHeightMap[i][j + 1] += HeightMap[i][j];
            }
        for (int i = 0; i < getWidth(); ++i)
            for (int j = 0; j < getDepth(); ++j)
                HeightMap[i][j] = Math.max(0, HeightMap[i][j] - averageHeightMap[i][j] / (5 * avgMagnitude));
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
        HeightMap[xStart][yFinish - 1] = mRandom(-1, 1) * diamondHeightStep;
        HeightMap[xFinish][yStart] = mRandom(-1, 1) * diamondHeightStep;
        HeightMap[xFinish][yFinish - 1] = mRandom(-1, 1) * diamondHeightStep;
        diamondSquareBlockGen(xStart, yStart, xFinish, yFinish);
    }

    /**
     * Generates squared height map with corners in (xStart,yStart), (xFinish,yFinish)
     */
    private void diamondSquareBlockGen(int xStart, int yStart, int xFinish, int yFinish) {
        if (xFinish - xStart < 3 && yFinish - yStart < 3)
            return;
        int xMiddle = (xStart + xFinish) / 2, yMiddle = (yStart + yFinish) / 2;

        HeightMap[xStart][yMiddle] = (HeightMap[xStart][yStart] + HeightMap[xStart][yFinish - 1]) / 2;
        HeightMap[xFinish-1][yMiddle] = (HeightMap[xFinish-1][yStart] + HeightMap[xFinish-1][yFinish - 1]) / 2;
        HeightMap[xMiddle][yStart] = (HeightMap[xStart][yStart] + HeightMap[xFinish-1][yStart]) / 2;
        HeightMap[xMiddle][yFinish - 1] = (HeightMap[xStart][yFinish - 1] + HeightMap[xFinish-1][yFinish - 1]) / 2;

        HeightMap[xMiddle][yMiddle] = (
                HeightMap[xStart][yMiddle]
                        + HeightMap[xFinish - 1][yMiddle]
                        + HeightMap[xMiddle][yStart]
                        + HeightMap[xMiddle][yFinish - 1]) / 4
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
