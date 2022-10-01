package house.pringle.ludumdare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Moving {

    private Texture texture;
    private Animator animator;

    // format is "folder/name" where name does not include the extension, number OR '_'
    private String[] animations = {"keep_alive/kaladin_ka_r"};

    private static final float KA_SPEED = 0.5f;
    public Player(int x, int y) {
        super(x, y, 0, 0);
        animator = new Animator(this);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("./kaladin/atlas/kaladin.atlas"));

        for (String s : animations) {
            animator.addAnimationByRegion(atlas, s);
        }
        animator.setAnimationSpeed("keep_alive/kaladin_ka_r", KA_SPEED);
    }

    public void render(SpriteBatch b) {
        animator.render(b);
    }
    public void dispose() {

    }
    public Rectangle getRectangle() {
        return animator.getRectangle();
    }
}
