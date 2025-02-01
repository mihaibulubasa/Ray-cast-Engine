import java.awt.*;

public class MouseHandler {
    private boolean running = true; // To control the thread
    private Robot robot;
    private Thread mouseTracker;
    private int centerX;
    private int centerY;
    private int distanceFromCamera;
    private Handler handler;
    public MouseHandler(Handler handler) {
        this.handler = handler;
        try {
            robot = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            centerX = (int) screenSize.getWidth() / 2;
            centerY = (int) screenSize.getHeight() / 2;
            distanceFromCamera = (int) (centerX / Math.tan(Math.PI / 6));

            startMouseTracker();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void startMouseTracker() {
        mouseTracker = new Thread(() -> {
            try {
                while (running) {
                    Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                    if (mouseLocation.x != centerX || mouseLocation.y != centerY) {
                        int nextX = centerX - mouseLocation.x;
                        double angDif;
                        if (nextX >= 0) {
                            angDif = Math.atan((double) nextX / distanceFromCamera);
                            handler.getPlayer().rotate(angDif); 
                        } else {
                            nextX = -nextX;
                            angDif = Math.atan((double) nextX / distanceFromCamera);
                            handler.getPlayer().rotate(-angDif); 
                        }
                        robot.mouseMove(centerX, centerY);
                    }
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
            }
        });

        mouseTracker.start();
    }

    // Stops the mouse tracking thread
    public void stop() {
        running = false;
        if (mouseTracker != null) {
            mouseTracker.interrupt(); // Interrupt the thread if needed
        }
    }
}
