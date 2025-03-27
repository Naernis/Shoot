import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Monster {
    private double x, y;
    private double speed;
    private int health;
    private int damage;
    private BufferedImage monsterSprite;
    private final int size = 40;
    private Player player;

    public Monster(double x, double y, MonsterType type, Player player) {
        this.x = x;
        this.y = y;
        this.health = type.health;
        this.damage = type.damage;
        this.speed = type.speed;
        this.player = player;
        
        try {
            this.monsterSprite = ImageIO.read(getClass().getResource(type.spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        double dx = player.getX() + player.getSize() / 2 - (x + size / 2);
        double dy = player.getY() + player.getSize() / 2 - (y + size / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    public void draw(Graphics g) {
        if (monsterSprite != null) {
            g.drawImage(monsterSprite, (int) x, (int) y, size, size, null);
        } else {
            g.setColor(Color.MAGENTA);
            g.fillRect((int) x, (int) y, size, size);
        }
    }

    public boolean isHit(Bullet bullet) {
        return bullet != null &&
                bullet.getX() >= x && bullet.getX() <= x + size &&
                bullet.getY() >= y && bullet.getY() <= y + size;
    }

    public void takeDamage(int amount) {
        health -= amount;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getDamage() {
        return damage;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
