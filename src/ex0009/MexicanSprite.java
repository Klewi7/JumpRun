
package ex0009;

import java.awt.geom.Rectangle2D;
import java.io.IOException;


public class MexicanSprite extends EnemySprite{

    public MexicanSprite() throws IOException {
        super(
                "LittleMexican1.png", 
                new Rectangle2D.Float(143, 50, 123, 124),
                24
        );
    }
    
}
