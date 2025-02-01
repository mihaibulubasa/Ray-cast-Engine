import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Camera {
	private Handler handler;
	private double FOV = Math.PI / 3;
	private int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	private int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	private double t = FOV / (WIDTH);
	public Camera(Handler handler) {
		this.handler = handler;
	}
	
	public class PointD {
		public double x;
		public double y;
		public double cos;
		public double sin;
		public double tan;
		public int row;
		public Tile tile;
		
	}
	
	private PointD getRayV(double angle) {
		PointD p = getStartV(angle);
		double y = handler.getWorld().getSize();
		double x = y / p.tan;
		if(p.sin >= 0) {
			y = -y;
		} else {
			x = -x;			
		}
		while((int)(p.x) / handler.getWorld().getSize() >= 0 && (int)(p.x) / handler.getWorld().getSize() < handler.getWorld().getWidth() && (int)(p.y) / handler.getWorld().getSize() >= 0 && (int)(p.y) / handler.getWorld().getSize() < handler.getWorld().getHeight()) {
			if(handler.getWorld().getTile((int)(p.x) / handler.getWorld().getSize(), (int)(p.y) / handler.getWorld().getSize()).isSolid()) {
				p.tile = handler.getWorld().getTile((int)(p.x) / handler.getWorld().getSize(), (int)(p.y) / handler.getWorld().getSize());
				break;
			} else {
				p.x += x;
				p.y += y;
			}
		}
		p.row = (int)p.x % handler.getWorld().getSize();
		return p;
	}
	
	
	private PointD getStartV(double angle) {
		PointD p = new PointD();
		p.cos = Math.cos(angle);
		p.sin = Math.sin(angle);
		p.tan = Math.tan(angle);
		if(p.sin >= 0) {
			p.y = ((int)handler.getPlayer().getY() / handler.getWorld().getSize()) * handler.getWorld().getSize() - 1;
			double c = handler.getPlayer().getY() - p.y;
			p.x = handler.getPlayer().getX() + (c / p.tan);
		} else {
			p.y = ((int)handler.getPlayer().getY() / handler.getWorld().getSize() + 1) * handler.getWorld().getSize();
			double c = p.y - handler.getPlayer().getY();
			p.x = (int)handler.getPlayer().getX() - (c / p.tan);
		}
		return p;
	}
	
	private PointD getRayO(double angle) {
		PointD p = getStartO(angle);
		double x = handler.getWorld().getSize();
		double y = x * p.tan;
		if(p.cos >= 0) {
			y = -y;
		} else {
			x = -x;
		}
		while((int)(p.x) / handler.getWorld().getSize() >= 0 && (int)(p.x) / handler.getWorld().getSize() < handler.getWorld().getWidth() && (int)(p.y) / handler.getWorld().getSize() >= 0 && (int)(p.y) / handler.getWorld().getSize() < handler.getWorld().getHeight()) {
			if(handler.getWorld().getTile((int)(p.x) / handler.getWorld().getSize(), (int)(p.y) / handler.getWorld().getSize()).isSolid()) {
				p.tile = handler.getWorld().getTile((int)(p.x) / handler.getWorld().getSize(), (int)(p.y) / handler.getWorld().getSize());
				break;
			} else {
				p.x += x;
				p.y += y;
			}
		}
		p.row = (int)p.y % handler.getWorld().getSize();
		return p;
	}
	private PointD getStartO(double angle) {
		PointD p = new PointD();
		p.cos = Math.cos(angle);
		p.sin = Math.sin(angle);
		p.tan = Math.tan(angle);
		if(p.cos >= 0) {
			p.x = (((int)handler.getPlayer().getX() / handler.getWorld().getSize()) + 1) * handler.getWorld().getSize();
			double c = p.x - handler.getPlayer().getX();
			p.y = handler.getPlayer().getY() - c * p.tan;
		} else {
			p.x = ((int)handler.getPlayer().getX() / handler.getWorld().getSize()) * handler.getWorld().getSize() - 1;
			double c = handler.getPlayer().getX() - p.x;
			p.y = handler.getPlayer().getY() + c * p.tan;
		}
		return p;
	}
	
	private PointD getRay(double angle) {
		PointD p = getRayO(angle);
		PointD p1 = getRayV(angle);
		if(distance((int)handler.getPlayer().getX(), (int)handler.getPlayer().getY(), p.x, p.y) < distance((int)handler.getPlayer().getX(), (int)handler.getPlayer().getY(), p1.x, p1.y)) {
			return p;
		}
		return p1;
	}
	
	private double distance(double x, double y, double x1, double y1) {
		return Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
	}
	
	private double angleCorrection(double angle) {
		if(angle > 2 * Math.PI) {
			return angle - 2 * Math.PI;
		}
		if(angle < 0) {
			return 2 * Math.PI + angle;
		}
		return angle;
	}
	
	private void setAngleEntity() {
		for(Entity entity : handler.getWorld().getEntities()) {
			double x = handler.getPlayer().getX() - entity.getX();
			double y = entity.getY() - handler.getPlayer().getY();
			double angle = angleCorrection(Math.atan(y / x));
			if(entity.getX() < handler.getPlayer().getX()) {
				angle = angle - Math.PI;
				if(entity.getY() > handler.getPlayer().getY()) {
					angle = Math.PI * 2 + angle;
					
				}
			}
			double angDif = angleCorrection(handler.getPlayer().getAngle() - angle);
			entity.setAngleRelativeToTheCamera(angDif);
		}
	}
	
	private int getEntityHeight(Entity entity) {
		entity.setDistanceFromCamera(distance(handler.getPlayer().getX(), handler.getPlayer().getY(), entity.getX(), entity.getY()));
		double angle = entity.getAngleRelativeToTheCamera();
		if(angle > (Math.PI / 6) * 11) {
			angle = Math.PI * 2 - angle;
		}
		int size = (int)((handler.getWorld().getSize() * HEIGHT) / (entity.getDistanceFromCamera() * Math.cos(angle)));
		return size;
	}
	private int getXEntityOnScreen(Entity entity) {
		if(entity.getAngleRelativeToTheCamera() > (Math.PI / 6) * 11) {
			double angle = 2 * Math.PI - entity.getAngleRelativeToTheCamera();
			return WIDTH / 2 - (int)((angle * WIDTH) / FOV);
		}
		return (int)((entity.getAngleRelativeToTheCamera() * WIDTH) / FOV) + WIDTH / 2;
	}
	
	private boolean isEntityInView(Entity entity) {
		if(entity.getAngleRelativeToTheCamera() < Math.PI / 6 || entity.getAngleRelativeToTheCamera() > (Math.PI / 6) * 11) {
			return true;
		}
		return false;
	}
	private void setEntitiesOnScreen() {
		setAngleEntity();
		for(Entity entity : handler.getWorld().getEntities()) {
			entity.setVisible(isEntityInView(entity));
		}
	}
	
	public class Collum {
		public double height;
		public int index;
		public boolean isEntity;
		public int entityIndex;
		public double distance;
		public int row;
		public Tile tile;
		
		public Collum(double height, int index, boolean isEntity) {
			this.height = height;
			this.index = index;
			this.isEntity = isEntity;
		}
	}
	public void paint(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
		setEntitiesOnScreen();
		ArrayList<Collum> collums = new ArrayList<>();
		double ang = handler.getPlayer().getAngle() + (FOV / 2);
		for(int i = 0; i < WIDTH; i++) {
			double angle = angleCorrection(ang - t * i);
			double angDif = angleCorrection(handler.getPlayer().getAngle() - angle);
			if(angDif > Math.PI / 6) {
				angDif = Math.PI * 2 - angDif;
			}
			PointD point = getRay(angle);
			double distance = distance(handler.getPlayer().getX(), handler.getPlayer().getY(), point.x, point.y);
			double height = (handler.getWorld().getSize() * HEIGHT) / (distance * Math.cos(angDif));
			collums.add(new Collum(height, i, false));
			collums.getLast().distance = distance;
			collums.getLast().row = point.row;
			collums.getLast().tile = point.tile;
		}
		
		for(Entity e : handler.getWorld().getEntities()) {
			if(e.isVisible()) {
				int x = getXEntityOnScreen(e);
				collums.add(new Collum(e.getDistanceFromCamera(), x, true));
				collums.getLast().entityIndex = handler.getWorld().getEntities().indexOf(e);
				collums.getLast().distance = e.getDistanceFromCamera();
			}
		}
		Collections.sort(collums, new Comparator<Collum>() {
			@Override
			public int compare(Camera.Collum o1, Camera.Collum o2) {
				return Double.compare(o2.distance, o1.distance);
			}
			
		});
		for(Collum c : collums) {
			if(c.isEntity) {
				Entity e = handler.getWorld().getEntities().get(c.entityIndex);
				int size = this.getEntityHeight(e);
				g.drawImage(e.getImage(), c.index - size / 4, (HEIGHT - size) / 2, size / 2, size, null);
			} else {
				int xR = c.row;
				if(xR == 64) {
					xR = 0;
				}
				int x = c.index;
				int y = (int)(HEIGHT - c.height) / 2;
				int width = 1;
				int height = (int)c.height;
				if(c.tile != null) {
					g.drawImage(c.tile.getImage(), x, y, x + width, y + height, xR, 0, xR + 1, handler.getWorld().getSize(), null);
				}
			}	
		}		
	}	
}
