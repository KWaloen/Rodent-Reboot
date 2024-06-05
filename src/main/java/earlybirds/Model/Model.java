package earlybirds.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import earlybirds.EventBus.EventHandler;
import earlybirds.EventBus.Eventbus;
import earlybirds.EventBus.Events.GameStateEvent;
import earlybirds.EventBus.Events.SoundEvent;
import earlybirds.Menu.FontPack;
import earlybirds.Model.Item.GameItems;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Item.Types.*;
import earlybirds.Model.Map.TileMap;
import earlybirds.Model.Map.Tiles.TileSize;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Movable.Enemy.Enemy;
import earlybirds.Model.Movable.Enemy.EnemyInterface;
import earlybirds.Model.Movable.Movable;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.UI.GameUi;
import earlybirds.Model.UI.ViewableUi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Model class which is going to have all the objects which controllers can
 * interact with and the gamescreen class can
 * display. It implements ViewableModel and ControllableModel which are
 * interfaces which restricts what controllers and
 * view objects has access to.
 */

public class Model implements ViewableModel, ControllableModel {
    private final Soundpack soundpack = new Soundpack();
    private Player player;
    private Texturepack texturePack;
    private TileMap map;
    public GameItems gameItems;
    private Array<Bullet> bullets;
    private List<Enemy> enemyList;
    private Eventbus eventbus;
    private FontPack fontPack;
    GameUi gameUi;
    private Integer currentLevel = 1;
    private ScoreKeeper scoreKeeper;
    private boolean highScoreSoundPlayed = false;

    /**
     * Constructs model object.
     * Creates player, texturePack, and new map objects.
     * Places Player at the spawn point.
     */
    public Model(Eventbus eventbus, ScoreKeeper scoreKeeper) {
        this.fontPack = new FontPack();
        this.texturePack = new Texturepack();
        this.eventbus = eventbus;
        this.scoreKeeper = scoreKeeper;
        reset();
    }

    /**
     * An alternative constructor where the TileMap is supplied externally. Mainly
     * used for testing purposes.
     */
    public Model(Eventbus eventbus, ScoreKeeper scoreKeeper, TileMap map) {
        this.fontPack = new FontPack();
        this.texturePack = new Texturepack();
        this.eventbus = eventbus;
        this.scoreKeeper = scoreKeeper;
        reset();
        this.map = map;
    }

    @Override
    public List<Sprite> getSprites() {
        List<Sprite> spriteList = new ArrayList<>();
        if (player.getShield().isImmune()) {
            spriteList.add(getShield());
        }
        spriteList.add(player.getSprite());
        for (Enemy enemy : enemyList) {
            spriteList.add(enemy.getSprite());
        }
        return spriteList;
    }

    /**
     * Get the player sprite in the game.
     */
    @Override
    public Sprite getPlayerSprite() {
        return player.getSprite();
    }

    /**
     * Get the bullets in the game.
     */
    @Override
    public Array<Bullet> getBullets() {
        return bullets;
    }


    /**
     * Get the shield sprite in the game.
     */
    @Override
    public Sprite getShield() {
        return player.getShield().getSprite();
    }


    /**
     * Get the map tile sprites in the game.
     */
    @Override
    public List<Sprite> getMapTileSprites() {
        return map.getMapTileSprites();
    }

    /**
     * Get the item sprites in the game.
     */
    @Override
    public List<Sprite> getItemSprites() {
        ArrayList<Sprite> spriteList = new ArrayList<>();
        for (Item item : gameItems.getSpawnedItemList()) {
            spriteList.add(item.getSprite());
        }
        return spriteList;
    }

    /**
     * Get the player object that can be controlled by the player.
     */
    @Override
    public Movable getControllablePlayer() {
        return player;
    }

    /**
     * Get the enemies in the game.
     */
    @Override
    public List<EnemyInterface> getEnemies() {
        return new ArrayList<>(enemyList);
    }


