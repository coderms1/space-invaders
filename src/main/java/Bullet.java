import java.awt.*;

// REPRESENTS A BULLET FIRED BY PLAYER OR ENEMY
public class Bullet {
    private int x, y;  // BULLET POSITION
    private boolean isPlayer; // INDICATES IF BULLET IS PLAYER-FIRED

    // INITIALIZES BULLET POSITION AND TYPE
    public Bullet(int x, int y, boolean isPlayer) {
        this.x = x;
        this.y = y;
        this.isPlayer = isPlayer;
    }

    // UPDATES BULLET POSITION BASED ON TYPE (IS PLAYER? OR NOT)
    void move() {
        y += isPlayer ? -10 : 5;
    }

    // RETURNS A COLLISION RECTANGLE FOR THE BULLET
    Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);
    }

    // GETTERS
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    // SETTERS
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    // RETURNS A STRING REPRESENTATION OF THE BULLET
    @Override
    public String toString() {
        return "Bullet [x=" + x + ", y=" + y + ", isPlayer=" + isPlayer + "]";
    }
}