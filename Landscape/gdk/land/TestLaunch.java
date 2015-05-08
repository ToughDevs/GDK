package gdk.land;

public class TestLaunch {
    public static void main(String args[]) {
        Land landscape = new Land();
        landscape.generateNew();
        //landscape.normalizeHeight(0, 0, landscape.getWidth(), landscape.getDepth());
        //landscape.averageHeight();

        for (int i = 0; i < landscape.getWidth(); ++i) {
            for (int j = 0; j < landscape.getDepth(); ++j)
                System.out.printf("% 06.2f ", landscape.getCellHeight(i, j));
            System.out.print("\n");
        }
    }
}
