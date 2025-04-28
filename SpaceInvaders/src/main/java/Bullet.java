// File: Bullet.java
import java.awt.*;

// Represents a BULLET FIRED by PLAYER or ENEMY
public class Bullet {
    int x, y;
    boolean isPlayer;

    // Initializes bullet position and type
    public Bullet(int x, int y, boolean isPlayer) {
        this.x = x;
        this.y = y;
        this.isPlayer = isPlayer;
    }

    // Updates BULLET POSITION based on type (is player? or not)
    void move() {
        y += isPlayer ? -10 : 5;
    }

    // Returns a COLLISION RECTANGLE for the BULLET
    Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);
    }
}