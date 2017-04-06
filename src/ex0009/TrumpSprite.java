package ex0009;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class TrumpSprite extends Sprite {

    public TrumpSprite() throws IOException {
        super(
                "TrumpRechts1.0.png",
                new Rectangle2D.Float(18, 26, 41, 92)
        );
    }
    
    public boolean isCollidingWith(Sprite otherSprite) {
        return false; // TODO: Implement.
    }

}
