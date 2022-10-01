package ludumdare.friday.project2;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

// HashMap yay
import java.util.HashMap;

public class Animator {

    private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    // maps names of animations ("anna_left", "anna_right") to the appropriate Animation Object (a sequence of TextureRegions, really)
    private HashMap<String,Animation<TextureRegion>> animations;
    // keeps track of the current animation that should be playing; the string is a key in the above hashmap
    private String currentAnim;
    private float width, height;
    private float stateTime;

    private final GameObject owner;
    private float projectileAngle;

    private ShapeRenderer shapeRenderer;

    public Animator(GameObject owner) {
        // initalize so the player's x and y match the animation's
        this.owner = owner;

        // instantiate the ArrayList and SpriteBatch
        animations = new HashMap<>();
        spriteBatch = new SpriteBatch();

        // initialize for below
        stateTime = 0.0f;

        if (Project2.HITBOXES) {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setAutoShapeType(true);
        }

    }

    public void addAnimationByRegion(TextureAtlas atlas, String name) {
        // This line takes the given atlas (large sprite file) and loads the animation with the give name ("anna_left_0", "anna_left_1), etc
        Animation<TextureRegion> temp = new Animation<TextureRegion>(0.09f, atlas.findRegions(name), Animation.PlayMode.LOOP);
        // then we put it into the animation hashmap paired with its name
        animations.put(name, temp);

        // if we don't have an animation already
        if (currentAnim == null) {
            // get one, any of them
            currentAnim = animations.keySet().iterator().next();
            //
            height = animations.get(currentAnim).getKeyFrame(0).getRegionHeight() * owner.camera_ratio * Project2.SPRITE_SCALE;
            width = animations.get(currentAnim).getKeyFrame(0).getRegionWidth() * owner.camera_ratio * Project2.SPRITE_SCALE;
        }
    }
    public void setAnimationSpeed(String name, float fps) {
        animations.get(name).setFrameDuration(fps);
    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        currentFrame = animations.get(currentAnim).getKeyFrame(stateTime, true);

        if (owner.hasIFrames()) {
            float frames = ((Player) owner).getIFrames();
            float flash = (float) (Math.abs(Math.sin(frames*10)));
            batch.setColor(1,1,.7f,flash);
            batch.draw(currentFrame, owner.getPosX(), owner.getPosY(), width, height); // Draw current frame at the owner's x and y coords
            batch.setColor(1,1,1,1);
        } else {
            if (currentAnim.equals("proj")) {
                batch.draw(currentFrame, owner.getPosX(), owner.getPosY(), 15,15,
            width*Project2.PROJECTILE_SCALE,height*Project2.PROJECTILE_SCALE, 1, 1, projectileAngle);
            } else {
                batch.draw(currentFrame, owner.getPosX(), owner.getPosY(), width, height); // Draw current frame at the owner's x and y coords
            }
        }

        if (Project2.HITBOXES) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Rectangle me = owner.getRectangle();
            shapeRenderer.rect(me.x, me.y, me.width, me.height);
            shapeRenderer.end();
        }
    }


    public Rectangle getRectangle() {
        return new Rectangle(owner.getPosX()+(width*.1f), owner.getPosY()+(height*.1f), width*.8f, height*.8f);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setCurrentAnim(String currentAnim) {
        // for changing what animation is playing
        this.currentAnim = currentAnim;
    }

    public void setRotation(float angle) {
        projectileAngle = angle;
    }
}
