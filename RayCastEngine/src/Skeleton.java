import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Skeleton extends Enemy{

	public Skeleton(double x, double y, int size) {
		super(x, y, size);
		try {
			image = ImageIO.read(new File("skeleton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		
	}

}