    /**
     * Checks if a movable object can move to a specified position.
     */
    @Override
    public boolean validMovableMove(Movable movable, float x, float y) {
        Shape2D hitbox = movable.getCollisionHitbox(x, y);
        for (Sprite wall : map.getCollisionSprites()) {
            if (checkShapeOverlap(hitbox, wall.getBoundingRectangle())) {
                return false;
            }
        }
        for (EnemyInterface enemy : enemyList) {
            if (!enemy.equals(movable)) {
                if (checkShapeOverlap(hitbox, enemy.getCollisionHitbox(0, 0))) {
                    return false;
                }
            }
        }
        if (!movable.equals(player)) {
            if (checkShapeOverlap(player.getCollisionHitbox(0, 0), hitbox)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if the player has reached the door to the next level.
     */
    @Override
    public void doorCheck() {
        if (map.getDoor().getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle())) {
            currentLevel++;
            eventbus.action(new SoundEvent(soundpack.getSound("doorOpen"), true));
            bullets.clear();
            texturePack = new Texturepack();
            map = new TileMap(10, texturePack, TileSize.LARGE_TILE);
            gameItems.initializeGameItems(map);
            player.setPos(map.getPlayerSpawn().x, map.getPlayerSpawn().y);
            initialiseEnemies();
        }
    }

    /**
     * Updates the bullets in the game.
     */
    public void updateBullets() {
        for (Bullet bullet : bullets) {
            if (checkBulletCollision(bullet)) {
                bullets.removeValue(bullet, true);
            } else {
                bullet.update();
            }
        }
    }

    /**
     * Updates the shield in the game.
     */
    public void updateShield() {
        player.getShield().update();
        gameUi.ShieldUpdate();
    }

    /**
     * Checks if a bullet has collided with any wall.
     *
     * @param bullet The bullet to check for collisions.
     * @return true if the bullet collides with a wall, false otherwise.
     */
    private boolean checkBulletCollision(Bullet bullet) {
        // Check if bullet collides with any wall
        for (Sprite wall : map.getCollisionSprites()) {
            if (wall.getBoundingRectangle().overlaps(bullet.bulletSprite.getBoundingRectangle())) {
                return true;
            }
        }
        // Check if bullet collides with any enemy
        for (Enemy enemy : enemyList) {
            if (movableOverlap(enemy, bullet) && bullet.getSymbol() == 'A') {
                eventbus.action(new SoundEvent(soundpack.getSound("enemyDeath"), true));
                enemyList.remove(enemy);
                scoreKeeper.enemyKilledIncreaseScore(this.currentLevel);
                if (scoreKeeper.isNewHighScore() && !highScoreSoundPlayed) {
                    eventbus.action(new SoundEvent(soundpack.getSound("highScore"), true));
                    this.highScoreSoundPlayed = true;
                }
                return true;
            }
            // Check if bullet collides with player
            if (player.getShield().isImmune()) {
                if (checkShapeOverlap(bullet.getCollisionHitbox(0, 0), player.getShield().getHitbox())
                        & !(bullet.getSymbol() == 'A')) {
                    return true;
                }
            } else if (movableOverlap(player, bullet) && !(bullet.getSymbol() == 'A')) {
                player.setHealth(player.getHealth() - bullet.getDamage());
                eventbus.action(new SoundEvent(soundpack.getSound("playerDamage"), true));
                playerHealthCheck();
                gameUi.healthUpdate();
                return true;

            }

        }
        return false;

    }


    /**
     * Adds a bullet to the game.
     *
     * @param bullet the bullet to be added
     */
    public void addBullet(Bullet bullet) {
        eventbus.action(new SoundEvent(soundpack.getSound("regularShot"), true));
        bullets.add(bullet);
    }

    /**
     * Gets the angle from a position to the player.
     */
    /**
     * Gets the angle from a position to the player.
     */
    @Override
    public float getAngleFromPosToPlayer(Vector2 pos) {
        Vector2 direction = new Vector2(player.getPos().x - pos.x, player.getPos().y - pos.y);
        float angle = (float) Math.atan2(direction.y, direction.x);
        return angle;
    }

    /**
     * Checks if an enemy has line of sight to the player.
     */
    /**
     * Checks if an enemy has line of sight to the player.
     */
    @Override
    public boolean lineOfSight(EnemyInterface enemy) {
        for (Sprite sprite : map.getCollisionSprites()) {
            if (Intersector.intersectSegmentRectangle(enemy.getPos(), player.getPos(), sprite.getBoundingRectangle())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param x coordinate of point
     * @param y coordinate of point
     * @return Return the distance from a specified point to the player
     */
    @Override
    public float distanceToPlayer(float x, float y) {
        return (float) Math.sqrt(Math.pow(player.getPos().x - x, 2) + Math.pow(player.getPos().y - y, 2));
    }

    /**
     * Finds enemy locations within the map and creates enemies.
     */
    public void initialiseEnemies() {
        enemyList = new ArrayList<>();
        for (Vector2 enemyPosition : map.getEnemySpawnLocations()) {
            enemyList.add(new Enemy(enemyPosition.x, enemyPosition.y, texturePack.getTexture("enemy"), currentLevel));
        }
    }

    /**
     * Checks if two Shape2D objects overlap.
     *
     * @param shape1 Shape2D object that needs to either be a Circle or Rectangle
     *               class.
     * @param shape2 Shape2D object that needs to either be a Circle or Rectangle
     *               class.
     * @return boolean, true if they overlap, false if not.
     */
    public boolean checkShapeOverlap(Shape2D shape1, Shape2D shape2) {
        if (shape1 instanceof Circle & shape2 instanceof Circle) {
            return Intersector.overlaps((Circle) shape1, (Circle) shape2);
        } else if (shape1 instanceof Circle & shape2 instanceof Rectangle) {
            return Intersector.overlaps((Circle) shape1, (Rectangle) shape2);
        } else if (shape1 instanceof Rectangle & shape2 instanceof Circle) {
            return Intersector.overlaps((Circle) shape2, (Rectangle) shape1);
        } else if (shape1 instanceof Rectangle & shape2 instanceof Rectangle) {
            return Intersector.overlaps((Rectangle) shape1, (Rectangle) shape2);
        } else
            throw new IllegalArgumentException(
                    "Both Shape2d objects needs to have classes that are Rectangle or Circle");
    }

    /**
     * Checks if the player has collided with an enemy.
     */
    /**
     * Checks if the player has collided with an enemy.
     */
    @Override
    public void checkPosMove(float xChange, float yChange, Movable movable) {
        if (validMovableMove(movable, xChange, yChange)) {
            movable.move(xChange, yChange);
        } else if (validMovableMove(movable, 0, yChange)) {
            movable.move(0, yChange);
        } else if (validMovableMove(movable, xChange, 0)) {
            movable.move(xChange, 0);
        }
    }

    /**
     * Checks if the player has collided with an item.
     */
    /**
     * Checks if the player has collided with an item.
     */
    @Override
    public void itemCollisionCheck(Player player) {
        Iterator<Item> iterator = gameItems.getSpawnedItemList().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getSprite().getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle())) {

                // ---------------Health Powerups------------------
                if (!playerHasFullHealth() && (item instanceof HealthPU || item instanceof FullHealthPU)) {
                    triggerPowerUpEffect(item);
                    iterator.remove();
                    gameUi.healthUpdate();
                }

                // ---------------Speed Powerups-------------------
                if (item instanceof Speed1PU || item instanceof Speed2PU || item instanceof Speed3PU) {
                    triggerPowerUpEffect(item);
                    iterator.remove();
                }

                // ---------------BulletSpeed Powerups-------------
                if (item instanceof BulletSpeedPU) {
                    triggerPowerUpEffect(item);
                    iterator.remove();
                }

                // ---------------Armor Powerups-------------------
                if (item instanceof ArmorPU) {
                    triggerPowerUpEffect(item);
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Triggers the powerup effect of the item.
     */
    private void triggerPowerUpEffect(Item item) {
        eventbus.action(new SoundEvent(soundpack.getSound("itemPickup"), true));
        item.doPowerUpEffect(player);
    }

    /**
     * Check if the player has health left, if not, game over.
     */
    @Override
    public void playerHealthCheck() {
        if (player.getHealth() <= 0) {
            eventbus.action(new GameStateEvent(GameStateEnum.GAMEOVER));
            eventbus.action(new SoundEvent(soundpack.getSound("playerDefeat"), true));
        }
    }

    /**
     * Check if the player has full health
     *
     * @return Boolean
     */
    public Boolean playerHasFullHealth() {
        if (player.getHealth() == 100) {
            return true;
        }
        return false;
    }

    @Override
    public Texturepack getTexturepack() {
        return texturePack;
    }

    /**
     * Resets the game to its initial state.
     */
    public void reset() {
        this.currentLevel = 1;

        this.player = new Player(texturePack);
        map = new TileMap(10, texturePack, TileSize.LARGE_TILE);
        player.setPos(map.getPlayerSpawn().x, map.getPlayerSpawn().y);
        this.gameItems = new GameItems(map);
        gameItems.initializeGameItems(map);
        this.bullets = new Array<>();
        enemyList = new ArrayList<>();
        initialiseEnemies();
        gameUi = new GameUi(this);
        scoreKeeper.resetScore();
        highScoreSoundPlayed = false;
    }

    /**
     * Get the soundpack
     *
     * @return Soundpack
     */
    public Soundpack getSoundpack() {
        return soundpack;
    }

    /**
     * Get the player
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the current level
     *
     * @return Integer
     */
    public Integer getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @return the fontpack of the model
     */
    @Override
    public FontPack getFontPack() {
        return fontPack;
    }

    /**
     * Get the gameUi
     *
     * @return GameUi
     */
    public ViewableUi getViewableUi() {
        return gameUi;
    }

    /**
     * Get the map
     *
     * @return TileMap
     */
    public TileMap getMap() {
        return map;
    }

    public void registerEventHandler(EventHandler handler) {
        eventbus.registerHandler(handler);

    }

    /**
     * Get the score, if scoreKeeper is null return 0.
     */
    public int getScore() {
        return scoreKeeper.getScore();
    }

    /**
     * @return the scoreKeeper of the model
     */
    public ScoreKeeper getScoreKeeper() {
        return scoreKeeper;
    }

    /**
     * Convienience method to simply code. Return true if two Movable objects overlap.
     *
     * @param movable1 the first movable
     * @param movable2 the second movable
     * @return
     */
    public boolean movableOverlap(Movable movable1, Movable movable2) {
        return checkShapeOverlap(movable1.getCollisionHitbox(0, 0),
                movable2.getCollisionHitbox(0, 0));
    }
}






