
public class Handler {
	private Player player;
	private World world;
	private KeyHandler key;
	private MouseHandler mouse;
	private Camera camera;
	public Handler() {
		world = new World(9, 9);
		player = new Player(80, 80, this);
		key = new KeyHandler();
		mouse = new MouseHandler(this);
		camera = new Camera(this);
	}

	public Player getPlayer() {
		return player;
	}

	public World getWorld() {
		return world;
	}

	public KeyHandler getKey() {
		return key;
	}
	
	public MouseHandler getMouse() {
		return mouse;
	}

	public Camera getCamera() {
		return camera;
	}
}
