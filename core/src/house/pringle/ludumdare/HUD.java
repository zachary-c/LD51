package ludumdare.friday.project2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {

    private Handler handler;
    private int edgeSpacing = 20;
    private TextureAtlas atlas;
    private float hudScalar = 0.5f;
    private Viewport viewport;

    private String[] healthFrames = {"hp0.png", "hp10.png", "hp20.png",
                                     "hp30.png", "hp40.png", "hp50.png",
                                     "hp60.png", "hp70.png", "hp80.png",
                                     "hp90.png", "hp100.png",
                                     "purple1.png", "purple2.png", "purple3.png",
                                     "purple4.png", "purple5.png", "red1.png",
                                     "red2.png", "red3.png", "red4.png",
                                     "red1.png", "heartempty.png"};

    public HUD(Handler handler) {
        this.handler = handler;

        atlas = new TextureAtlas(Gdx.files.internal("./hud/atlas/hitpoints.atlas"));
        viewport = handler.game.gameScreen.getViewport();
    }

    public void render(SpriteBatch batch) {
        TextureRegion hpBar = atlas.findRegion(getHPBarFrame());
        float hpBarY = (viewport.getCamera().position.y + viewport.getCamera().viewportHeight / 2f) - (hpBar.getRegionHeight()/2f +edgeSpacing);
        float hpBarX = (viewport.getCamera().position.x - viewport.getCamera().viewportWidth / 2f) + (edgeSpacing);
        batch.draw(hpBar, hpBarX, hpBarY, hpBar.getRegionWidth()*hudScalar, hpBar.getRegionHeight()*hudScalar);
    }

    private String getHPBarFrame() {
        int hpNum = handler.getPlayer().getHealth();
      //  System.out.println("hp" + Math.round(hpNum / 10f) * 10);
        return "hp" + Math.round(hpNum / 10f) * 10;
    }
}
