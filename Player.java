import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player {
    private int x, y;
    private final int speed = 3;
    private final int size = 40;
    private int health = 10;
    private int maxHealth = 10;
    private boolean left, right, up, down;
    private BufferedImage spritePlayer;

    // Emplacement du joueur et chemin de son sprite
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            spritePlayer = ImageIO.read(getClass().getResource("/resources/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mise à jour de son déplacement
    public void update() {
        if (left)
            x -= speed;
        if (right)
            x += speed;
        if (up)
            y -= speed;
        if (down)
            y += speed;
    }

    // Affichage joueur
    public void draw(Graphics g) {
        if (spritePlayer != null) {
            g.drawImage(spritePlayer, x, y, size + 5 , size, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, size, size);
        }
    }

    // Détection touche apuyer
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            right = true;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            up = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            down = true;
    }

    // Détection touche relâcher
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            right = false;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            down = false;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0)
            health = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getSize() {
        return size;
    }
}
