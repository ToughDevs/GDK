package gdk.land;

import gdk.land.Landscape ;

public class TestLaunch {
    public static void main(String args[]) {
        Landscape landscape = new Landscape();
        landscape.generateNew();
        landscape.normalizeHeight();
        landscape.averageHeight();

        for (int i = 0; i < landscape.getWidth(); ++i) {
            for (int j = 0; j < landscape.getDepth(); ++j)
                System.out.printf("% 06.2f ", landscape.getPointHeight(i,j));
            System.out.print("\n");
        }
    }
}
