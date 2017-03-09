package ex0009;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

class Sprite {

    private int x;
    private int y;
    private BufferedImage image;

    public Sprite(String imageName) throws IOException {
        URL imageUrl = getClass().getResource("../images/" + imageName);
        image = ImageIO.read(imageUrl);
    }
    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return image.getWidth();
    }

}
