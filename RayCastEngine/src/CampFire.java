import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CampFire extends Entity{

	public CampFire(double x, double y, int size) {
		super(x, y, size);
		try {
			image = ImageIO.read(new File("campfire.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		
	}

}
