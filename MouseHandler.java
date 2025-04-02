import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseHandler extends MouseAdapter {
    private Player player;
    private ArrayList<Bullet> bullets;
    private long lastShotTime = 0;
    private final long RELOAD_TIME = 250;

    public MouseHandler(Player player, ArrayList<Bullet> bullets) {
        this.player = player;
        this.bullets = bullets;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= RELOAD_TIME) {

            int startX = player.getX() + player.getSize() / 2;
            int startY = player.getY() + player.getSize() / 2;
            int targetX = e.getX();
            int targetY = e.getY();

            double dx = targetX - startX;
            double dy = targetY - startY;
            double length = Math.sqrt(dx * dx + dy * dy);

            dx = (dx / length) * 10;
            dy = (dy / length) * 10;

            bullets.add(new Bullet(startX, startY, dx, dy));
            lastShotTime = currentTime;
        }
    }
}
