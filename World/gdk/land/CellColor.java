package land;

import java.awt.*;

public class CellColor extends Color {

    public CellColor(int r, int g, int b ) {
        super(r, g, b);
    }

    public CellColor(Color c) {
        super(c.getRGB()) ;
    }

    public CellColor add( CellColor x ) {
        return new CellColor(
                ( this.getRed() + x.getRed() ) / 2,
                ( this.getGreen() + x.getGreen() ) / 2,
                ( this.getBlue() + x.getBlue() ) / 2
                ) ;
    }
}
