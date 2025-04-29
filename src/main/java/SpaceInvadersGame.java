import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// REPRESENTS THE MAIN GAME CLASS FOR SPACE INVADERS
public class SpaceInvadersGame extends JFrame {
    private final ArrayList<Enemy> enemies = new ArrayList<>(40); // STORES ALL ENEMY ALIENS
    private final ArrayList<Bullet> bullets = new ArrayList<>();  // STORES ALL BULLETS (PLAYER AND ENEMY)
    private final Player player; // PLAYER OBJECT
    private int descentCounter = 0, enemyDirection = 1, score = 0; // ENEMY MOVEMENT TRACKING AND SCORE
    private boolean gameOver; // FLAGS IF GAME IS OVER
    private final JLabel livesLabel; // DISPLAYS PLAYER LIVES
    private final JLabel scoreLabel; // DISPLAYS PLAYER SCORE
    private final Timer timer; // DRIVES THE GAME LOOP
    private long lastShotTime; // TRACKS LAST PLAYER SHOT FOR COOLDOWN

    // INITIALIZES GAME WINDOW, UI, AND STATE
    public SpaceInvadersGame() {
        setTitle("Space Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GamePanel panel = new GamePanel();
        panel.setBackground(Color.BLACK);
        add(panel);
        player = new Player(350, 3); // INITIALIZES PLAYER AT X=350 WITH 3 LIVES
        livesLabel = new JLabel("Lives: " + player.getLives());
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setBounds(10, 10, 100, 20);
        panel.add(livesLabel);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(690, 10, 100, 20);
        panel.add(scoreLabel);

        // CREATES 5X8 GRID OF ENEMIES
        for (int row = 0; row < 5; row++)
            for (int col = 0; col < 8; col++)
                enemies.add(new Enemy(100 + col * 60, 50 + row * 50, row == 0 ? 3 : row <= 2 ? 2 : 1));

        // HANDLES PLAYER INPUT (MOVEMENT AND SHOOTING)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;
                if (e.getKeyCode() == KeyEvent.VK_LEFT && player.getX() > 0) player.move(-10);
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getX() < 750) player.move(10);
                else if (e.getKeyCode() == KeyEvent.VK_SPACE && System.currentTimeMillis() - lastShotTime >= 200) {
                    bullets.add(new Bullet(player.getX() + 15, 500, true));
                    lastShotTime = System.currentTimeMillis();
                }
                panel.repaint();
            }
        });

        // STARTS THE GAME LOOP (UPDATES EVERY 50MS)
        timer = new Timer(50, e -> {
            if (!gameOver) {
                updateGame();
                panel.repaint();
            }
        });
        timer.start();
        setFocusable(true);
        setVisible(true);
    }

    // UPDATES GAME STATE EACH FRAME
    private void updateGame() {
        // MOVES ENEMIES HORIZONTALLY AND CHECKS FOR DIRECTION REVERSAL
        boolean reverse = false;
        for (Enemy e : enemies) {
            e.setX(e.getX() + e.getLevel() * enemyDirection);
            if (e.getX() <= 0 || e.getX() >= 750) reverse = true;
        }
        if (reverse) enemyDirection = -enemyDirection;

        // LOWERS ENEMIES EVERY 100 FRAMES
        if (++descentCounter >= 100) {
            for (Enemy e : enemies) {
                e.setY(e.getY() + 10);
                if (e.getY() >= 500) {
                    gameOver = true;
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Game Over! Enemies reached the bottom.\nScore: " + score);
                    dispose();
                    return;
                }
            }
            descentCounter = 0;
        }

        // UPDATES BULLETS AND CHECKS FOR COLLISIONS
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.move();
            if (b.getY() < 0 || b.getY() > 600) {
                bullets.remove(i);
                continue;
            }
            if (b.isPlayer()) {
                for (int j = enemies.size() - 1; j >= 0; j--) {
                    Enemy e = enemies.get(j);
                    if (b.getBounds().intersects(e.getBounds())) {
                        e.setHealth(e.getHealth() - 1);
                        if (e.getHealth() <= 0) {
                            enemies.remove(j);
                            score += e.getLevel() == 3 ? 30 : e.getLevel() == 2 ? 20 : 10;
                            scoreLabel.setText("Score: " + score);
                        }
                        bullets.remove(i);
                        break;
                    }
                }
            } else {
                if (b.getBounds().intersects(player.getHitbox())) {
                    player.setLives(player.getLives() - 1);
                    livesLabel.setText("Lives: " + player.getLives());
                    bullets.remove(i);
                    if (player.getLives() <= 0) {
                        gameOver = true;
                        timer.stop();
                        JOptionPane.showMessageDialog(this, "Game Over! You ran out of lives.\nScore: " + score);
                        dispose();
                        return;
                    }
                }
            }
        }

        // TRIGGERS ENEMY SHOOTING (5% CHANCE PER FRAME)
        if (Math.random() < 0.05 && !enemies.isEmpty()) {
            Enemy e = enemies.get((int) (Math.random() * enemies.size()));
            bullets.add(new Bullet(e.getX() + e.getWidth() / 2, e.getY() + e.getHeight(), false));
        }

        // CHECKS FOR WIN CONDITION (ALL ENEMIES DEFEATED)
        if (enemies.isEmpty()) {
            gameOver = true;
            timer.stop();
            JOptionPane.showMessageDialog(this, "You Win! All enemies defeated.\nScore: " + score);
            dispose();
        }
    }

    // RENDERS GAME OBJECTS ON SCREEN
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Enemy e : enemies) {
                g.setColor(e.getLevel() == 3 ? new Color(255, 0, 255) : e.getLevel() == 2 ? new Color(0, 255, 255) : new Color(128, 0, 128));
                g.fillRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
            }
            g.setColor(new Color(0, 255, 200));
            g.fillRect(player.getX(), 500, 30, 20);
            for (Bullet b : bullets) {
                g.setColor(b.isPlayer() ? new Color(50, 205, 50) : new Color(255, 165, 0));
                g.fillRect(b.getX(), b.getY(), 5, 10);
            }
        }
    }

    // LAUNCHES THE GAME
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpaceInvadersGame::new);
    }
}