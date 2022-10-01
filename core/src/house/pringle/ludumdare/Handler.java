package ludumdare.friday.project2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.util.ArrayList;
import java.util.Iterator;

public class Handler {
    final Project2 game;

    private ArrayList<GameObject> objectList;
    private int pleaseTakeANumber = -1;
    private World world;

    // nextPlayerSpawnLocation
    private int nextPSLX = 0;
    private int nextPSLY = 0;

    // enemy stats
    private int enemySpeed;
    private int enemyDamage;
    private int enemyFireRate;

    private boolean newLevel = false;

    // this is so things like benemy can add projectiles to the arraylist while they're being rendered without concurrentModificationExceptions
    private ArrayList<BaseProjectile> projectiles = new ArrayList<>();

    public Handler(final Project2 game){
        this.game = game;
        objectList = new ArrayList<>();
        // we need to add the player first off so they will always be at objectList.get(0) for getPlayer()
        objectList.add(new Player(0, 0, this, 100, 3));

        // initialize enemy stats
        enemySpeed = 2;
        enemyDamage = 10;
        enemyFireRate = 2;

        world = new World(this);
        // put the player at their spawn location
    //    getPlayer().setPosX(world.getCurrentLevel().getPlayerSpawnX());
    //    getPlayer().setPosY(world.getCurrentLevel().getPlayerSpawnY());
       // objectList.addAll(world.getCurrentLevel().getLevelGameObjects());
    }

    public void render(SpriteBatch batch){
        for (GameObject o : objectList) {
            o.render(batch);
        }

        for (BaseProjectile bp : projectiles) {
            bp.render(batch);
        }

        for (Wall w : world.getCurrentLevel().getAutoWalls()) {
            w.render(batch);
        }
     //   addBufferToList();
        checkForCollisions();
        if (newLevel) {
            if (world.getCLNum() != 0) {
                for (Iterator<GameObject> iterator = objectList.iterator(); iterator.hasNext();) {
                    GameObject o = iterator.next();
              //      System.out.println(o.getLevelID() + " vs " + world.getCurrentLevel().getLevelID());
                    if (o.getLevelID() == world.getCurrentLevel().getLevelID()-1 && o.getID() != 0) {
                        iterator.remove();
               //         System.out.println("removed object!");
                    }
                }
            }
            System.out.println(world.getCurrentLevel().getLevelGameObjects());
            objectList.addAll(world.getCurrentLevel().getLevelGameObjects());
            newLevel = false;
        }
    }

    public void checkForCollisions() {
        boolean removed = false;
        // iterate through each of the projectiles
        for (Iterator<BaseProjectile> iterator = projectiles.iterator(); iterator.hasNext();) {
            BaseProjectile b = iterator.next();
            // iterate through the list of objects
            for (GameObject o : objectList) {
                // confirm that we're not colliding with ourselves
                if (o.getID() == b.getCreatorID()) {
                    continue;
                }

                // if the rectangles overlap
                if (b.getRectangle().overlaps(o.getRectangle())){

                    // if it's a player, damage them
                    if (getPlayer().getID() == o.getID()) {
                        getPlayer().takeDamage(getEnemyDamage());
                    }
                    b.explode();
                    iterator.remove();
                    removed = true;
                    break;
           //         System.out.println("projectile overlaps object");
                }
            }

            // if we already deleted the projectile, don't worry about comparing walls
            if (removed) { continue; }
            for (Wall w : world.getCurrentLevel().getAutoWalls()) {
                // if they overlap
                if (b.getRectangle().overlaps(w.getRectangle())){
                    b.explode();
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public void loadNextLevel() {

        game.gameScreen.initializeLevel(world.nextLevel().getTiledMap());
        newLevel = true;
    }

    public void spawnPlayer() {
        getPlayer().setPosX(world.getCurrentLevel().getPlayerSpawnX());
        getPlayer().setPosY(world.getCurrentLevel().getPlayerSpawnY());
    }

    public ArrayList<GameObject> getObjectList() {
        return objectList;
    }

    public Player getPlayer() {
        return (Player) objectList.get(0) ;
    }

    public int getNextID() {
        pleaseTakeANumber++;
        return pleaseTakeANumber;
    }

    public World getWorld() {
        return world;
    }

    public void dispose() {
        for (GameObject o : objectList) {
            o.dispose();
        }
    }

   // public int getPlayerSpawnX() { return world.getCurrentLevel().getPlayerSpawnX(); }
   // public int getPlayerSpawnY() { return retu; }

    public int getEnemySpeed() {
        return enemySpeed;
    }
    public int getEnemyDamage() {
        return enemyDamage;
    }
    public int getEnemyFireRate() { return enemyFireRate; }

    public void addProjectile(BaseProjectile bp) {
        projectiles.add(bp);
    }
}
