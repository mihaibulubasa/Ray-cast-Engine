import java.awt.Image;

public abstract class Entity {
	protected double x, y;
	protected double angleRelativeToTheCamera;
	protected boolean visible = false;
	protected double distanceFromCamera;
	protected int size;
	protected Image image;
	public Entity(double x, double y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	public abstract void tick();
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public double getAngleRelativeToTheCamera() {
		return angleRelativeToTheCamera;
	}

	public void setAngleRelativeToTheCamera(double angleRelativeToTheCamera) {
		this.angleRelativeToTheCamera = angleRelativeToTheCamera;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public double getDistanceFromCamera() {
		return distanceFromCamera;
	}

	public void setDistanceFromCamera(double distanceFromCamera) {
		this.distanceFromCamera = distanceFromCamera;
	}

	public Image getImage() {
		return image;
	}
}
