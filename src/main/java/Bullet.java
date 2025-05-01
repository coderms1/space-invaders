import java.awt.*;

public class Bullet {
    private int x, y; // POSITION
    private boolean isPlayer; // DETERMINES 'IS PLAYER-FIRED' or FALSE

    // BULLET POSITION & TYPE
    public Bullet(int x, int y, boolean isPlayer) {
        this.x = x;
        this.y = y;
        this.isPlayer = isPlayer;
    }

    // UPDATES BULLET (BASED on TYPE --> 'IS PLAYER?' or NOT)
    void move() {
        y += isPlayer ? -10 : 5;
    }

    // COLLISION RECTANGLE
    Rectangle getBounds() {
        return new Rectangle(x, y, 5, 9); // REDUCED 50% FROM 9X18
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

    // toString() - RETURNS STRING of BULLET
    @Override
    public String toString() {
        return "Bullet [x=" + x + ", y=" + y + ", isPlayer=" + isPlayer + "]";
    }
}
