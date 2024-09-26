import javax.swing.*;

public class MainBird {

    private static JFrame window;
    public static Timer timer, timer2, startTimer;
    private static int proceed = 4;

    public MainBird() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(GamePanel.WIDTH, GamePanel.HEIGHT);
        window.setLocationRelativeTo(null);
        window.setTitle("Flappy Bird");
        window.setResizable(false);
        window.setVisible(true);
    }

    private void rendering() {
        MenuPanel mp = new MenuPanel();
        GamePanel gp = new GamePanel();

        timer = new Timer(20, e -> {
            gp.repaint();
            gp.Move();
        });

        window.add(mp);
        window.setVisible(true);

        // Use a Timer instead of Thread.sleep() for waiting
        startTimer = new Timer(10, e -> {
            if (mp.StartingPoint) {
                startTimer.stop();
                window.remove(mp);
                window.add(gp);
                gp.setVisible(true);
                window.revalidate();

                timer2 = new Timer(700, ev -> {
                    proceed--;
                    GamePanel.proceed = proceed;
                    GamePanel.starting = true;
                    gp.repaint();
                    if (proceed == 0) {
                        timer2.stop();
                        timer.start();
                        GamePanel.starting = false;
                    }
                });
                timer2.start();
            }
        });
        startTimer.start();
    }

    

    public static JFrame getWindow() {
        return window;
    }

    public static void main(String[] args) {
        MainBird mb = new MainBird();
        mb.rendering();
    }
}
