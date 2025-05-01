import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player {
    private int x, lives;  // POSITION & LIVES
    private final Rectangle hitbox; // HITBOX (COLLISION)
    private final Image image;

    // PLAYER POSITION, LIVES, & IMAGE
    public Player(int x, int lives) {
        this.x = x;
        this.lives = lives;
        this.hitbox = new Rectangle(x, 575, 54, 36);
        Image tempImage = null;
        try {
            tempImage = ImageIO.read(getClass().getResource("/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.image = tempImage;
    }

    // MOVES PLAYER >> LEFT/RIGHT <<
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
    public Image getImage() {
        return image;
    }

    // SETTERS
    public void setX(int x) {
        this.x = x;
        hitbox.x = x; // UPDATES HITBOX POSITION
    }
    public void setLives(int lives) {
        this.lives = lives;
    }

    // toString() - RETURNS STRING of PLAYER
    @Override
    public String toString() {
        return "Player [x=" + x + ", lives=" + lives + ", hitbox=" + hitbox + "]";
    }
}
