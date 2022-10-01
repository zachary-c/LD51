package house.pringle.ludumdare;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {

    /**
     * ID for the next GameObject. Every new GameObject "takes a number" and increments this by one. Count of
     * all GameObjects created.
     */
    private static int nextID;

    protected int x;
    protected int y;
    protected int id;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        id = nextID;
        nextID++;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void dispose();

    public int getX() { return x; }
    public int getY() { return y; }

    public abstract Rectangle getRectangle();

    public abstract void receiveNotification(AnimationNotification notification, String animation);
}
