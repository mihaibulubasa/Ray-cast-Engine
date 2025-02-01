import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Goblin extends Enemy{

	public Goblin(double x, double y, int size) {
		super(x, y, size);
		try {
			image = ImageIO.read(new File("monster.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		
	}

}
