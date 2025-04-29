import java.awt.*;

// Represents an ENEMY ALIEN in the game
public class Enemy {
    private int x, y, level, health, width, height;

    // Initializes enemy POSITION, LEVEL, & HEALTH
    public Enemy(int x, int y, int level) {
        this.x = x;
        this.y = y;
        // file name (image)
        this.level = level;
        this.health = level;
        this.width = level == 3 ? 30 : level == 2 ? 25 : 20;
        this.height = level == 3 ? 20 : level == 2 ? 15 : 10;
    }

    // Returns a COLLISION RECTANGLE for the ENEMY (keep in bounds)
    Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // toString method
    @Override
    public String toString() {
        return "Enemy [x=" + x + ", y=" + y + ", level=" + level + ", health=" + health +
                ", width=" + width + ", height=" + height + "]";
    }
}