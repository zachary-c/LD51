package house.pringle.ludumdare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static house.pringle.ludumdare.AnimationNotification.*;

import java.util.HashMap;

public class Animator {

    private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    // maps names of animations ("anna_left", "anna_right") to the appropriate Animation Object (a sequence of TextureRegions, really)
    private HashMap<String,Animation<TextureRegion>> animations;
    // keeps track of the current animation that should be playing; the string is a key in the above hashmap
    private String currentAnimationName;
    private Animation<TextureRegion> currentAnimation;
    private float width, height;
    private float stateTime = 0.0f;

    private final GameObject owner;

    private ShapeRenderer shapeRenderer;
    private float frameDuration = 0.09f;

    public Animator(GameObject owner) {
        // initialize so the player's x and y match the animation's
        this.owner = owner;

        // instantiate the ArrayList and SpriteBatch
        animations = new HashMap<>();
        spriteBatch = new SpriteBatch();

        // initialize for below
        stateTime = 0.0f;

        if (Main.HITBOXES) {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setAutoShapeType(true);
        }

    }
    public String getCurrentAnimationName() { return currentAnimationName; }

    public void resetStateTime() {
        stateTime = 0;
    }

    public void addAnimationByRegion(TextureAtlas atlas, String name, Animation.PlayMode looping) {
        // This line takes the given atlas (large sprite file) and loads the animation with the give name ("anna_left_0", "anna_left_1), etc
        Animation<TextureRegion> temp = new Animation<TextureRegion>(frameDuration, atlas.findRegions(name), looping);
        // then we put it into the animation hashmap paired with its name
        animations.put(name, temp);

        // if we don't have an animation already
        if (currentAnimationName == null) {
            // get one, any of them
            currentAnimationName = animations.keySet().iterator().next();
            currentAnimation = animations.get(currentAnimationName);
            // gets an animation,
            height = currentAnimation.getKeyFrame(0).getRegionHeight() * Main.SPRITE_SCALE;
            width = currentAnimation.getKeyFrame(0).getRegionWidth() * Main.SPRITE_SCALE;
        }
    }
    public void setAnimationSpeed(String name, float frameDuration) {
        animations.get(name).setFrameDuration(frameDuration);
    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        if (currentAnimation.getPlayMode() == Animation.PlayMode.NORMAL) {
            currentFrame = currentAnimation.getKeyFrame(stateTime, false);

            // Here, if the animation is finished we let the gameObject know so it can react appropriately
            if (currentAnimation.isAnimationFinished(stateTime)) {
                owner.receiveNotification(JUMP_ANIMATION_FINISH, currentAnimationName);
            }
        } else {
            currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, owner.getX(), owner.getY(), width*Main.SPRITE_SCALE, height*Main.SPRITE_SCALE); // Draw current frame at the owner's x and y coords

        if (Main.HITBOXES) {
            // draw a rectangle around the player as a hitbox
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Rectangle me = owner.getRectangle();
            shapeRenderer.rect(me.x, me.y, me.width, me.height);
            shapeRenderer.end();
        }
    }


    public Rectangle getRectangle() {
        return new Rectangle(owner.getX()+(width*.1f), owner.getY()+(height*.1f), width*.8f, height*.8f);
    }

    public void setCurrentAnimation(String currentAnimationName) {
        this.currentAnimationName = currentAnimationName;
        this.currentAnimation = animations.get(currentAnimationName);
    }
    public int getCurrentFrameIndex() {
        return currentAnimation.getKeyFrameIndex(stateTime);
    }
}
