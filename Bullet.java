import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Bullet {
    private int x, y;
    private double dx, dy;
    private final int width = 18;
    private final int height = 18;
    private BufferedImage spriteBullet;

    // Stat des balles et recherche de l'image
    public Bullet(double x, double y, double dx, double dy) {
        this.x = (int) x;
        this.y = (int) y;
        this.dx = dx;
        this.dy = dy;

        try {
            spriteBullet = ImageIO.read(getClass().getResource("/resources/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x += dx;
        y += dy;
    }

    // Affichage des balles
    public void draw(Graphics g) {
        if (spriteBullet != null) {
            g.drawImage(spriteBullet, (int) x, (int) y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval((int) x, (int) y, width, height);
        }
    }

    // Vérifier si les balles sort de la page
    public boolean isOffScreen(int screenWidth, int screenHeight) {
        return (x < 0 || x > screenWidth || y < 0 || y > screenHeight);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
