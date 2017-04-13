/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex0009;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 *
 * @author Maxi
 */
public class EnemySprite extends Sprite{
private int kickHeight;
    public EnemySprite(String imageName, Rectangle2D newFailRectangle, int newKickHeight) throws IOException {
        super(imageName, newFailRectangle);
        kickHeight = newKickHeight;
    }

    public int getKickHeight() {
        return kickHeight;
    }
    
}
