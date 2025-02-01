import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends Tile{

	public Wall(int i, int j) {
		super(i, j);
		solid = true;
		try {
			image = ImageIO.read(new File("wall_1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
