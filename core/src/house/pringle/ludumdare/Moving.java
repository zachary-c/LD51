package house.pringle.ludumdare;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Moving extends GameObject {

    private double velX;
    private double velY;
    protected double speed;

    public Moving(int x, int y, double velX, double velY, double speed) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.speed = speed;
    }

    @Override
    public void render(SpriteBatch batch) {
        x = (int) (x+(velX*Main.SPRITE_SPEED_SCALE));
        y = (int) (y+(velY*Main.SPRITE_SPEED_SCALE));
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }
    public void setVelY(double velY) {
        this.velY = velY;
    }
    public double getVelY() { return velY; }
    public double getVelX() { return velX; }
}
