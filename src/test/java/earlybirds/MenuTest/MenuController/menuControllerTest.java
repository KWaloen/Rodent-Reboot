package earlybirds.MenuTest.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import earlybirds.EventBus.EventHandler;
import earlybirds.EventBus.Eventbus;
import earlybirds.EventBus.Events.Event;
import earlybirds.EventBus.Events.GameStateEvent;
import earlybirds.GdxTestRunner;
import earlybirds.Menu.Buttons.Button;
import earlybirds.Menu.Buttons.ButtonEnum;
import earlybirds.Menu.MenuController.MenuController;
import earlybirds.Menu.MenuModels.GameOverMenuModel;
import earlybirds.Menu.MenuModels.StartMenuModel;
import earlybirds.Model.GameStateEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class menuControllerTest {
    MenuController controller;
    Eventbus bus;
    Button button;

    boolean check = false;

    @Before
    public void setup() {
        check = false;
        Input input = mock(Input.class);
        Gdx.input = input;

        StartMenuModel startMenuModel = mock(StartMenuModel.class);
        button = mock(Button.class);
        when(button.getButtonType()).thenReturn(ButtonEnum.STARTBUTTON);
        when(button.overlap(any(float.class), any(float.class))).thenReturn(true);

        List<Button> list = new ArrayList<>();
        list.add(button);

        when(startMenuModel.getButtons()).thenReturn(list);
        bus = new Eventbus();
        controller = new MenuController(bus, startMenuModel);

    }

    // Checks if the bus receives an event to push
    @Test
    public void startTest() {

        bus.registerHandler(new EventHandler() {
            @Override
            public void handle(Event event) {
                check = true;
            }
        });
        controller.touchDown(1, 1, 0, Input.Buttons.LEFT);
        assertTrue(check);
    }

    // Checks if the received event has the right gamestate
    @Test
    public void startTest2() {

        bus.registerHandler(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (event instanceof GameStateEvent) {
                    if (((GameStateEvent) event).getNewState().equals(GameStateEnum.PLAYING)) {
                        check = true;
                    }
                }
            }
        });
        controller.touchDown(1, 1, 0, Input.Buttons.LEFT);
        assertTrue(check);

    }

    // checks if receiving the wrong gamestate does not change the state
    @Test
    public void startTest3() {

        bus.registerHandler(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (event instanceof GameStateEvent) {
                    if (((GameStateEvent) event).getNewState().equals(GameStateEnum.GAMEOVER)) {
                        check = true;
                    }
                }
            }
        });
        controller.touchDown(1, 1, 0, Input.Buttons.LEFT);
        assertFalse(check);

    }

    @Test
    public void changeMenu() {
        GameOverMenuModel gameoverMenu = mock(GameOverMenuModel.class);
        controller.setMenu(gameoverMenu);
        controller.touchDown(1, 1, 0, Input.Buttons.LEFT);
        assertTrue(controller.getMenu().equals(gameoverMenu));

    }

    //The mouse moved method should not send out events at all:
    @Test
    public void startTest4() {
        bus.registerHandler(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (event instanceof GameStateEvent) {
                    check = true;
                }
            }

        });
        controller.mouseMoved(1, 3);
        assertFalse(check);
    }
}
