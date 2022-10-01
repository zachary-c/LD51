package house.pringle.ludumdare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;

import static house.pringle.ludumdare.PlayerAnimations.*;

public class Player extends Moving {

    private Animator animator;
    /**
     * Used to determine if we can jump, and when gravity should be applied
     */
    private boolean onGround = true;
    /**
     * Tells us which way to attack, and similar
     */
    private boolean facingRight = true;
    private boolean busy = false;
    private boolean jumping = false;
    private boolean player1;

    // format is "folder/name" where name does not include the extension, number OR '_'
    private HashMap<PlayerAnimations, String> animations = new HashMap<>();

    private float kASpeed, jumpSpeed, walkSpeed;
    private float jumpVelocity = 40;

    public Player(int x, int y, double speed, boolean player1) {
        super(x, y, 0, 0, speed);
        animator = new Animator(this);
        this.player1 = player1;
        animations.put(LEFTWALK, player1 ? "kaladin_walk_l" : "ironhorn_walk_l");
        animations.put(RIGHTWALK, player1 ? "kaladin_walk_r" : "ironhorn_walk_r");
        animations.put(LEFTJUMP, player1 ? "kaladin_jump_l" : "ironhorn_jump_l");
        animations.put(RIGHTJUMP, player1 ? "kaladin_jump_r" : "ironhorn_jump_r");
        animations.put(LEFTKA, player1 ? "kaladin_ka_l" : "ironhorn_ka_l");
        animations.put(RIGHTKA, player1 ? "kaladin_ka_r" : "ironhorn_ka_r");
        animations.put(LEFTFALL, player1 ? "kaladin_fall_l" : "ironhorn_fall_l");
        animations.put(RIGHTFALL, player1 ? "kaladin_fall_r" : "ironhorn_fall_r");
        kASpeed = player1 ? 0.5f : 0.5f;
        jumpSpeed = player1 ? 0.2f : 0.2f;
        walkSpeed = player1 ? 0.1f : 0.2f;

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(player1 ? "./kaladin/atlas/kaladin.atlas" : "./ironhorn/atlas/ironhorn.atlas"));

        for (String s : animations.values()) {
            animator.addAnimationByRegion(atlas, s, s.contains("jump") ? Animation.PlayMode.NORMAL : Animation.PlayMode.LOOP);
        }

        animator.setAnimationSpeed(animations.get(LEFTKA), kASpeed);
        animator.setAnimationSpeed(animations.get(RIGHTKA), kASpeed);
        animator.setAnimationSpeed(animations.get(LEFTJUMP), jumpSpeed);
        animator.setAnimationSpeed(animations.get(RIGHTJUMP), jumpSpeed);
    }

    public void render(SpriteBatch b) {
        super.render(b);
        gravity();
        animator.render(b);
        keyHandler();

        if (player1) {
            player1RenderLogic();
        } else {
            player2RenderLogic();
        }

        BitmapFont font = new BitmapFont();
        font.setColor(Color.BLACK);

        font.draw(b, "Frame: " + animator.getCurrentFrameIndex(), 10, 500);
        font.draw(b, "busy: " + busy, 10, 475);
        font.draw(b, "jumping: " + busy, 10, 450);
        font.draw(b, "on ground: " + onGround, 10, 425);
        font.draw(b, "(" + x + ", " + y + ")", 10, 400);

    }

    public void receiveNotification(AnimationNotification notification, String animation) {
        switch (notification) {
            case JUMP_ANIMATION_FINISH:
                animator.setCurrentAnimation(animations.get(facingRight ? RIGHTFALL : LEFTFALL));
                busy = false;
                break;
        }
    }

    private void keyHandler() {
        // keep left right and up down separate
        if (!(Gdx.input.isKeyPressed(Input.Keys.W)
                && Gdx.input.isKeyPressed(Input.Keys.A)
                && Gdx.input.isKeyPressed(Input.Keys.S)
                && Gdx.input.isKeyPressed(Input.Keys.D))) {
            if (!busy && !jumping) {
                animator.setCurrentAnimation(animations.get(facingRight ? RIGHTKA : LEFTKA));
            }
        }
        if (!busy) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                setVelX(-speed);
                if (!jumping) {
                    animator.setCurrentAnimation(animations.get(LEFTWALK));
                }
                facingRight = false;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                setVelX(speed);
                if (!jumping) {
                    animator.setCurrentAnimation(animations.get(RIGHTWALK));
                }
                facingRight = true;
            } else {
                setVelX(0);
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGround && !jumping) {
            if (player1) {
                setVelY(jumpVelocity);
                jumping = true;
            } else { // player 2, ironhorn case
                setVelX(0);
                busy = true;
            }
            animator.resetStateTime();
            animator.setCurrentAnimation(animations.get(facingRight ? RIGHTJUMP : LEFTJUMP));
            onGround = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(0);
        }
    }

    private void player2RenderLogic() {
        if (onGround && (animator.getCurrentAnimationName().equals(animations.get(LEFTJUMP)) ||
            animator.getCurrentAnimationName().equals(animations.get(RIGHTJUMP))) && animator.getCurrentFrameIndex() == 2) {
            setVelY(jumpVelocity);
            jumping = true;
            busy = false;
        }
    }
    private void player1RenderLogic() {
        // some special logic, probably
    }
    private void gravity() {
        if (y > 10) {
            setVelY(getVelY() - 1.0);
            onGround = false;
        } else {
            setVelY(0);
            y = 10;
            onGround = true;
            jumping = false;
        }
    }

    public void dispose() {}
    public Rectangle getRectangle() { return animator.getRectangle(); }
}
