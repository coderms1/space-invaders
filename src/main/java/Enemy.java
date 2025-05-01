import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Enemy {
    private int x, y, level, health, width, height;
    private final Image image;

    // ENEMY POSITION, LEVEL, HEALTH, & IMAGE
    public Enemy(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.health = level;
        this.width = level == 3 ? 54 : level == 2 ? 45 : 36;
        this.height = level == 3 ? 36 : level == 2 ? 27 : 18;
        Image tempImage = null;
        try {
            String imagePath = "/enemy" + level + ".png";
            tempImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.image = tempImage;
    }

    // COLLISION RECTANGLE (KEEP IN BOUNDS)
    Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // GETTERS
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
    public Image getImage() {
        return image;
    }

    // SETTERS
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

    // toString() - RETURNS STRING of ENEMY
    @Override
    public String toString() {
        return "Enemy [x=" + x + ", y=" + y + ", level=" + level + ", health=" + health +
                ", width=" + width + ", height=" + height + "]";
    }
}
