package earlybirds.Menu.Buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

/**
 * Class to create textboxes
 */
public class Textbox implements ViewableTextbox {

    String text;
    Rectangle rectangle;
    BitmapFont font;
    @SuppressWarnings("unused")
    private int iterations;
    float scale;
    float lineheight;

    /**
     * @param fontSize the fontsize
     * @param text     text to display
     * @param font     font to use
     * @param x        x position of the center of the box
     * @param y        y position of the center of the box
     * @param sizeX    width
     * @param sizeY    height
     */
    public Textbox(float fontSize, String text, BitmapFont font, float x, float y, float sizeX, float sizeY) {
        this.text = text;
        this.font = font;
        this.scale = fontSize;
        this.rectangle = new Rectangle((int) x - sizeX / 2, (int) y - sizeY / 2, (int) sizeX, (int) sizeY);
        this.lineheight = font.getData().lineHeight;
        if (!validLayoutHeight()) {
            reSizeText();
        }

    }

    /**
     * This uses a binary search to rescale the text size and space between the
     * lines.
     * If after 5 iterations it has not found a value within the threshold it picks
     * the
     * lowest boundary which is guaranteed to fit the box.
     */

    public void reSizeText() {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text, Color.WHITE, getBounds().width, Align.left, true);
        float tolerance = 15;
        float left = 0;
        float right = 1f;
        float middle = 0.5f;
        float startY = font.getScaleY();
        float lineY = font.getLineHeight();
        int iterations = 0;
        while (Math.abs(getBounds().height - layout.height) > tolerance || getBounds().height < layout.height) {
            iterations++;
            middle = 1 / 2f * (left + right);
            font.getData().setScale(startY * middle);
            font.getData().lineHeight = lineY * middle;
            layout.setText(font, text, Color.WHITE, getBounds().width, Align.left, true);
            if (getBounds().height < layout.height) {
                right = middle;

            } else {
                left = middle;
            }
            if (iterations > 5) {
                font.getData().setScale(left * startY);
                font.getData().lineHeight = left * lineY;
                scale = startY * left;
                lineheight = lineY * left;
                return;

            }

        }

        scale = startY * middle;
        lineheight = lineY * middle;
        this.iterations = iterations;

    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Vector2 middlePos() {
        return new Vector2((float) (rectangle.getX() + 0.5 * rectangle.getWidth()),
                (float) (rectangle.getY() + 0.5 * rectangle.getHeight()));
    }

    @Override
    public Rectangle getBounds() {
        return rectangle;
    }

    @Override
    public BitmapFont getFont() {
        font.getData().lineHeight = lineheight;
        font.getData().setScale(scale);
        return font;
    }

    /**
     * @return boolean for whether text layout height will be smaller than
     * textbox height which makes it valid.
     */
    public boolean validLayoutHeight() {
        font.getData().lineHeight = lineheight;
        font.getData().setScale(scale);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text, Color.WHITE, getBounds().width, Align.left, true);
        return (getBounds().height > layout.height);

    }

    /**
     * Sets the text for the textbox
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
        if (!validLayoutHeight()) {
            reSizeText();
        }
    }

}
