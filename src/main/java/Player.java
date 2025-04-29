import java.awt.*;

// REPRESENTS THE PLAYER IN THE GAME
public class Player {
    private int x, lives; // PLAYER POSITION AND LIVES
    private final Rectangle hitbox; // PLAYER HITBOX FOR COLLISION

    // INITIALIZES PLAYER POSITION AND LIVES
    public Player(int x, int lives) {
        this.x = x;
        this.lives = lives;
        this.hitbox = new Rectangle(x, 500, 30, 20);
    }

    // MOVES PLAYER LEFT OR RIGHT
    public void move(int dx) {
        x += dx;
        hitbox.x = x; // UPDATES HITBOX POSITION
    }

    // GETTERS
    public int getX() {
        return x;
    }

    public int getLives() {
        return lives;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    // SETTERS
    public void setX(int x) {
        this.x = x;
        hitbox.x = x; // UPDATES HITBOX POSITION
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    // RETURNS A STRING REPRESENTATION OF THE PLAYER
    @Override
    public String toString() {
        return "Player [x=" + x + ", lives=" + lives + ", hitbox=" + hitbox + "]";
    }
}