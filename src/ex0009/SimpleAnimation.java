package ex0009;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimpleAnimation {

    private static final int TICK_IN_MILLISECOND = 1000 / 60;

    private Canvas canvas;
    private boolean gameOver;
    private boolean trumpIsJumping;
    private int remainingTrumpJumpTicks;
    private PlayerKeystate playerKeystate;
    private TrumpSprite trumpSprite;
    private MexicanSprite mexicanSprite;

    public static void main(String[] args) throws IOException {
        SimpleAnimation ani = new SimpleAnimation();
        ani.go();
    }

    public void go() throws IOException {
        JFrame frame = new JFrame();
        playerKeystate = new PlayerKeystate();
        
        trumpSprite = new TrumpSprite();
        trumpSprite.setX((Canvas.WIDTH - trumpSprite.getWidth()) / 2);
        trumpSprite.setY(350);
        
        mexicanSprite = new MexicanSprite();
        mexicanSprite.setX(400);
        mexicanSprite.setY(300);
        canvas = new Canvas();

        Music backgroundMusic = new Music();
        Thread thread = new Thread(backgroundMusic);
        thread.start();
        
        frame.addKeyListener(playerKeystate);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setTitle("Trump'n'Run");
        frame.pack();
        frame.setVisible(true);

        LocalDateTime now = LocalDateTime.now();
        int currentHour = now.getHour();
        // TODO: Change background image depending on hour.1
        gameLoop();

    }

    public void gameLoop() {
        while (!gameOver) {
            moveTrump();
            moveMexicans();
            canvas.repaint();
            sleepATick();
        }
    }

    private void sleepATick() {
        try {
            Thread.sleep(TICK_IN_MILLISECOND);
        } catch (InterruptedException error) {
            error.printStackTrace();
        }
    }

    private void moveTrump() {
        if (playerKeystate.isRightPressed) {
            trumpSprite.setX(trumpSprite.getX() + 10);
            if ((trumpSprite.getX() + trumpSprite.getWidth()) > canvas.getWidth()) {
                trumpSprite.setX(canvas.getWidth() - trumpSprite.getWidth());
            }
        }
        if (playerKeystate.isLeftPressed) {
            trumpSprite.setX( trumpSprite.getX() - 10);
            if (trumpSprite.getX() < 0) {
                trumpSprite.setX(0);
            }
        }
        if (playerKeystate.isJumpPressed) {
            if (!trumpIsJumping) {
                trumpIsJumping = true;
                remainingTrumpJumpTicks = 60;
            }
        }
        if (trumpIsJumping) {
            remainingTrumpJumpTicks--;
            if (remainingTrumpJumpTicks == 0) {
                trumpIsJumping = false;
            }
        }
    }
    
    private void moveMexicans(){
      mexicanSprite.setX(mexicanSprite.getX()-2);
      
    }
    
    class PlayerKeystate implements KeyListener {

        public boolean isLeftPressed;  // TODO: Use getXXXX()
        public boolean isRightPressed;
        public boolean isJumpPressed;

        public PlayerKeystate() {
            // Do nothing.
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Do nothing.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                isRightPressed = true;
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                isLeftPressed = true;
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                isJumpPressed = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                isRightPressed = false;
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                isLeftPressed = false;
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                isJumpPressed = false;
            }
        }
    }

    public class Canvas extends JPanel {

        private static final int HEIGHT = 600;
        private static final int WIDTH = 800;

        public Canvas() throws IOException {
            super();
            
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            int trumpDeltaY = (int) (-1.0 / 3 * Math.pow(remainingTrumpJumpTicks - 30, 2) + 300);
            g2d.drawImage(trumpSprite.getImage(), trumpSprite.getX(), trumpSprite.getY() - trumpDeltaY, null);
            g2d.drawImage(mexicanSprite.getImage(),mexicanSprite.getX() , mexicanSprite.getY(),null);
        }
    }
}
