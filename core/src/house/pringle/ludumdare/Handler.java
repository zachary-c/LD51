package house.pringle.ludumdare;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Handler {
    public final Main game;

    private ArrayList<GameObject> gameObjects;
    private Player player;

    public Handler(Main game){
        this.game = game;
        gameObjects = new ArrayList<>();

        player = new Player(0, 0);

        gameObjects.add(player);
    }

    public void render(SpriteBatch batch) {
        for (GameObject o : gameObjects) {
            o.render(batch);
        }
    }

    public ArrayList<GameObject> getGameObject() {
        return gameObjects;
    }

    public Player getPlayer() {
        return player;
    }

    public void dispose() {
        for (GameObject o : gameObjects) {
            o.dispose();
        }
    }

}
