package ex0009;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class BurritoSprite extends EnemySprite {

    public BurritoSprite() throws IOException {
        super(
                "GreatBurrito.png",
                new Rectangle2D.Float(8, 57, 85, 334),
                64
        );
    }
}
