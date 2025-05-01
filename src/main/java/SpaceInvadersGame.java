import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// MAIN CLASS for SPACE INVADERS GAME
public class SpaceInvadersGame extends JFrame {
    private final ArrayList<Enemy> enemies = new ArrayList<>(65); // ENEMY ALIENS (set to 65)
    private final ArrayList<Bullet> bullets = new ArrayList<>();  // BULLETS (PLAYER & ENEMY)
    private final Player player;
    private int descentCounter = 0, enemyDirection = 1, score = 0; // ENEMY MOVEMENT & SCORE
    private boolean gameOver;  // CHECKS if GAME OVER
    private final JLabel livesLabel; // PLAYER LIVES
    private final JLabel scoreLabel;  // PLAYER SCORE
    private final Timer timer; // GAME LOOP
    private long lastShotTime;  // LAST PLAYER SHOT (cooldown)

    // INITIALIZES GAME WINDOW, UI, & STATE
    public SpaceInvadersGame() {
        setTitle("Space Invaders");
        setSize(1104, 690);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GamePanel panel = new GamePanel();
        panel.setBackground(Color.BLACK);
        add(panel);
        player = new Player(350, 3); // PLAYER STARTs -> (X=350 WITH 3 LIVES)
        livesLabel = new JLabel("Lives: " + player.getLives());
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setBounds(10, 10, 100, 20);
        panel.add(livesLabel);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(994, 10, 100, 20);
        panel.add(scoreLabel);

        // ENEMY GRID (**5X13** for a TOTAL of 65) <--apt to change
        for (int row = 0; row < 5; row++)
            for (int col = 0; col < 13; col++)
                enemies.add(new Enemy(50 + col * 65, 30 + row * 72, row == 0 ? 3 : row <= 2 ? 2 : 1));

        // HANDLES PLAYER INPUT (MOVEMENT/SHOOTING)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;
                if (e.getKeyCode() == KeyEvent.VK_LEFT && player.getX() > 0) player.move(-10);
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getX() < 1050) player.move(10);
                else if (e.getKeyCode() == KeyEvent.VK_SPACE && System.currentTimeMillis() - lastShotTime >= 200) {
                    bullets.add(new Bullet(player.getX() + 27, 575, true));
                    lastShotTime = System.currentTimeMillis();
                }
                panel.repaint();
            }
        });

        // STARTS GAME LOOP (UPDATES EVERY 50MS)
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

    // UPDATES GAME (EACH FRAME)
    private void updateGame() {
        // MOVES ENEMIES HORIZONTALLY & LOOKS for REVERSAL
        boolean reverse = false;
        for (Enemy e : enemies) {
            e.setX(e.getX() + e.getLevel() * enemyDirection);
            if (e.getX() <= 0 || e.getX() >= 1050) reverse = true;
        }
        if (reverse) enemyDirection = -enemyDirection;

        // LOWERS ENEMIES **EVERY 100 FRAMES** (change for difficulty...levels 2 & up!)
        if (++descentCounter >= 100) {
            for (Enemy e : enemies) {
                e.setY(e.getY() + 10);
                if (e.getY() >= 575) {
                    gameOver = true;
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Game Over! Enemies reached the player.\nScore: " + score);
                    dispose();
                    return;
                }
            }
            descentCounter = 0;
        }

        // UPDATES BULLETS & LOOKS for COLLISIONS
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.move();
            if (b.getY() < 0 || b.getY() > 690) {
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

        // ENEMY SHOOTING (7% CHANCE PER FRAME)
        if (Math.random() < 0.07 && !enemies.isEmpty()) {
            Enemy e = enemies.get((int) (Math.random() * enemies.size()));
            bullets.add(new Bullet(e.getX() + e.getWidth() / 2, e.getY() + e.getHeight(), false));
        }

        // CHECKS for WIN CONDITION (ALL ENEMIES DEFEATED)
        if (enemies.isEmpty()) {
            gameOver = true;
            timer.stop();
            JOptionPane.showMessageDialog(this, "You Win! All enemies defeated.\nScore: " + score);
            dispose();
        }
    }

    // RENDERS OBJECTS on SCREEN
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Enemy e : enemies) {
                // ENEMY IMAGE
                g.drawImage(e.getImage(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), null);
            }
            // PLAYER IMAGE
            g.drawImage(player.getImage(), player.getX(), 575, 54, 36, null);
            for (Bullet b : bullets) {
                g.setColor(b.isPlayer() ? new Color(50, 205, 50) : new Color(255, 165, 0));
                g.fillRect(b.getX(), b.getY(), 5, 9); // REDUCED 50% FROM 9X18
            }
        }
    }

    // LAUNCHES the GAME ... Pew! Pew! 💥💥
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpaceInvadersGame::new);
    }
}
