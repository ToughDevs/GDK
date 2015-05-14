package gdk.land;

import javafx.scene.control.Cell;
import javafx.util.Pair;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Land {
    public int BIOME_SIZE;
    public int BIOME_MAP_SIZE;
    private int LAND_W;
    private int LAND_D;
    private double LAND_SCALE;
    private LandCell[][] CellMap;

    public Land() {
        BIOME_SIZE = 64;
        BIOME_MAP_SIZE = 8;
        LAND_W = BIOME_SIZE * BIOME_MAP_SIZE;
        LAND_D = BIOME_SIZE * BIOME_MAP_SIZE;
        LAND_SCALE = 1;
        CellMap = new LandCell[LAND_W][LAND_D];
        for (int i = 0; i < LAND_W; ++i)
            for (int j = 0; j < LAND_D; ++j)
                CellMap[i][j] = new LandCell();
    }

    public Land(int BIOME_SIZE, int BIOME_MAP_SIZE) {
        this.BIOME_SIZE = BIOME_SIZE ;
        this.BIOME_MAP_SIZE = BIOME_MAP_SIZE ;
        LAND_W = BIOME_SIZE * BIOME_MAP_SIZE;
        LAND_D = BIOME_SIZE * BIOME_MAP_SIZE;
        LAND_SCALE = 1;
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

    public CellTemperature getCellTemperature(int x, int y) {
        return CellMap[x][y].cellTemperature ;
    }

    public void setCellFertility(int x, int y, double f) {
        CellMap[x][y].fertility = f ;
    }

    public double getCellFertility(int x, int y) {
        return CellMap[x][y].fertility ;
    }

    public double getCellHumidity(int x, int y) {
        return CellMap[x][y].humidity ;
    }

    public void setCellHumidity(int x, int y, double h) {
        CellMap[x][y].humidity = h ;
    }

    public double getCellVirulence(int x, int y) {
        return CellMap[x][y].virulence ;
    }

    public void setCellVirulence(int x, int y, double v) {
        CellMap[x][y].virulence = v ;
    }

    public double getCellVegetation(int x, int y) {
        return CellMap[x][y].vegetation ;
    }

    public void setCellVegetation(int x, int y, double v) {
        CellMap[x][y].vegetation = v ;
    }

    public double getCellFreshMeat(int x, int y) {
        return CellMap[x][y].freshMeat ;
    }

    public void setCellFreshMeat(int x, int y, double f) {
        CellMap[x][y].freshMeat = f ;
    }

    public double getCellRottenMeat(int x, int y) {
        return CellMap[x][y].rottenMeat ;
    }

    public void setCellRottenMeat(int x, int y, double r) {
        CellMap[x][y].rottenMeat = r ;
    }

    public Biome getCellBiomeType(int x, int y) {
        return Biome.getBiomeById(CellMap[x][y].biomeID) ;
    }

    public CellColor getCellColor(int x, int y) {
        return CellMap[x][y].cellColor ;
    }

    public void setBiomeMutationIterations( int a ) {
        BIOME_MUTATE_ITERATIONS_COUNT = a ;
    }

    private double WATER_CELLS_PERCENT = 0.15 ;
    private double COLD_CELLS_PERCENT_MIN = 0.15 ;
    private double COLD_CELLS_PERCENT_MAX = 0.25 ;
    private double[] BIOME_MUTATE_PROBABILITY = new double[] {0.8, -1, 0.05, 0.01} ;
    private int MAX_MUTATION_STEPS = 2 ;
    private int BIOME_MUTATE_ITERATIONS_COUNT = 2000 ;

    public void generateNew() {
        LocalDateTime localDateTime = LocalDateTime.now() ;

        int water_cells = (int) (WATER_CELLS_PERCENT * BIOME_MAP_SIZE * BIOME_MAP_SIZE);
        int cold_cells = (int) (mRandom(COLD_CELLS_PERCENT_MIN, COLD_CELLS_PERCENT_MAX) * BIOME_MAP_SIZE * BIOME_MAP_SIZE);

        Random random = new Random();

        int i, j, k, x, y, b, all[];
        Biome[][] BiomeMap;
        BiomeMap = new Biome[BIOME_MAP_SIZE][BIOME_MAP_SIZE] ;
        for (i = 0; i < BIOME_MAP_SIZE; ++i)
            for (j = 0; j < BIOME_MAP_SIZE; ++j)
                BiomeMap[i][j] = new Biome();

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
                };

        int bi, bj;
        for (bi = 0; bi < BIOME_MAP_SIZE; ++bi)
            for (bj = 0; bj < BIOME_MAP_SIZE; ++bj)
                for (i = bi * BIOME_SIZE; i < (bi + 1) * BIOME_SIZE; ++i)
                    for (j = bj * BIOME_SIZE; j < (bj + 1) * BIOME_SIZE; ++j)
                        CellMap[i][j].biomeID = BiomeMap[bi][bj].BIOME_ID ;

        // Biome mutations
        int[][] BiomeNewCellMap = new int[LAND_D][LAND_W] ;
        int[][] mutationsCount = new int[LAND_D][LAND_W] ;
        for (i = 0; i < LAND_W; ++i)
            for (j = 0; j < LAND_D; ++j)
                mutationsCount[i][j] = 0 ;
        float[] BiomeMutationCandidates = new float[16] ;
        int dx[] = new int[]{-1, -1, -1,  0, 0, 0,  1, 1, 1},
                dy[] = new int[]{-1,  0,  1, -1, 0, 1, -1, 0, 1} ;
        float weight ;
        for( int iteration = 0 ; iteration < BIOME_MUTATE_ITERATIONS_COUNT ; ++iteration ) {
            for (i = 0; i < LAND_W; ++i)
                for (j = 0; j < LAND_D; ++j) {
                    BiomeNewCellMap[i][j] = CellMap[i][j].biomeID;
                    if ( mutationsCount[i][j] < MAX_MUTATION_STEPS && mRandom(0f, 1f) <= BIOME_MUTATE_PROBABILITY[mutationsCount[i][j]]) {
                        for (k = 0; k < 16; ++k)
                            BiomeMutationCandidates[k] = 0;
                        for (k = 0; k < 9; ++k) {
                            x = i + dx[k];
                            y = j + dy[k];
                            if (dx[k] == 0 || dy[k] == 0)
                                weight = 1f;
                            else
                                weight = 0.5f;
                            if (x >= 0 && y >= 0 && x < LAND_W && y < LAND_D)
                                BiomeMutationCandidates[CellMap[x][y].biomeID] += weight;
                        }
                        ArrayList<Pair<Float, Integer>> mutationCandidates = new ArrayList<Pair<Float, Integer>>();
                        for (k = 0; k < 16; ++k)
                            if (k != CellMap[i][j].biomeID && BiomeMutationCandidates[k] > 0)
                                mutationCandidates.add(new Pair<Float, Integer>(BiomeMutationCandidates[k], k));
                        if (mutationCandidates.size() == 0)
                            continue;
                        else {
                            Collections.sort(mutationCandidates, new Comparator<Pair<Float, Integer>>() {
                                @Override
                                public int compare(Pair<Float, Integer> o1, Pair<Float, Integer> o2) {
                                    if (o1.getKey() > o2.getKey())
                                        return -1;
                                    else if (o1.getKey().equals(o2.getKey()))
                                        return 0;
                                    else
                                        return 1;
                                }
                            });
                            if (mutationCandidates.get(0).getKey() < 2.0f)
                                continue ;
                            mutationsCount[i][j]++ ;
                            BiomeNewCellMap[i][j] = mutationCandidates.get(0).getValue();
                        }
                    }
                }
            for (i = 0; i < LAND_W; ++i)
                for (j = 0; j < LAND_D; ++j)
                    CellMap[i][j].biomeID = BiomeNewCellMap[i][j] ;
        }

        for( int iteration = 0 ; iteration < BIOME_MUTATE_ITERATIONS_COUNT ; ++iteration ) {
            for (i = 0; i < LAND_W; ++i)
                for (j = 0; j < LAND_D; ++j) {
                    BiomeNewCellMap[i][j] = CellMap[i][j].biomeID;
                    if ( mRandom(0f, 1f) <= BIOME_MUTATE_PROBABILITY[0]) {
                        for (k = 0; k < 16; ++k)
                            BiomeMutationCandidates[k] = 0;
                        for (k = 0; k < 9; ++k) {
                            x = i + dx[k];
                            y = j + dy[k];
                            if (dx[k] == 0 || dy[k] == 0)
                                weight = 1f;
                            else
                                weight = 0.5f;
                            if (x >= 0 && y >= 0 && x < LAND_W && y < LAND_D)
                                BiomeMutationCandidates[CellMap[x][y].biomeID] += weight;
                        }
                        ArrayList<Pair<Float, Integer>> mutationCandidates = new ArrayList<Pair<Float, Integer>>();
                        for (k = 0; k < 16; ++k)
                            if (k != CellMap[i][j].biomeID && BiomeMutationCandidates[k] > 0)
                                mutationCandidates.add(new Pair<Float, Integer>(BiomeMutationCandidates[k], k));
                        if (mutationCandidates.size() == 0)
                            continue;
                        else {
                            Collections.sort(mutationCandidates, new Comparator<Pair<Float, Integer>>() {
                                @Override
                                public int compare(Pair<Float, Integer> o1, Pair<Float, Integer> o2) {
                                    if (o1.getKey() > o2.getKey())
                                        return -1;
                                    else if (o1.getKey().equals(o2.getKey()))
                                        return 0;
                                    else
                                        return 1;
                                }
                            });
                            if (mutationCandidates.get(0).getKey() < 2.0f)
                                continue ;
                            mutationsCount[i][j]++ ;
                            BiomeNewCellMap[i][j] = mutationCandidates.get(0).getValue();
                        }
                    }
                }
            for (i = 0; i < LAND_W; ++i)
                for (j = 0; j < LAND_D; ++j)
                    CellMap[i][j].biomeID = BiomeNewCellMap[i][j] ;
        }

        // Fix water basins
        float waterNeighbours ;
        double maxNeighbour ;
        int bestNeighbour ;
        for(i = 0 ; i < LAND_D ; ++i)
            for(j = 0 ; j < LAND_W; ++j)
                if( CellMap[i][j].biomeID == Biome.BIOME_WATER &&
                        CellMap[i][j].cellHeight > Biome.biomeWater.scaleRateMin ) {
                    System.out.println("high water") ;
                    for(k = 0 ; k < 16 ; ++k)
                        BiomeMutationCandidates[k] = 0 ;
                    for (k = 0; k < 4; ++k) {
                        x = i + dx[k];
                        y = j + dy[k];
                        if (dx[k] == 0 || dy[k] == 0)
                            weight = 1f;
                        else
                            weight = 0.5f;
                        if (x >= 0 && y >= 0 && x < LAND_D && y < LAND_W)
                            BiomeMutationCandidates[CellMap[x][y].biomeID] += weight;
                    }
                    BiomeMutationCandidates[Biome.BIOME_PLAINS] += 0.01 ;
                    maxNeighbour = 0 ; bestNeighbour = 0 ;
                    for(k = 0 ; k < 16; ++k)
                        if( k != Biome.BIOME_WATER &&
                                BiomeMutationCandidates[k] > maxNeighbour ) {
                            maxNeighbour = BiomeMutationCandidates[k] ;
                            bestNeighbour = k ;
                        }
                    CellMap[i][j].biomeID = bestNeighbour ;
                }

        for( int iteration = 0 ; iteration < BIOME_MUTATE_ITERATIONS_COUNT ; ++iteration ) {
            for (i = 0; i < LAND_W; ++i)
                for (j = 0; j < LAND_D; ++j) {
                    BiomeNewCellMap[i][j] = CellMap[i][j].biomeID;
                    if (BiomeNewCellMap[i][j] == Biome.BIOME_WATER) {
                        waterNeighbours = 0;
                        for (k = 0; k < 4; ++k) {
                            x = i + dx[k];
                            y = j + dy[k];
                            if (dx[k] == 0 || dy[k] == 0)
                                weight = 1f;
                            else
                                weight = 0.5f;
                            if (x >= 0 && y >= 0 && x < LAND_D && y < LAND_W) {
                                if (CellMap[x][y].biomeID == Biome.BIOME_WATER)
                                    waterNeighbours += weight;
                            } else
                                waterNeighbours += weight;
                        }
                        if (waterNeighbours < 2.5f) {
                            for (k = 0; k < 16; ++k)
                                BiomeMutationCandidates[k] = 0;
                            for (k = 0; k < 4; ++k) {
                                x = i + dx[k];
                                y = j + dy[k];
                                if (dx[k] == 0 || dy[k] == 0)
                                    weight = 1f;
                                else
                                    weight = 0.5f;
                                if (x >= 0 && y >= 0 && x < LAND_D && y < LAND_W)
                                    BiomeMutationCandidates[CellMap[x][y].biomeID] += weight;
                            }
                            ArrayList<Pair<Float, Integer>> mutationCandidates = new ArrayList<Pair<Float, Integer>>();
                            for (k = 0; k < 16; ++k)
                                if (k != CellMap[i][j].biomeID && BiomeMutationCandidates[k] > 0)
                                    mutationCandidates.add(new Pair<Float, Integer>(BiomeMutationCandidates[k], k));
                            if (mutationCandidates.size() == 0)
                                continue;
                            else if (mutationCandidates.size() == 1)
                                BiomeNewCellMap[i][j] = mutationCandidates.get(0).getValue();
                        }
                    }
                    for (i = 0; i < LAND_W; ++i)
                        for (j = 0; j < LAND_D; ++j)
                            CellMap[i][j].biomeID = BiomeNewCellMap[i][j];
                }
        }

        diamondSquareGen(0, 0, LAND_W, LAND_D);
        normalizeHeight(0, 0, LAND_W, LAND_D);
        //averageHeight(5);

        double[][] heightCoefficientMap = new double[LAND_D][LAND_W] ;
        for( i = 0 ; i < LAND_D ; ++i )
            for( j = 0 ; j < LAND_W ; ++j )
                heightCoefficientMap[i][j] = Biome.getBiomeById(CellMap[i][j].biomeID).scaleRateMin ;

        double[][] averageHeightMap = new double[LAND_W][LAND_D];
        int[][] averageHeightCellsCnt = new int[LAND_W][LAND_D];
        int iterations = 30 ;
        while( iterations-- > 0 ) {
            for (i = 0; i < LAND_W; ++i)
                for (j = 0; j < LAND_D; ++j) {
                    averageHeightMap[i][j] = heightCoefficientMap[i][j];
                    averageHeightCellsCnt[i][j] = 1 ;
                    if (i > 0) {
                        averageHeightMap[i][j] += heightCoefficientMap[i-1][j];
                        ++averageHeightCellsCnt[i][j] ;
                    }
                    if (j > 0) {
                        averageHeightMap[i][j] += heightCoefficientMap[i][j-1];
                        ++averageHeightCellsCnt[i][j] ;
                    }
                    if (i < LAND_W-1) {
                        averageHeightMap[i][j] += heightCoefficientMap[i+1][j];
                        ++averageHeightCellsCnt[i][j] ;
                    }
                    if (j < LAND_D-1) {
                        averageHeightMap[i][j] += heightCoefficientMap[i][j+1];
                        ++averageHeightCellsCnt[i][j] ;
                    }
                }
            for (i = 0; i < LAND_W; ++i)
                for (j = 0; j < LAND_D; ++j)
                    averageHeightMap[i][j] /= averageHeightCellsCnt[i][j] ;
            for (i = 0; i < LAND_W; ++i)
                for (j = 0; j < LAND_D; ++j)
                    heightCoefficientMap[i][j] = Math.max(0, averageHeightMap[i][j]);
        }

        for( i = 0 ; i < LAND_D ; ++i )
            for( j = 0 ; j < LAND_W ; ++j ) {
                CellMap[i][j].cellHeight *= heightCoefficientMap[i][j];
                Biome.getBiomeById(CellMap[i][j].biomeID)
                        .applyBiomeToCell(CellMap[i][j]);
            }

        greedyDealWithHills(100, BIOME_SIZE/2, 4, 0.2) ;

        averageHeight(10);

        for (i = 0; i < LAND_W; ++i)
            for (j = 0; j < LAND_D; ++j)
                if( CellMap[i][j].biomeID == Biome.BIOME_WATER &&
                        CellMap[i][j].cellHeight > Biome.biomeWater.scaleRateMin ) {
                    System.out.println("High water debug") ;
                    CellMap[i][j].cellHeight *= Biome.biomeWater.scaleRateMin;
                }

        averageHeight(10);

        averageColor(15);
        //applyHeightColorMask(10);
        applyRandomColorMask(0.1f);

        System.out.println( localDateTime.until(LocalDateTime.now(), ChronoUnit.SECONDS) ) ;
    }

    /**
     * Accessory landscape functions
     */

    private double HILL_MUTATION_PROBABILITY = 0.5 ;
    public void greedyDealWithHills(int iterations, int regionRadius, int regionDecreaseRadius, double newScale) {
        int i, j, i1, j1, count;
        while( iterations-- > 0 ) {
            for( i = 0 ; i < LAND_D ; ++i )
                for( j = 0 ; j < LAND_W ; ++j )
                    if( CellMap[i][j].biomeID == Biome.BIOME_HILLS && CellMap[i][j].cellHeight > Biome.biomeHills.scaleRateMin * 0.5 ) {
                        count = 0;
                        for (i1 = i - regionRadius; i1 <= i + regionRadius; ++i1)
                            for (j1 = j - regionRadius; j1 <= j + regionRadius; ++j1)
                                if (i1 < 0 || i1 >= LAND_D || j1 < 0 || j1 >= LAND_W)
                                    ++count;
                                else if (CellMap[i1][j1].cellHeight > Biome.biomeHills.scaleRateMin * 0.5)
                                    ++count;
                                else if (CellMap[i1][j1].cellHeight == 0 &&
                                        CellMap[i1][j1].biomeID == Biome.BIOME_HILLS)
                                    count -= 4 * regionRadius * regionRadius ;
                        if (count > (regionRadius * regionRadius) / 4 && Math.random() <= HILL_MUTATION_PROBABILITY) {
                            for( i1 = Math.max(i-regionDecreaseRadius, 0) ; i1 < Math.min(i+regionDecreaseRadius, LAND_D) ; ++i1 )
                                for( j1 = Math.max(j-regionDecreaseRadius, 0); j1 < Math.min(j+regionDecreaseRadius, LAND_W) ; ++j1 )
                                    if( Math.random() <= HILL_MUTATION_PROBABILITY )
                                        CellMap[i1][j1].cellHeight = Math.random() * newScale ;
                        }
                    }
        }
    }

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

    public void applyRandomColorMask(float strength) {
        int r, g, b ; float s ;
        for(int i = 0 ; i < LAND_W ; ++i )
            for( int j = 0 ; j < LAND_D ; ++j ) {
                r = CellMap[i][j].cellColor.getRed() ;
                g = CellMap[i][j].cellColor.getGreen() ;
                b = CellMap[i][j].cellColor.getBlue() ;
                s = (float) ( -0.5 + Math.random() ) * strength ;
                r = Math.min(255, Math.round(r / (1 - s))) ;
                g = Math.min(255, Math.round(g / (1 - s)));
                b = Math.min( 255, Math.round (b / (1-s)) ) ;
                CellMap[i][j].cellColor = new CellColor(new Color(r, g, b)) ;
//                CellMap[i][j].cellColor = CellMap[i][j].cellColor.add(
//                        new CellColor(
//                                new Color((float) (Math.random() * strength), (float) (Math.random() * strength), (float) (Math.random() * strength))
//                        )
//                );
            }
    }

    public void applyHeightColorMask(float strength) {
        int r, g, b;
        float s;
        for (int i = 0; i < LAND_W; ++i)
            for (int j = 0; j < LAND_D; ++j) {
                r = CellMap[i][j].cellColor.getRed();
                g = CellMap[i][j].cellColor.getGreen();
                b = CellMap[i][j].cellColor.getBlue();
                s = (float) ((0.9 + Math.random() * 0.1) * (CellMap[i][j].cellHeight-0.15) * strength);
                r = Math.max(Math.min(255, Math.round(r / (1 - s))), 0);
                g = Math.max(Math.min(255, Math.round(g / (1 - s))), 0);
                b = Math.max(Math.min(255, Math.round(b / (1 - s))), 0);
                CellMap[i][j].cellColor = new CellColor(new Color(r, g, b));
//                CellMap[i][j].cellColor = CellMap[i][j].cellColor.add(
//                        new CellColor(
//                                new Color((float) (Math.random() * strength), (float) (Math.random() * strength), (float) (Math.random() * strength))
//                        )
//                );
            }
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
                    CellMap[i][j].cellHeight = Math.max(0, averageHeightMap[i][j]);
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
