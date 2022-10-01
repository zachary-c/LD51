package house.pringle.ludumdare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {

    private Handler handler;
    private TextureAtlas atlas;
    private Viewport viewport;

    public HUD(Handler handler) {
        this.handler = handler;

        //atlas = new TextureAtlas(Gdx.files.internal(""));
        //viewport = handler.game.gameScreen.getViewport();
    }

    public void render(SpriteBatch batch) {
        // do hud rendering later
    }

}
