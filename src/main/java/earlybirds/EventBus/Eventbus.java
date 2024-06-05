package earlybirds.EventBus;

import earlybirds.EventBus.Events.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for an Eventbus which is meant to communicate different events to
 * separate parts of the game.
 * It's usually meant for events which doesn't need to be sent with every frame
 * that is rendered.
 */
public class Eventbus {
    List<EventHandler> eventHandlers;

    /**
     * Creates an eventbus
     */
    public Eventbus() {
        this.eventHandlers = new ArrayList<>();
    }

    /**
     * Is meant to register new methods which can handle events. This way any object
     * that has access to the
     * eventbus can choose to interact with different events in different ways.
     * 
     * @param eventHandler
     */
    public void registerHandler(EventHandler eventHandler) {
        eventHandlers.add(eventHandler);

    }

    /**
     * Makes all the registered methods run with the given event.
     * 
     * @param event the event that is being run.
     */
    public void action(Event event) {
        for (EventHandler eventHandler : eventHandlers) {
            eventHandler.handle(event);
        }
    }
}
