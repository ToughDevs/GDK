package gdk.land;

import java.awt.*;
import java.util.Random;

public class Land {
    public int BIOME_SIZE; // size of each biome
    public int BIOME_MAP_SIZE; // size of biome map
    private int LAND_W; // land width
    private int LAND_D; // land depth
    private double LAND_SCALE; // land height scale
    public Biome[][] BiomeMap; // biome map
    private LandCell[][] CellMap; // height map

    public Land() {
        BIOME_SIZE = 64;
        BIOME_MAP_SIZE = 8;
        LAND_W = BIOME_SIZE * BIOME_MAP_SIZE;
        LAND_D = BIOME_SIZE * BIOME_MAP_SIZE;
        LAND_SCALE = 1;
        BiomeMap = new Biome[BIOME_MAP_SIZE][BIOME_MAP_SIZE] ;
        for (int i = 0; i < BIOME_MAP_SIZE; ++i)
            for (int j = 0; j < BIOME_MAP_SIZE; ++j)
                BiomeMap[i][j] = new Biome();
        CellMap = new LandCell[LAND_W][LAND_D];
        for (int i = 0; i < LAND_W; ++i)
            for (int j = 0; j < LAND_D; ++j)
                CellMap[i][j] = new LandCell();
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

    public double getHeightScale() {
        return LAND_SCALE;
    }

    public void setScale(int newScale) {
        LAND_SCALE = newScale;
    }

    public double getCellHeight(int x, int y) {
        return CellMap[x][y].cellHeight * LAND_SCALE ;
    }

    public CellColor getCellColor(int x, int y) {
        return CellMap[x][y].cellColor ;
    }

    public void generateNewOld() {
        diamondSquareGen(0, 0, getWidth(), getDepth());
    }

    private double WATER_CELLS_PERCENT = 0.25 ;
    private double COLD_CELLS_PERCENT_MIN = 0.15 ;
    private double COLD_CELLS_PERCENT_MAX = 0.25 ;

    public void generateNew() {
        int water_cells = (int) (WATER_CELLS_PERCENT * BIOME_MAP_SIZE * BIOME_MAP_SIZE);
        int cold_cells = (int) (mRandom(COLD_CELLS_PERCENT_MIN, COLD_CELLS_PERCENT_MAX) * BIOME_MAP_SIZE * BIOME_MAP_SIZE);

        Random random = new Random();

        int i, j, b, all[];

        all = new int[BIOME_MAP_SIZE * BIOME_MAP_SIZE];
        for (i = 0; i < BIOME_MAP_SIZE * BIOME_MAP_SIZE; ++i)
            all[i] = i;
        for (i = BIOME_MAP_SIZE * BIOME_MAP_SIZE - 1; i >= 0; --i) {
            j = random.nextInt(i + 1);
            if (j != i) {
                all[j] ^= all[i];
                all[i] ^= all[j];
                all[j] ^= all[i];
            }
        }
        for (i = 0; i < water_cells; ++i)
            BiomeMap[all[i] / BIOME_MAP_SIZE][all[i] % BIOME_MAP_SIZE].SUBSTANCE_TYPE = Biome.SUBSTANCE_WATER;

        all = new int[BIOME_MAP_SIZE * BIOME_MAP_SIZE];
        for (i = 0; i < BIOME_MAP_SIZE * BIOME_MAP_SIZE; ++i)
            all[i] = i;
        for (i = BIOME_MAP_SIZE * BIOME_MAP_SIZE - 1; i >= 0; --i) {
            j = random.nextInt(i + 1);
            if (j != i) {
                all[j] ^= all[i];
                all[i] ^= all[j];
                all[j] ^= all[i];
            }
        }
        for (i = 0; i < cold_cells; ++i)
            BiomeMap[all[i] / BIOME_MAP_SIZE][all[i] % BIOME_MAP_SIZE].COLD = true;

        for (i = 0; i < BIOME_MAP_SIZE; ++i)
            for (j = 0; j < BIOME_MAP_SIZE; ++j)
                if (BiomeMap[i][j].SUBSTANCE_TYPE == Biome.SUBSTANCE_LAND) {
                    if (!BiomeMap[i][j].COLD)
                        b = Biome.NORMAL_BIOME_LIST[random.nextInt(Biome.NORMAL_BIOME_LIST.length)];
                    else
                        b = Biome.FROZEN_BIOME_LIST[random.nextInt(Biome.FROZEN_BIOME_LIST.length)];
                    if (b == Biome.BIOME_DESERT)
                        BiomeMap[i][j] = new BiomeDesert(BiomeMap[i][j]);
                    else if (b == Biome.BIOME_PLAINS)
                        BiomeMap[i][j] = new BiomePlains(BiomeMap[i][j]);
                    else if (b == Biome.BIOME_HILLS)
                        BiomeMap[i][j] = new BiomeHills(BiomeMap[i][j]);
                    else if (b == Biome.BIOME_FOREST)
                        BiomeMap[i][j] = new BiomeForest(BiomeMap[i][j]);
                }
                else {
                    BiomeMap[i][j] = new BiomeWater(BiomeMap[i][j]);
                }

        diamondSquareGen(0, 0, LAND_W, LAND_D);
        normalizeHeight(0, 0, LAND_W, LAND_D);
        averageHeight(5);

        int bi, bj;
        for (bi = 0; bi < BIOME_MAP_SIZE; ++bi)
            for (bj = 0; bj < BIOME_MAP_SIZE; ++bj) {
                for (i = bi * BIOME_SIZE; i < (bi + 1) * BIOME_SIZE; ++i)
                    for (j = bj * BIOME_SIZE; j < (bj + 1) * BIOME_SIZE; ++j) {
                        CellMap[i][j].cellHeight *= mRandom(BiomeMap[bi][bj].scaleRateMin, BiomeMap[bi][bj].scaleRateMax);
                        CellMap[i][j].cellColor = new CellColor(BiomeMap[bi][bj].DEFAULT_COLOR) ;
                    }
            }

        averageHeight(20);
        averageColor(50) ;
        applyRandomColorMask();
    }

    /**
     * Accessory landscape functions
     */
    public void normalizeHeight(int ws, int ds, int we, int de) {
        double minHeight = 0;
        for (int i = ws; i < we; ++i)
            for (int j = ds; j < de; ++j)
                minHeight = Math.min(minHeight, CellMap[i][j].cellHeight);
        for (int i = ws; i < we; ++i)
            for (int j = ds; j < de; ++j)
                CellMap[i][j].cellHeight -= minHeight;

        double maxHeight = 0;
        for (int i = ws; i < we; ++i)
            for (int j = ds; j < de; ++j)
                maxHeight = Math.max(maxHeight, CellMap[i][j].cellHeight);
        for (int i = ws; i < we; ++i)
            for (int j = ds; j < de; ++j)
                CellMap[i][j].cellHeight /= maxHeight;
    }

    public void applyRandomColorMask() {
        for(int i = 0 ; i < LAND_W ; ++i )
            for( int j = 0 ; j < LAND_D ; ++j )
                CellMap[i][j].cellColor = CellMap[i][j].cellColor.add(
                        new CellColor(
                                new Color((float)(Math.random()*0.2), (float)(Math.random()*0.2), (float)(Math.random()*0.2))
                        )
                ) ;
    }

    public void averageColor(int iterations) {
        CellColor[][] averageColorMap = new CellColor[LAND_W + 1][LAND_D + 1];
        double []neighborAverage = new double[3] ;
        int neighborsCount ;
        while( iterations-- > 0 ) {
            for (int i = 0; i < LAND_W; ++i)
                for (int j = 0; j < LAND_D; ++j) {
                    neighborsCount = 1 ;
                    neighborAverage[0] = CellMap[i][j].cellColor.getRed() / 255.f ;
                    neighborAverage[1] = CellMap[i][j].cellColor.getGreen() / 255.f ;
                    neighborAverage[2] = CellMap[i][j].cellColor.getBlue() / 255.f ;
                    if (i > 0) {
                        neighborAverage[0] += CellMap[i-1][j].cellColor.getRed() / 255.f ;
                        neighborAverage[1] += CellMap[i-1][j].cellColor.getGreen() / 255.f ;
                        neighborAverage[2] += CellMap[i-1][j].cellColor.getBlue() / 255.f ;
                        ++neighborsCount ;
                    }
                    if (j > 0) {
                        neighborAverage[0] += CellMap[i][j-1].cellColor.getRed() / 255.f ;
                        neighborAverage[1] += CellMap[i][j-1].cellColor.getGreen() / 255.f ;
                        neighborAverage[2] += CellMap[i][j-1].cellColor.getBlue() / 255.f ;
                        ++neighborsCount ;
                    }
                    if (i < LAND_W-1) {
                        neighborAverage[0] += CellMap[i+1][j].cellColor.getRed() / 255.f ;
                        neighborAverage[1] += CellMap[i+1][j].cellColor.getGreen() / 255.f ;
                        neighborAverage[2] += CellMap[i+1][j].cellColor.getBlue() / 255.f ;
                        ++neighborsCount ;
                    }
                    if (j < LAND_D-1) {
                        neighborAverage[0] += CellMap[i][j+1].cellColor.getRed() / 255.f ;
                        neighborAverage[1] += CellMap[i][j+1].cellColor.getGreen() / 255.f ;
                        neighborAverage[2] += CellMap[i][j+1].cellColor.getBlue() / 255.f ;
                        ++neighborsCount ;
                    }
                    averageColorMap[i][j] = new CellColor(new Color(
                            (float) neighborAverage[0]/neighborsCount,
                            (float) neighborAverage[1]/neighborsCount,
                            (float) neighborAverage[2]/neighborsCount
                    )) ;
                }
            for (int i = 0; i < LAND_W; ++i)
                for (int j = 0; j < LAND_D; ++j)
                    CellMap[i][j].cellColor = averageColorMap[i][j];
        }
    }

    public void averageHeight(int iterations) {
        double[][] averageHeightMap = new double[LAND_W + 1][LAND_D + 1];
        int[][] averageHeightCellsCnt = new int[LAND_W + 1][LAND_D + 1];
        while( iterations-- > 0 ) {
            for (int i = 0; i < LAND_W; ++i)
                for (int j = 0; j < LAND_D; ++j) {
                    averageHeightMap[i][j] = CellMap[i][j].cellHeight;
                    averageHeightCellsCnt[i][j] = 1 ;
                    if (i > 0) {
                        averageHeightMap[i][j] += CellMap[i-1][j].cellHeight;
                        ++averageHeightCellsCnt[i][j] ;
                    }
                    if (j > 0) {
                        averageHeightMap[i][j] += CellMap[i][j-1].cellHeight;
                        ++averageHeightCellsCnt[i][j] ;
                    }
                    if (i < LAND_W-1) {
                        averageHeightMap[i][j] += CellMap[i+1][j].cellHeight;
                        ++averageHeightCellsCnt[i][j] ;
                    }
                    if (j < LAND_D-1) {
                        averageHeightMap[i][j] += CellMap[i][j+1].cellHeight;
                        ++averageHeightCellsCnt[i][j] ;
                    }
                }
            for (int i = 0; i < LAND_W; ++i)
                for (int j = 0; j < LAND_D; ++j)
                    averageHeightMap[i][j] /= averageHeightCellsCnt[i][j] ;
            for (int i = 0; i < LAND_W; ++i)
                for (int j = 0; j < LAND_D; ++j)
                    CellMap[i][j].cellHeight = Math.max(0, CellMap[i][j].cellHeight * 0.5 + averageHeightMap[i][j] * 0.5);
        }
    }

    /**
     * Diamond-square algorithm implementation
     */

    private final double diamondHeightStep = 10.0d;

    /**
     * Generates rectangular height map with corners in (xStart,yStart), (xFinish,yFinish)
     */
    private void diamondSquareGen(int xStart, int yStart, int xFinish, int yFinish) {
        CellMap[xStart][yStart].cellHeight = mRandom(-1, 1) * diamondHeightStep;
        CellMap[xStart][yFinish - 1].cellHeight = mRandom(-1, 1) * diamondHeightStep;
        CellMap[xFinish - 1][yStart].cellHeight = mRandom(-1, 1) * diamondHeightStep;
        CellMap[xFinish - 1][yFinish - 1].cellHeight = mRandom(-1, 1) * diamondHeightStep;
        diamondSquareBlockGen(xStart, yStart, xFinish, yFinish);
    }

    /**
     * Generates squared height map with corners in (xStart,yStart), (xFinish,yFinish)
     */
    private void diamondSquareBlockGen(int xStart, int yStart, int xFinish, int yFinish) {
        if (xFinish - xStart < 3 && yFinish - yStart < 3)
            return;
        int xMiddle = (xStart + xFinish) / 2, yMiddle = (yStart + yFinish) / 2;

        CellMap[xStart][yMiddle].cellHeight = (CellMap[xStart][yStart].cellHeight + CellMap[xStart][yFinish - 1].cellHeight) / 2;
        CellMap[xFinish - 1][yMiddle].cellHeight = (CellMap[xFinish - 1][yStart].cellHeight + CellMap[xFinish - 1][yFinish - 1].cellHeight) / 2;
        CellMap[xMiddle][yStart].cellHeight = (CellMap[xStart][yStart].cellHeight + CellMap[xFinish - 1][yStart].cellHeight) / 2;
        CellMap[xMiddle][yFinish - 1].cellHeight = (CellMap[xStart][yFinish - 1].cellHeight + CellMap[xFinish - 1][yFinish - 1].cellHeight) / 2;

        CellMap[xMiddle][yMiddle].cellHeight = (
                CellMap[xStart][yMiddle].cellHeight
                        + CellMap[xFinish - 1][yMiddle].cellHeight
                        + CellMap[xMiddle][yStart].cellHeight
                        + CellMap[xMiddle][yFinish - 1].cellHeight) / 4
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

    private int mRandom(int from, int to) {
        if (from > to)
            return 0;
        return (int)Math.round( mRandom((double)from, (double)to) ) ;
    }
}
