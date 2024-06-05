package earlybirds.ModelTest.UiTest;

import com.badlogic.gdx.graphics.Texture;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Movable.Shield;
import earlybirds.Model.Texturepack;
import earlybirds.Model.UI.ShieldUI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class ShieldUiTest {
    ShieldUI shieldUI;
    Shield shield;
    Texture greenTexture;
    Texture yellowTexture;

    @Before
    public void setup() {
        Texturepack pack = mock(Texturepack.class);
        Shield shield = mock(Shield.class);
        this.shield = shield;
        when(shield.getRemainingImmunity()).thenReturn(5f);
        when(shield.getImmunityDuration()).thenReturn((long) 10);

        greenTexture = Mockito.mock(Texture.class);
        yellowTexture = Mockito.mock(Texture.class);

        Mockito.when(pack.getTexture("greenbar")).thenReturn(greenTexture);
        Mockito.when(pack.getTexture("yellowbar")).thenReturn(yellowTexture);

        shieldUI = new ShieldUI(shield, pack, 50, 50, 50, 50);

    }

    @Test
    public void shieldRemainingTest() {
        float originalWidth = shieldUI.getSprite().getWidth();
        shieldUI.shieldUpdate();
        float afterWidth = shieldUI.getSprite().getWidth();

        assertTrue(almostEqual(originalWidth, 2 * afterWidth));
    }

    @Test
    public void shieldRemainingTest2() {
        when(shield.getRemainingImmunity()).thenReturn(2.5f);
        float originalWidth = shieldUI.getSprite().getWidth();
        shieldUI.shieldUpdate();
        float afterWidth = shieldUI.getSprite().getWidth();

        assertTrue(almostEqual(originalWidth, 4 * afterWidth));
    }

    // The shield UI sprite should have greenTexture when the remaining shield time
    // is greater than zero, otherwise it should
    // return yellowSprite
    @Test
    public void correctSpriteTest() {
        assertTrue(shieldUI.getSprite().getTexture().equals(greenTexture));
        when(shield.getRemainingImmunity()).thenReturn(0f);
        shieldUI.shieldUpdate();
        assertTrue(shieldUI.getSprite().getTexture().equals(yellowTexture));
    }

    @Test
    public void cooldownSpriteTest() {
        float startWidth = shieldUI.getSprite().getWidth();
        when(shield.getRemainingImmunity()).thenReturn(0f);
        when(shield.getRemainingCooldown()).thenReturn((long) 7500);

        shieldUI.shieldUpdate();
        assertTrue(almostEqual(2 * shieldUI.getSprite().getWidth(), startWidth));

    }
    /**
     * Helper method to check if two floats are almost equal
     * @param val1 float 1
     * @param val2 float 2
     * @return true if the floats are almost equal
     */
    private boolean almostEqual(float val1, float val2) {
        return Math.abs(val1 - val2) < 0.001;
    }
}