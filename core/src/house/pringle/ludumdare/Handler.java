package house.pringle.ludumdare;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Handler {
    public final Main game;

    private ArrayList<GameObject> gameObjects;
    private Player kaladin;
    private Player ironhorn;
    private Player currentPlayer;

    public Handler(Main game){
        this.game = game;
        gameObjects = new ArrayList<>();

        kaladin = new Player(0, 10, 4.0, true);
        ironhorn = new Player(300, 10, 4.0, false);
        currentPlayer = ironhorn;

        gameObjects.add(kaladin);
        gameObjects.add(ironhorn);
    }

    public void render(SpriteBatch batch) {
        for (GameObject o : gameObjects) {
            o.render(batch);
        }
        
    }

    public ArrayList<GameObject> getGameObject() {
        return gameObjects;
    }

    public Player getKaladin() {
        return kaladin;
    }
    public Player getIronhorn() {
        return ironhorn;
    }
    public Player getPlayer() {
        return currentPlayer;
    }

    public void dispose() {
        for (GameObject o : gameObjects) {
            o.dispose();
        }
    }

}
