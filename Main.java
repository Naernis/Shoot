import javax.swing.*;

public class Main {
    // Lancement du jeu shoot
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shoot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GamePanel());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}