// File: SpaceInvadersGame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SpaceInvadersGame extends JFrame {
    private ArrayList<Enemy> enemies = new ArrayList<>(40);
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int playerX = 350, playerLives = 3, descentCounter = 0, enemyDirection = 1, score = 0;
    private boolean gameOver;
    private JLabel livesLabel, scoreLabel;
    private Timer timer;
    private long lastShotTime;
    private Rectangle player = new Rectangle(playerX, 500, 30, 20);

    // Initializes game window and components
    public SpaceInvadersGame() {
        setTitle("Space Invaders");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GamePanel panel = new GamePanel();
        panel.setBackground(Color.BLACK);
        add(panel);
        livesLabel = new JLabel("Lives: " + playerLives);
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setBounds(10, 10, 100, 20);
        panel.add(livesLabel);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(690, 10, 100, 20);
        panel.add(scoreLabel);

        // Creates 5x8 enemy grid
        for (int row = 0; row < 5; row++)
            for (int col = 0; col < 8; col++)
                enemies.add(new Enemy(100 + col * 60, 50 + row * 50, row == 0 ? 3 : row <= 2 ? 2 : 1));

        // Processes arrow keys and shooting
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;
                if (e.getKeyCode() == KeyEvent.VK_LEFT && playerX > 0) playerX -= 10;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && playerX < 750) playerX += 10;
                else if (e.getKeyCode() == KeyEvent.VK_SPACE && System.currentTimeMillis() - lastShotTime >= 200) {
                    bullets.add(new Bullet(playerX + 15, 500, true));
                    lastShotTime = System.currentTimeMillis();
                }
                player.x = playerX;
                panel.repaint();
            }
        });

        // Starts the game loop
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

    // Updates game state
    private void updateGame() {
        // Moves enemies horizontally
        boolean reverse = false;
        for (Enemy e : enemies) {
            e.x += e.level * enemyDirection;
            if (e.x <= 0 || e.x >= 750) reverse = true;
        }
        if (reverse) enemyDirection = -enemyDirection;

        // Lowers enemies every 100 frames
        if (++descentCounter >= 100) {
            for (Enemy e : enemies) {
                e.y += 10;
                if (e.y >= 500) {
                    gameOver = true;
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Game Over! Enemies reached the bottom.\nScore: " + score);
                    dispose();
                    return;
                }
            }
            descentCounter = 0;
        }

        // Updates bullets and checks collisions
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.move();
            if (b.y < 0 || b.y > 600) {
                bullets.remove(i);
                continue;
            }
            if (b.isPlayer) {
                for (int j = enemies.size() - 1; j >= 0; j--) {
                    Enemy e = enemies.get(j);
                    if (b.getBounds().intersects(e.getBounds())) {
                        e.health--;
                        if (e.health <= 0) {
                            enemies.remove(j);
                            score += e.level == 3 ? 30 : e.level == 2 ? 20 : 10;
                            scoreLabel.setText("Score: " + score);
                        }
                        bullets.remove(i);
                        break;
                    }
                }
            } else {
                if (b.getBounds().intersects(player)) {
                    livesLabel.setText("Lives: " + --playerLives);
                    bullets.remove(i);
                    if (playerLives <= 0) {
                        gameOver = true;
                        timer.stop();
                        JOptionPane.showMessageDialog(this, "Game Over! You ran out of lives.\nScore: " + score);
                        dispose();
                        return;
                    }
                }
            }
        }

        // Triggers enemy shooting
        if (Math.random() < 0.05 && !enemies.isEmpty()) {
            Enemy e = enemies.get((int) (Math.random() * enemies.size()));
            bullets.add(new Bullet(e.x + e.width / 2, e.y + e.height, false));
        }

        // Checks for win condition
        if (enemies.isEmpty()) {
            gameOver = true;
            timer.stop();
            JOptionPane.showMessageDialog(this, "You Win! All enemies defeated.\nScore: " + score);
            dispose();
        }
    }

    // Renders game objects
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Enemy e : enemies) {
                g.setColor(e.level == 3 ? new Color(255, 0, 255) : e.level == 2 ? new Color(0, 255, 255) : new Color(128, 0, 128));
                g.fillRect(e.x, e.y, e.width, e.height);
            }
            g.setColor(new Color(0, 255, 200));
            g.fillRect(playerX, 500, 30, 20);
            for (Bullet b : bullets) {
                g.setColor(b.isPlayer ? new Color(50, 205, 50) : new Color(255, 165, 0));
                g.fillRect(b.x, b.y, 5, 10);
            }
        }
    }
    // Launches the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpaceInvadersGame::new);
    }
}