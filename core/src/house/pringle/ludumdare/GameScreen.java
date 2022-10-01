package house.pringle.ludumdare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private final Main main;

    public int WORLD_WIDTH = 2048; //  default 16:9 * 128 pixels per tile
    public int WORLD_HEIGHT = 1152;

    private Texture background;

    private Sprite mapSprite;
    public GameScreen(Main main) {
        this.main = main;

        mapSprite = new Sprite(new Texture("pretty.png"));
        mapSprite.setSize(4, 20);
    }

    @Override
    public void render(float delta) {
        main.viewport.apply();

        main.batch.setProjectionMatrix(main.viewport.getCamera().combined);
        handleMovement();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            main.setScreen(main.menuScreen);
        }

        main.batch.begin();
        main.batch.draw(mapSprite, 0,0);
        main.handler.render(main.batch);
        main.batch.end();
    }

    private void handleMovement() {
        OrthographicCamera camera = (OrthographicCamera) main.viewport.getCamera();

        float playerXCenter = main.handler.getPlayer().getX() + main.handler.getPlayer().getRectangle().width/2f;
        float playerYCenter = main.handler.getPlayer().getY() + main.handler.getPlayer().getRectangle().height/2f;

        if (playerXCenter > camera.viewportWidth / 2f) {
            camera.position.x = playerXCenter;
        }
        if (playerYCenter > camera.viewportHeight / 2f) {
            camera.position.y = playerYCenter;
        }

        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2f, WORLD_WIDTH - camera.viewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2f, WORLD_HEIGHT - camera.viewportHeight / 2f);
    }

    @Override
    public void resize(int width, int height) {
        main.viewport.update(width, height, true);
        // Called when the Application is resized. This can happen at any point
        // during a non-paused state but will never happen before a call to create().
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.
        // game.startMusic();
    }

    @Override
    public void pause() {
        // Called when the Application is paused, usually when it's not active
        // or visible on-screen. An Application is also paused before it is destroyed.
    }

    @Override
    public void resume() {
        // Called when the Application is resumed from a paused state, usually when it regains focus.
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen for a Game.
    }

    @Override
    public void dispose() {
        // Called when this screen should release all resources.
        main.handler.dispose();
    }
}
