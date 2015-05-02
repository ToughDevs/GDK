package src;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;

public class EvoWorld {
	ArrayList<EvoAnimal> animals;	
	EvoTerritory territory;
	final int biomTypeCount = 3;
	final Color[] biomTypeColor = 
		{new Color(255,0,0), 
		 new Color(0,255,0), 
		 new Color(0,0,255)};

	public void tick() {

	}

	public void generate(int width, int height, int animalsCount) {
		this.animals = new ArrayList<EvoAnimal>();
		for (int i = 0; i < animalsCount; i++) {
			animals.add(new EvoAnimal(
				new Point(width, height)
				));
		}

		this.territory = new EvoTerritory(width, height, biomTypeCount);

	}
}