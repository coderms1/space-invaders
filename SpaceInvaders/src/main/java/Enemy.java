// File: Enemy.java
import java.awt.*;

// Represents an ENEMY ALIEN in the game
public class Enemy {
    int x, y, level, health, width, height;

    // Initializes enemy POSITION, LEVEL, & HEALTH
    public Enemy(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.health = level;
        this.width = level == 3 ? 30 : level == 2 ? 25 : 20;
        this.height = level == 3 ? 20 : level == 2 ? 15 : 10;
    }

    // Returns a COLLISION RECTANGLE for the ENEMY (keep in bounds)
    Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}