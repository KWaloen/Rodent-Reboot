package earlybirds.EventBus.Events;

import earlybirds.Model.GameStateEnum;

/**
 * Event type meant to communicate gamestate changes
 */
public class GameStateEvent implements Event {
    GameStateEnum gameStateEnum;

    /**
     * Constructor for the GameStateEvent
     * @param newState the new state of the game
     */
    public GameStateEvent(GameStateEnum newState) {
        this.gameStateEnum = newState;
    }

    /**
     * Getter for the new state
     * @return the new state
     */
    public GameStateEnum getNewState() {
        return gameStateEnum;
    }

}
