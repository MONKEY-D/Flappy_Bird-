import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(GamePanel.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    public static boolean GameOver = false;
    public static int score = 0;
    public static boolean starting = false;
    public static int proceed = -1;
    public static boolean paused = false;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;

    private int xCoor = 0;
    private BufferedImage img;

    BirdImage bi = new BirdImage();
    WallImage wi = new WallImage(GamePanel.WIDTH + 200);
    WallImage wi2 = new WallImage(GamePanel.WIDTH + 200 + GamePanel.WIDTH / 2);

    // Flags to track when the bird has passed a wall
    private boolean passedWi = false;
    private boolean passedWi2 = false;

    public GamePanel() {
        LoadImage();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!GamePanel.paused) { // Check if the game is not paused
                    bi.goUpwards();
                    bi.switchToWingsDown();
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!GamePanel.paused) { // Check if the game is not paused
                    bi.switchToWingsUp();
                    repaint();
                }
            }
        });
    }

    private void LoadImage() {
        try {
            img = ImageIO.read(new File("Images/background1.png"));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to load background- image", ex);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < getWidth() / img.getWidth() + 2; i++) {
            g.drawImage(img, xCoor + i * img.getWidth(), 0, null);
        }

        bi.drawBird(g);
        wi.drawWall(g);
        wi2.drawWall(g);

        g.setFont(new Font("Tahoma", Font.BOLD, 40));
        g.drawString("Score: " + score, 20, 50); // Show score

        if (starting) {
            g.setFont(new Font("Tahoma", Font.BOLD, 150));
            g.drawString(Integer.toString(proceed), WIDTH / 2 - 70, 300);
        }
    }

    public void Move() {
        if (!GamePanel.paused) { // Check if the game is not paused
            bi.BirdMovement();
            wi.wallMovement();
            wi2.wallMovement();

            if (GameOver) {
                score = 0; // Reset score on game over
                passedWi = false;
                passedWi2 = false;
                wi.X = GamePanel.WIDTH;
                wi2.X = GamePanel.WIDTH + (GamePanel.WIDTH / 2);

                startRestartCountdown();
            }

            xCoor += WallImage.speed;
            if (xCoor == -6100) {
                xCoor = 0;
            }

            // Check if the bird has passed the first wall
            if (!passedWi && BirdImage.x > wi.X + wi.getWidth()) {
                score += 1;
                passedWi = true;
            } else if (BirdImage.x < wi.X + wi.getWidth()) {
                passedWi = false; // Reset flag if bird is not past the wall
            }

            // Check if the bird has passed the second wall
            if (!passedWi2 && BirdImage.x > wi2.X + wi2.getWidth()) {
                score += 1;
                passedWi2 = true;
            } else if (BirdImage.x < wi2.X + wi2.getWidth()) {
                passedWi2 = false; // Reset flag if bird is not past the wall
            }
        }
    }

    private void startRestartCountdown() {
        proceed = 3;
        starting = true;
        GamePanel.paused = true; // Pause the game

        Timer restartTimer = new Timer(1000, e -> {
            proceed--;
            repaint();
            if (proceed <= 0) {
                ((Timer) e.getSource()).stop();
                starting = false;
                GamePanel.paused = false; // Unpause the game
                restartGame();
            }
        });
        restartTimer.start();
    }

    private void restartGame() {
        wi = new WallImage(GamePanel.WIDTH + 200);
        wi2 = new WallImage(GamePanel.WIDTH + 200 + GamePanel.WIDTH / 2);
        bi = new BirdImage();
        GameOver = false;
        score = 0;
        passedWi = false;
        passedWi2 = false;
        MainBird.timer.start(); // Start the game timer again
    }

    public static boolean popUpMessage() {
        int result = JOptionPane.showConfirmDialog(null, "GAME OVER, Your Score is " + score + "\n Do you want to Restart?", "Game Over", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
