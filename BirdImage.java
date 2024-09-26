import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


public class BirdImage {
    private static final Logger LOGGER = Logger.getLogger(BirdImage.class.getName());

    private BufferedImage imgWingsUp = null;
    private BufferedImage imgWingsDown = null;
    private BufferedImage img = null;
    private static final int bird_dia = 40;
    public static int x = (GamePanel.WIDTH / 2) - bird_dia/2;
    public static int y = GamePanel.HEIGHT / 2;

    private static int speed = 2;
    private final int acce = 1;

    public BirdImage() {
        LoadImage();
        img=imgWingsUp;
    }

    private void LoadImage() {
        try {
            imgWingsUp = ImageIO.read(new File("Images/bird2.png"));
            imgWingsDown = ImageIO.read(new File("Images/bird1.png"));

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to load wall image", ex);
        }
    }

    public void drawBird(Graphics g) {
        int newWidth = 55;
        int newHeight = 45;
        g.drawImage(img, x, y, newWidth, newHeight, null);
    }
    public void BirdMovement(){

        if (y >=0 && y<=GamePanel.HEIGHT) {
            speed += acce;
            y += speed;
        } else{

            boolean option = GamePanel.popUpMessage();

            if(option){
                try{
                    Thread.sleep(500);
                }catch(Exception ex){
                    LOGGER.log(Level.SEVERE, "Failed to load wall image", ex);
                }
                reset();
            }else{
                //close window
                JFrame frame = MainBird.getWindow();
                frame.dispose();
                MainBird.timer.stop();
            }
        }
    }

    public void goUpwards(){
        speed = -14;
    }

    public void switchToWingsDown() {
        img = imgWingsDown;
    }

    public void switchToWingsUp() {
        img = imgWingsUp;
    }

    public static void reset() {
        speed = 2;
        y = GamePanel.HEIGHT/2;
        GamePanel.GameOver = true;
        GamePanel.score = 0;
    }

    public static Rectangle getBirdRect() {

        return new Rectangle(x, y, bird_dia, 40);
    }
}
