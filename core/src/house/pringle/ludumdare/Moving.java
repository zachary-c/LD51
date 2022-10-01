package house.pringle.ludumdare;

public abstract class Moving extends GameObject {

    protected double velX;
    protected double velY;

    public Moving(int x, int y, double velX, double velY) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }
    public void setVelY(double velY) {
        this.velY = velY;
    }
}
