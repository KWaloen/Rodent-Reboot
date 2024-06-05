package earlybirds.Controller;

/**
 * Interface for controllers. Controllers can interact with the model via
 * methods defined here.
 */
public interface Controller {
    /**
     * Moves the objects controlled by controllers one step forward in time.
     */
    public void tick();
}
