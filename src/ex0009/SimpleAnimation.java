package ex0009;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
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
    private int remainingTrumpJumpTicks;
    private BufferedImage backgroundImage, wallImage;
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
        canvas.background();
        frame.pack();
        frame.setVisible(true);

        LocalDateTime now = LocalDateTime.now();
        int currentHour = now.getHour();
        // TODO: Change background image depending on hour.1
        gameLoop();

    }

    public void gameLoop() {
        while (!gameOver) {
            gameOver = trumpSprite.isCollidingWith(mexicanSprite);
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
            trumpSprite.setX(trumpSprite.getX() - 10);
            if (trumpSprite.getX() < 0) {
                trumpSprite.setX(0);
            }
        }
        if (playerKeystate.isJumpPressed) {
            if (!trumpSprite.isJumping()) {
                trumpSprite.setRemainingJumpTicks(60);
            }
        }
        if (trumpSprite.isJumping()) {
            trumpSprite.setRemainingJumpTicks(trumpSprite.getRemainingJumpTicks() - 1);

        }
    }

    private void moveMexicans() {
        mexicanSprite.setX(mexicanSprite.getX() - 2);

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
        private static final int WALL_Y = 465;

        private boolean isDebug = true;

        public Canvas() throws IOException {
            super();

            setPreferredSize(new Dimension(WIDTH, HEIGHT));
        }

        private void drawSpriteCollisionRectangle(Graphics2D g2d, Sprite sprite) {
            final Rectangle2D relativeCollisionRectangle = sprite.getRelativeCollisionRectangle();
            g2d.drawRect(
                    (int) relativeCollisionRectangle.getX(),
                    (int) relativeCollisionRectangle.getY(),
                    (int) relativeCollisionRectangle.getWidth(),
                    (int) relativeCollisionRectangle.getHeight());
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            g2d.translate((-1) * (trumpSprite.getX() - 200), 0);
            g2d.drawImage(backgroundImage, trumpSprite.getX() - 300, 0, null);

            for (int i = -1; i < 50; i++) {
                int wallX = i * wallImage.getWidth();
                g2d.drawImage(wallImage, wallX, WALL_Y, null);
                // HACK: Draw wall again below to avoid gray area.
                // FIXME: Increase wall height and remove hack below.
                g2d.drawImage(wallImage, wallX, WALL_Y + wallImage.getHeight(), null);
            }

            g2d.drawImage(trumpSprite.getImage(), trumpSprite.getX(), trumpSprite.getY(), null);
            g2d.drawImage(mexicanSprite.getImage(), mexicanSprite.getX(), mexicanSprite.getY(), null);

            g2d.setColor(Color.red);
            drawSpriteCollisionRectangle(g2d, trumpSprite);
            drawSpriteCollisionRectangle(g2d, mexicanSprite);

        }

        private void background() throws IOException {
            URL imageUrl = getClass().getResource("../images/Hintergrund.jpg");
            backgroundImage = ImageIO.read(imageUrl);
            URL wallUrl = getClass().getResource("../images/GreatWall.png");
            wallImage = ImageIO.read(wallUrl);
        }
    }
}
