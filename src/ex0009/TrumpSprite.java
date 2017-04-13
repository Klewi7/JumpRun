package ex0009;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class TrumpSprite extends Sprite {

    private int remainingJumpTicks;

   
    
    public TrumpSprite() throws IOException {
        super(
                "TrumpRechts1.0.png",
                new Rectangle2D.Float(18, 26, 41, 92)
        );
    }
    
    @Override
    public int getY() {
        int deltaY = (int) (-1.0 / 3 * Math.pow(getRemainingJumpTicks() - 30, 2) + 300);

        return super.getY() - deltaY;
    }

    public boolean isJumping() {
        return remainingJumpTicks != 0;
    };
     
    public int getRemainingJumpTicks() {
        return remainingJumpTicks;
    }

    public void setRemainingJumpTicks(int remainingJumpTicks) {
        this.remainingJumpTicks = remainingJumpTicks;
    }

    public boolean isCollidingWith(EnemySprite enemySprite) {
        Rectangle2D failRectangle = enemySprite.getRelativeCollisionRectangle();
        return getRelativeCollisionRectangle().intersects(failRectangle);
    }
    
    public boolean isKicking(EnemySprite enemySprite){
        double footY = getRelativeCollisionRectangle().getY() + getRelativeCollisionRectangle().getHeight();
        return isCollidingWith(enemySprite) && (footY < enemySprite.getBodyY());
    }

}
