import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;

    private ArrayList<Bullet> bullets;
    private long lastShotTime = 0;
    private final long RELOAD_TIME = 250;

    private ArrayList<Monster> monsters = new ArrayList<>();
    private long lastMonsterSpawn = 0;
    private final long MONSTER_SPAWN_INTERVAL = 2000;

    private boolean gameOver = false;

    // Écran du jeu
    public GamePanel() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.BLACK);
        player = new Player(250, 250);
        bullets = new ArrayList<>();
        timer = new Timer(16, this);
        timer.start();
        addKeyListener(this);

        // Détection du clique (pour le tir)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastShotTime >= RELOAD_TIME) {

                    int startX = player.getX() + player.getSize() / 2;
                    int startY = player.getY() + player.getSize() / 2;
                    int targetX = e.getX();
                    int targetY = e.getY();
                    double angle = Math.atan2(targetY - startY, targetX - startX);

                    double dx = Math.cos(angle) * 10;
                    double dy = Math.sin(angle) * 10;

                    bullets.add(new Bullet(startX, startY, dx, dy));
                    lastShotTime = currentTime;
                }
            }
        });
        setFocusable(true);
    }

    // Vérification des événements (monstre touché ? mort ? apparition de monstre ?
    // etc...)
    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        bullets.forEach(Bullet::update);
        bullets.removeIf(b -> b.isOffScreen(getWidth(), getHeight()));

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMonsterSpawn >= MONSTER_SPAWN_INTERVAL) {
            spawnMonster();
            lastMonsterSpawn = currentTime;
        }

        for (Monster m : monsters) {
            m.update();
        }

        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Monster> monstersToRemove = new ArrayList<>();
        for (Monster m : monsters) {
            for (Bullet b : bullets) {
                if (m.isHit(b)) {
                    m.takeDamage(1);
                    bulletsToRemove.add(b);
                    if (m.isDead()) {
                        monstersToRemove.add(m);
                    }
                }
            }
        }

        if (!gameOver) {
            for (Monster m : monsters) {
                double distX = (m.getX() + 20) - (player.getX() + player.getSize() / 2);
                double distY = (m.getY() + 20) - (player.getY() + player.getSize() / 2);
                double distance = Math.sqrt(distX * distX + distY * distY);

                if (distance < 30) {
                    player.takeDamage(m.getDamage());
                    monsters.remove(m);
                    break;
                }
            }

            if (player.getHealth() <= 0) {
                gameOver = true;
                timer.stop();
            }
        }

        bullets.removeAll(bulletsToRemove);
        monsters.removeAll(monstersToRemove);

        repaint();
    }

    private void spawnMonster() {
        int side = (int) (Math.random() * 4);
        int x = 0, y = 0;
    
        switch (side) {
            case 0: // Haut
                x = (int) (Math.random() * getWidth());
                y = 0;
                break;
            case 1: // Bas
                x = (int) (Math.random() * getWidth());
                y = getHeight();
                break;
            case 2: // Gauche
                x = 0;
                y = (int) (Math.random() * getHeight());
                break;
            case 3: // Droite
                x = getWidth();
                y = (int) (Math.random() * getHeight());
                break;
        }
    
        // Sélection aléatoire du type de monstre
        MonsterType[] types = MonsterType.values();
        MonsterType randomType = types[(int) (Math.random() * types.length)];
    
        monsters.add(new Monster(x, y, randomType, player));
    }

    // Affichage de tout les éléments (joueur, balles, monstres, barre de vie)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        bullets.forEach(b -> b.draw(g));
        monsters.forEach(m -> m.draw(g));

        g.setColor(Color.RED);
        g.fillRect(10, 10, 200, 20);
        g.setColor(Color.GREEN);
        int lifeBarWidth = (int) (200 * ((double) player.getHealth() / player.getMaxHealth()));
        g.fillRect(10, 10, lifeBarWidth, 20);
        g.setColor(Color.WHITE);
        g.drawRect(10, 10, 200, 20);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 180, getHeight() / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
