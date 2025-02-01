import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class World {
	private final int size = 64;
	private int width;
	private int height;
	private Tile[][] map;
	private ArrayList<Entity> entities;
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		map = new Tile[height][width];
		entities = new ArrayList<>();
		entities.add(new Goblin(300, 300, 32));
		entities.add(new Skeleton(370, 370, 32));
		entities.add(new CampFire(250, 80, 16));
		initWorld();
	}
	
	private void initWorld() {
		for(int i = 1; i < height - 1; i++) {
			for(int j = 1; j < width - 1; j++) {
				map[i][j] = new EmptyTile(i, j);
			}
		}
		for(int i = 0; i < width; i++) {
			map[0][i] = new Wall(0, i);
			map[width - 1][i] = new Wall(width - 1, i);;
		}
		for(int i = 0; i < height; i++) {
			map[i][0] = new Wall(i, 0);
			map[i][height - 1] = new Wall(i, height - 1);;
		}
		map[2][2] = new Wall2(2, 2);
	}
	
	public void tick() {
		for(Entity e : entities) {
			e.tick();
		}
	}
	
	public Tile getTile(int x, int y) {
		return map[y][x];
	}
	public int getSize() {
		return size;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

}
