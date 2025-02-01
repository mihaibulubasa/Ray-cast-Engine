//import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window extends JFrame{
	
	private static final long serialVersionUID = -1268045423924893033L;
	private Game game;
	public Window(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setSize(new Dimension(500, 500));
		setUndecorated(true);
		game = new Game();
		add(game);
		addKeyListener(game.getHandler().getKey());
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setCursor(this.getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));
	}
	
	@Override 
	public void dispose() {
		super.dispose();
		if(game.getHandler().getMouse() != null) {
			game.getHandler().getMouse().stop();
		}
	}
}
