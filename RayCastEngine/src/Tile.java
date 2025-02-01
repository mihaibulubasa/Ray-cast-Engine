import java.awt.Image;

public abstract class Tile {
	protected int i, j, id;
	protected boolean solid;
	protected Image image;
	
	public Tile(int i, int j) {
		this.i = i;
		this.j = j;;
	}
	
	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public int getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public boolean isSolid() {
		return solid;
	}
	
}
