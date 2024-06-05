package earlybirds.MenuTest.ButtonsTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import earlybirds.GdxTestRunner;
import earlybirds.Menu.Buttons.Button;
import earlybirds.Menu.Buttons.ButtonEnum;
import earlybirds.Menu.Buttons.Textbox;
import earlybirds.Menu.Buttons.TextboxWithSprite;
import earlybirds.Menu.FontPack;
import earlybirds.Model.Texturepack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class ButtonTest {
    BitmapFont font;
    FontPack fontPack;

    Sprite sprite;
    private Texturepack texturepack;

    public ButtonTest() {
        texturepack = mock(Texturepack.class);
        Texture texture = mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);
        fontPack = new FontPack();
        font = new BitmapFont(Gdx.files.internal("menuSprites/font.fnt"));
    }

    @Before
    public void setup() {
        font = new BitmapFont(Gdx.files.internal("menuSprites/font.fnt"));
        sprite = new Sprite(texturepack.getTexture("button"));
    }

    @Test
    public void TextTest() {
        Textbox textbox = new Textbox(1, "test", font, 0, 0, 100, 100);
        assertEquals("test", textbox.getText());
    }

    @Test
    public void TextSizeTest1() {
        Textbox textbox = new Textbox(1, "test", font, 0, 0, 100, 100);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(textbox.getFont(), textbox.getText(), Color.WHITE, 100, Align.left, true);
        assertTrue(layout.height < textbox.getBounds().height);
    }

    @Test
    public void TextSizeTest2() {

        String text = "test\nl\ndasdj\nasjdlk\n";
        Integer height = 40;

        Textbox textbox = new Textbox(1, "test", font, 0, 0, 100, height);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(textbox.getFont(), textbox.getText(), Color.WHITE, 100, Align.left, true);

        GlyphLayout withoutResize = new GlyphLayout();
        withoutResize.setText(fontPack.getStandard(), text, Color.WHITE, 100, Align.left, true);
        assertFalse(withoutResize.height < height);
        assertTrue(layout.height < textbox.getBounds().height);
    }

    @Test
    public void TextSizeTest3() {

        String text = "test\nl\ndasdj\nasjdlk\n";
        Integer height = 20;

        Textbox textbox = new Textbox(1, text, font, 0, 0, 100, height);
        float layoutHeight = getLayoutHeight(textbox.getFont(), text);

        float layoutHeightWihoutReize = getLayoutHeight(fontPack.getStandard(), text);
        assertFalse(layoutHeightWihoutReize < height);
        assertTrue(layoutHeight < textbox.getBounds().height);
    }

    // Making a textbos with sprite versus one without should not change the size of
    // the text:
    @Test
    public void TextboxWithSpriteTest() {
        String text = "test\nl\ndasdj\nasjdlk\n";
        TextboxWithSprite textboxWithSprite = new TextboxWithSprite(1, text, font, sprite, 0, 0, 100, 100);
        Textbox textbox = new Textbox(1, text, font, 0, 0, 100, 100);
        assertTrue(textboxWithSprite.getSprite().getHeight() == 100);
        assertTrue(textboxWithSprite.getSprite().getWidth() == 100);

        assertEquals(getLayoutHeight(textboxWithSprite.getFont(), text), getLayoutHeight(textbox.getFont(), text));

    }

    @Test
    public void ButtonTest1() {
        String text = "test\nl\ndasdj\nasjdlk\n";
        Button button = new Button(1, ButtonEnum.STARTBUTTON, text, font, sprite, 0, 0, 100, 100);
        Textbox textbox = new Textbox(1, text, font, 0, 0, 100, 100);
        assertEquals(getLayoutHeight(button.getFont(), text), getLayoutHeight(textbox.getFont(), text));
    }

    @Test
    public void ButtonTest2() {
        Color originalColor = new Color(sprite.getColor());
        String text = "test\nl\ndasdj\nasjdlk\n";
        Button button = new Button(1, ButtonEnum.STARTBUTTON, text, font, sprite, 0, 0, 100, 100);
        button.unHover();
        assertFalse(button.getSprite().getColor().equals(originalColor));
        button.Hover();
        assertTrue(button.getSprite().getColor().equals(originalColor));
    }

    public float getLayoutHeight(BitmapFont font, String text) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text, Color.WHITE, 100, Align.left, true);
        return layout.height;

    }

    @Test
    public void ButtonTest3() {
        String text = "test\nl\ndasdj\nasjdlk\n";
        Button button = new Button(1, ButtonEnum.STARTBUTTON, text, font, sprite, 0, 0, 100, 100);
        assertTrue(button.getButtonType().equals(ButtonEnum.STARTBUTTON));
    }

    @Test
    public void ButtonTest4() {
        String text = "test\nl\ndasdj\nasjdlk\n";
        Button button = new Button(1, ButtonEnum.STARTBUTTON, text, font, sprite, 0, 0, 100, 100);
        assertFalse(button.overlap(0, 60));
        assertTrue(button.overlap(0, 0));
    }


}
