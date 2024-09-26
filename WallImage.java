import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class WallImage {
    private static final Logger LOGGER = Logger.getLogger(WallImage.class.getName());
    
    private final Random r = new Random();
    public int X;
    public int Y = r.nextInt(GamePanel.HEIGHT - 400) + 200;
    private final int width_Wall = 45;
    private int height = GamePanel.HEIGHT - Y;
    private final int gap = 220;

    public static int speed = -10;

    private BufferedImage img = null;

    public WallImage(int X) {
        this.X = X;
        LoadImage();
    }

    private void LoadImage() {
        try {
            img = ImageIO.read(new File("C:/Users/Kartik/OneDrive/Desktop/college/JAVA/MiniProject/Images/pipe.png"));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to load wall image", ex);
        }
    }

    public void drawWall(Graphics g) {
        int wallWidth = 130;
        int wallHeight = 800;

        g.drawImage(img, X, Y, wallWidth, wallHeight, null);
        g.drawImage(img, X, (-GamePanel.HEIGHT) + (Y - gap), wallWidth, wallHeight, null);
    }

    public void wallMovement() {
        X += speed;

        if (X <= -width_Wall) {
            X = GamePanel.WIDTH + width_Wall;
            Y = r.nextInt(GamePanel.HEIGHT - 400) + 220;
            height = GamePanel.HEIGHT - Y;
        }

        Rectangle lowerRect = new Rectangle(X, Y, width_Wall, height);
        Rectangle upperRect = new Rectangle(X, 0, width_Wall, GamePanel.HEIGHT - (height + gap));

        if (lowerRect.intersects(BirdImage.getBirdRect()) || upperRect.intersects(BirdImage.getBirdRect())) {
            boolean option = GamePanel.popUpMessage();

            if (option) {
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Failed to load wall image", ex);
                }
                BirdImage.reset();
                wall_Reset();
            } else {
                // Close window
                JFrame frame = MainBird.getWindow();
                frame.dispose();
                MainBird.timer.stop();
            }
        }
    }

    public int getWidth() {
        return width_Wall;
    }

    private void wall_Reset() {
        Y = r.nextInt(GamePanel.HEIGHT - 400) + 200;
        height = GamePanel.HEIGHT - Y;
        GamePanel.GameOver = true;
    }
}
