import java.awt.event.KeyEvent;

public class Player {
	private final int size = 32;
	private double x;
	private double y;
	private double angle = 0;
	private double movSpeed = 3;
	private double rotSpeed = 2f;
	
	private Handler handler;
	
	public Player(double x, double y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;
	}
	public void tick() {
		moveUp();
		moveDown();
		moveRight();
		moveLeft();
		angleCorrection();
	}
	
	public void rotate(double angleAmount) {
		angle += rotSpeed * angleAmount;
	}
	
	private void moveUp() {
		if(handler.getKey().isKeyPressed(KeyEvent.VK_W) == true) {
			double nextX = x + movSpeed * Math.cos(angle);
			double nextY = y - movSpeed * Math.sin(angle);
			if(collision(nextX, y) == false) {
				x += movSpeed * Math.cos(angle);
			}
			if(collision(x, nextY) == false) {
				y -= movSpeed * Math.sin(angle);
			}
		}	
	}
	
	private void moveDown() {
		if(handler.getKey().isKeyPressed(KeyEvent.VK_S) == true) {
			double nextX = x - movSpeed * Math.cos(angle);
			double nextY = y + movSpeed * Math.sin(angle);
			if(collision(nextX, y) == false) {
				x -= movSpeed * Math.cos(angle);
			}
			if(collision(x, nextY) == false) {
				y += movSpeed * Math.sin(angle);
			}
		}
	}
	private void moveLeft() {
		if(handler.getKey().isKeyPressed(KeyEvent.VK_A)== true) {
			double nextX = x - movSpeed * Math.sin(angle);
			double nextY = y - movSpeed * Math.cos(angle);
			if(collision(nextX, y) == false) {
				x -= movSpeed * Math.sin(angle);
			}
			if(collision(x, nextY) == false) {
				y -= movSpeed * Math.cos(angle);
			}
		}
	}
	private void moveRight() {
		if(handler.getKey().isKeyPressed(KeyEvent.VK_D) == true) {
			double nextX = x + movSpeed * Math.sin(angle);
			double nextY = y + movSpeed * Math.cos(angle);
			if(collision(nextX, y) == false) {
				x += movSpeed * Math.sin(angle);
			}
			if(collision(x, nextY) == false) {
				y += movSpeed * Math.cos(angle);
			}
		}
	}
	
	private double distance(double x, double y, double x1, double y2) {
	    return Math.sqrt((x1 - x) * (x1 - x) + (y2 - y) * (y2 - y));
	}
	
	private boolean collision(double x, double y) {
		int i = (int)y / handler.getWorld().getSize();
		int j = (int)x / handler.getWorld().getSize();
		if(handler.getWorld().getTile(j, i).isSolid()) {
			return true;
		}
		if(distance(x, y, x, ((int)y / handler.getWorld().getSize()) * handler.getWorld().getSize()) < size / 2) {
			i = (int)y / handler.getWorld().getSize() - 1 ;
			j = (int)x / handler.getWorld().getSize();
			if(handler.getWorld().getTile(j, i).isSolid()) {
				return true;
			}
		}
		if(distance(x, y, x, ((int)y / handler.getWorld().getSize() + 1) * handler.getWorld().getSize()) < size / 2) {
			i = (int)y / handler.getWorld().getSize() + 1;
			j = (int)x / handler.getWorld().getSize();
			if(handler.getWorld().getTile(j, i).isSolid()) {
				return true;
			}
		}
		if(distance(x, y, ((int)x / handler.getWorld().getSize()) * handler.getWorld().getSize(), y) < size / 2) {
			i = (int)y / handler.getWorld().getSize();
			j = (int)x / handler.getWorld().getSize() - 1;
			if(handler.getWorld().getTile(j, i).isSolid()) {
				return true;
			}
		}
		if(distance(x, y, ((int)x / handler.getWorld().getSize() + 1) * handler.getWorld().getSize(), y) < size / 2) {
			i = (int)y / handler.getWorld().getSize();
			j = (int)x / handler.getWorld().getSize() + 1;
			if(handler.getWorld().getTile(j, i).isSolid()) {
				return true;
			}
		}
		if(distance(x, y, ((int)x / handler.getWorld().getSize()) * handler.getWorld().getSize(), ((int)y / handler.getWorld().getSize()) * handler.getWorld().getSize()) < size / 2) {
			i = (int)y / handler.getWorld().getSize() - 1;
			j = (int)x / handler.getWorld().getSize() - 1;
			if(handler.getWorld().getTile(j, i).isSolid()) {
				return true;
			}
		}
		if(distance(x, y, ((int)x / handler.getWorld().getSize() + 1) * handler.getWorld().getSize(), ((int)y / handler.getWorld().getSize()) * handler.getWorld().getSize()) < size / 2) {
			i = (int)y / handler.getWorld().getSize() - 1;
			j = (int)x / handler.getWorld().getSize() + 1;
			if(handler.getWorld().getTile(j, i).isSolid()) {
				return true;
			}
		}
		if(distance(x, y, ((int)x / handler.getWorld().getSize()) * handler.getWorld().getSize(), ((int)y / handler.getWorld().getSize() + 1) * handler.getWorld().getSize()) < size / 2) {
			i = (int)y / handler.getWorld().getSize() + 1;
			j = (int)x / handler.getWorld().getSize() - 1;
			if(handler.getWorld().getTile(j, i).isSolid()) {
				return true;
			}
		}
		if(distance(x, y, ((int)x / handler.getWorld().getSize() + 1) * handler.getWorld().getSize(), ((int)y / handler.getWorld().getSize() + 1) * handler.getWorld().getSize()) < size / 2) {
			i = (int)y / handler.getWorld().getSize() + 1;
			j = (int)x / handler.getWorld().getSize() + 1;
			if(handler.getWorld().getTile(j, i).isSolid()) {
				return true;
			}
		}
		return false;
	}
	private void angleCorrection() {
		if(angle > 2 * Math.PI) {
			angle = angle - 2 * Math.PI;
		}
		if(angle < 0) {
			angle = 2 * Math.PI + angle;
		}
	}
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}
}
