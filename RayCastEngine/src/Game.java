import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = -7983728485805403829L;
	private Timer timer;
	private Handler handler;
	private int frames, framesOnScreen = 60;
	private long lastTime= System.currentTimeMillis();
	public Game() {
		setLayout(null);
		handler = new Handler();
		timer = new Timer(0, this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		handler.getCamera().paint(g);
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(framesOnScreen), 20, 20);
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		handler.getPlayer().tick();
		handler.getWorld().tick();
		repaint();
		frames++;
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastTime >= 1000) {
			framesOnScreen = frames;
			frames = 0;
			lastTime += 1000;
		}
	}

	public Handler getHandler() {
		return handler;
	}
	
	
}
