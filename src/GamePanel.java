import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static int WIDTH = 600;
    public static int HEIGHT = 400;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private int FPS = 30;
    private int targetTime = 1000/FPS;

    private TileMap tileMap;
    private Player player;

    // constructor
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    // function
    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    @Override
    public void run() {

        init();

        long startTime;
        long urdTime;
        long waitTime;

        //game loop
        while(running){

            startTime = System.nanoTime();

            gameUpdate();
            gameRender();
            gameDraw();

            urdTime = (System.nanoTime() - startTime) / 1_000_000;
            waitTime = targetTime - urdTime;

            if(waitTime<=0) waitTime = 5;

            try{
                Thread.sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void init(){
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        tileMap = new TileMap("res/testmap.txt", 32);
        player = new Player(tileMap);
        player.setX(50);
        player.setY(50);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void gameUpdate(){
        tileMap.update();
        player.update();
    }
    private void gameRender(){
        tileMap.draw(g);
        player.draw(g);
    }
    private void gameDraw(){
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_LEFT){
            player.setLeft(true);
        }
        if(code == KeyEvent.VK_RIGHT){
            player.setRight(true);
        }
        if(code == KeyEvent.VK_UP){
            player.setJumping(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_LEFT){
            player.setLeft(false);
        }
        if(code == KeyEvent.VK_RIGHT){
            player.setRight(false);
        }
    }
}