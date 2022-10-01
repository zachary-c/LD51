package house.pringle.ludumdare;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game {
	protected SpriteBatch batch;
	public static final boolean HITBOXES = false;

	public static final float SPRITE_SCALE = 2.0f;
	public static final float SPRITE_SPEED_SCALE = 1.0f;

	public Handler handler;

	public Screen gameScreen;
	public Screen menuScreen;
	public ExtendViewport viewport;
	public Texture texture;
	public TextureRegion region;

	@Override
	public void create () {

		batch = new SpriteBatch();
		viewport = new ExtendViewport(1152, 200);
		viewport.getCamera().position.set(10, 5, 0);

		texture = new Texture(Gdx.files.internal("pretty.png"));
		region = new TextureRegion(texture);
		handler = new Handler(this);
		gameScreen = new GameScreen(this);

		this.setScreen(gameScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
