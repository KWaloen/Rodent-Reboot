package earlybirds;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import earlybirds.Controller.EnemyController;
import earlybirds.Controller.PlayerController;
import earlybirds.Controller.SoundController;
import earlybirds.EventBus.Eventbus;
import earlybirds.EventBus.Events.Event;
import earlybirds.EventBus.Events.GameStateEvent;
import earlybirds.EventBus.Events.SoundEvent;
import earlybirds.Menu.FontPack;
import earlybirds.Menu.MenuHandler;
import earlybirds.Model.GameState;
import earlybirds.Model.GameStateEnum;
import earlybirds.Model.Model;
import earlybirds.Model.ScoreKeeper;
import earlybirds.View.GameScreen;

import static com.badlogic.gdx.Gdx.input;

/**
 * Gamehandler klassen er hovedklassen som lager delene av model-view-kontroller
 * strukturen og implementerer
 * ApplicationListener.
 */
public class GameHandler implements ApplicationListener {
    private Model model;
    private GameScreen screen;
    private PlayerController playerController;
    private EnemyController enemyController;
    private GameState gameState;
    private SpriteBatch batch;
    private MenuHandler menuHandler;
    @SuppressWarnings("unused")
    private SoundController soundController;
    private ScoreKeeper scoreKeeper;
    private Eventbus eventbus;
    private FontPack fontPack;

    /**
     * Denne metoden kjøres når programmet starter. Blir en form for konstruktør for
     * libgdx.
     * Objekter som lages her kan samarbeide med libgdx sine funksjoner bedre.
     */
    @Override
    public void create() {
        this.fontPack = new FontPack();
        this.eventbus = new Eventbus();
        this.batch = new SpriteBatch();
        gameState = new GameState();
        scoreKeeper = new ScoreKeeper();
        model = new Model(eventbus, scoreKeeper);
        screen = new GameScreen(model, batch);
        enemyController = new EnemyController(model);
        menuHandler = new MenuHandler(eventbus, batch, fontPack, scoreKeeper);
        soundController = new SoundController(eventbus, model.getSoundpack());
        eventbus.registerHandler(this::GameStateHandle);
        eventbus.action((new SoundEvent(model.getSoundpack().getSound("musicBackground"), false)));
        OrthographicCamera camera = screen.getGameCam();
        this.playerController = new PlayerController(model, input, camera);
        menuHandler.activate();

    }

    @Override
    public void resize(int i, int i1) {
    }

    /**
     * Metoden heter "render" men vi tenker på den mer som clock-tick. Her går tiden
     * et steg fremover og view-objektet og kontrollene
     * oppdateres.
     * Kontrollerene endrer på modellen, mens GameScreen klassen viser det på
     * skjermen.
     */
    @Override
    public void render() {
        if (gameState.getGameStateEnum().equals(GameStateEnum.PLAYING)) {

            enemyController.tick();
            playerController.tick();
            model.updateBullets();
            model.updateShield();
            screen.renderModel();

        } else if (gameState.getGameStateEnum().equals(GameStateEnum.SETUP)) {

            menuHandler.setToStartMenu();
            menuHandler.render();

        } else if (gameState.getGameStateEnum().equals(GameStateEnum.GAMEOVER)) {

            menuHandler.setToGameOverMenu();
            menuHandler.render();

        } else if (gameState.getGameStateEnum().equals(GameStateEnum.EXIT)) {
            Gdx.app.exit();
        } else if (gameState.getGameStateEnum().equals(GameStateEnum.RESTART)) {
            gameState.setGameState(GameStateEnum.SETUP);
            menuHandler.setToStartMenu();
            menuHandler.render();
            model.reset();

        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        screen.dispose();

    }

    /**
     * This method is registered with the eventbus and checks for gamestate changes.
     *
     * @param event
     */
    public void GameStateHandle(Event event) {
        if (event instanceof GameStateEvent) {
            this.gameState.setGameState(((GameStateEvent) event).getNewState());
            if (((GameStateEvent) event).getNewState().equals(GameStateEnum.PLAYING)) {
                playerController.activate();
            } else if (((GameStateEvent) event).getNewState().equals(GameStateEnum.GAMEOVER) ||
                    ((GameStateEvent) event).getNewState().equals(GameStateEnum.RESTART)) {
                menuHandler.activate();
            }

        }
    }
}
