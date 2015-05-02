package src;

import java.util.Random;

public class EvoTerritory {
	EvoTerrCell[][] cells;

	public EvoTerritory(int width, int height, int biomCount) {
		cells = new EvoTerrCell[width][height];
		for (int i =0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				cells[i][j] = new EvoTerrCell(biomCount);
			}
		}
	}
}

class EvoTerrCell {
	int biomType;

	public EvoTerrCell(int biomCount) {
		Random random = new Random();
		this.biomType = random.nextInt(biomCount);
	}
}