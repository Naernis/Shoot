import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private Player player;
    private int score = 0;

    private ArrayList<Bullet> bullets;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private long lastMonsterSpawn = 0;

    private boolean gameOver = false;

    private BufferedImage background;

    // Écran du jeu
    public GamePanel() {
        setPreferredSize(new Dimension(700, 700));
        setBackground(Color.BLACK);
        player = new Player(250, 250);
        bullets = new ArrayList<>();
        timer = new Timer(16, this);
        timer.start();

        //Fond du jeux
        try {
            background = ImageIO.read(getClass().getResource("/resources/background.jpg"));
        } catch (IOException e) {
                e.printStackTrace();
        }

    addMouseListener(new MouseHandler(player, bullets)); 
    addKeyListener(new KeyboardHandler(player));
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
        if (currentTime - lastMonsterSpawn >= getSpawnInterval()) {
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
                        score += m.getPoints();
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

    //Ajout des monstre plus fort au fur est à mesure du jeu
        ArrayList<MonsterType> availableTypes = new ArrayList<>();
        availableTypes.addAll(Arrays.asList(MonsterType.BASIC, MonsterType.HIT, MonsterType.FAST));

        if (score >= 100) {
            availableTypes.addAll(Arrays.asList(MonsterType.STRANGE, MonsterType.TANK, MonsterType.LOVE));
        }
        if (score >= 250) {
            availableTypes.add(MonsterType.PERFECT); 
        }
        if (score >= 450 ) {
            availableTypes.add(MonsterType.BOSS);
        }
    
        MonsterType randomType = availableTypes.get((int) (Math.random() * availableTypes.size()));

        
        double speedMultiplier = 1.0 + (score / 1000.0); //Ajout de +1% de vitesse tout les 100 points
        monsters.add(new Monster(x, y, randomType, speedMultiplier, player));
    }

    // Affichage de tout les éléments (joueur, fond, balles, monstres, score, barre de vie)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }

        player.draw(g);
        bullets.forEach(b -> b.draw(g));
        monsters.forEach(m -> m.draw(g));

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 15, getWidth() - 10); 

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
            g.drawString("GAME OVER", getWidth() / 3, getHeight() / 2);
        }
    }

    private long getSpawnInterval() { //Ajout de l'intervalle d'apparition des monstres avec le score
        return 1800 - (score * 1);  
    }
}
