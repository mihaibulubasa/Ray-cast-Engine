import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall2 extends Tile{
	
	public Wall2(int i, int j) {
		super(i, j);
		solid = true;
		try {
			image = ImageIO.read(new File("window.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
