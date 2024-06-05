package earlybirds.EventBus;

import earlybirds.EventBus.Events.Event;

/**
 * A simple functional interface for methods which should be used in an
 * eventBus.
 */
@FunctionalInterface
public interface EventHandler {
    /**
     * Could be any void method with an Event as parameter.
     * It should check which type of event that is being is used and act
     * accordingly.
     * 
     * @param event
     */
    public void handle(Event event);
}
