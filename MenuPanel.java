import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
    private static final Logger LOGGER = Logger.getLogger(MenuPanel.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private BufferedImage img = null;
    public boolean StartingPoint = false;

    public MenuPanel(){
        LoadImage();
        //mouse click event
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
            StartingPoint = true;
        }
        });
    }

    private void LoadImage(){
        try{
                img = ImageIO.read(new File("Images/front.png"));
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Failed to load wall image", ex);
        }
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        g.drawImage(img , 0 , 0 , GamePanel.WIDTH, GamePanel.HEIGHT, null);
    }
    
}
