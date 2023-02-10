/**
 * @author Adrien Decharrois
 * @version 1.0
 */

package com.emexgames.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.emexgames.game.manager.GameStateManager;
import com.emexgames.game.sprites.Character;
import com.emexgames.game.util.Constants;

/**
 * Classe permettant de mettre en place le menu principale du jeu
 */
public class MainMenu extends GameState {
    private Texture background;
    private Character character;
    private BitmapFont gameTitleText, touchText, toText, startText;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private GlyphLayout gameTitleGlyph, touchGlyph, toGlyph, startGlyph;

    public MainMenu(GameStateManager gsm) {
        super(gsm);

        this.background = new Texture("bg.jpg");
        this.character  = new Character(0,210);

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        this.parameter.size = 60;
        this.gameTitleText = this.generator.generateFont(this.parameter);

        this.touchText  = this.generator.generateFont(this.parameter);
        this.toText     = this.generator.generateFont(this.parameter);
        this.startText  = this.generator.generateFont(this.parameter);

        this.gameTitleGlyph = new GlyphLayout();
        this.touchGlyph     = new GlyphLayout();
        this.toGlyph        = new GlyphLayout();
        this.startGlyph     = new GlyphLayout();

        this.gameTitleGlyph.setText(this.gameTitleText, Constants.GAME_TITLE);
        this.touchGlyph.setText(this.touchText, "Touchez");
        this.toGlyph.setText(this.toText, "pour");
        this.startGlyph.setText(this.startText, "jouer");

        this.cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()) {
            this.gsm.set(new PlayState(this.gsm));
        }
    }

    @Override
    public void update(float dt)
    {
        this.handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        /*  Matrice de projection */
        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();
        sb.draw(this.background, -625, 0);
        sb.draw(this.background, 642,0);
        sb.draw(this.character.getTexture(), this.character.getPosition().x, this.character.getPosition().y);

        this.gameTitleText.draw(sb, this.gameTitleGlyph, this.cam.viewportWidth/2 - this.gameTitleGlyph.width/2,this.cam.viewportHeight - this.gameTitleGlyph.height);
        this.touchText.draw(sb, this.touchGlyph, 280, 450);
        this.toText.draw(sb, this.toGlyph, 580, 450);
        this.startText.draw(sb, this.startGlyph, 780, 450);

        sb.end();
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.character.dispose();
        this.gameTitleText.dispose();
        this.touchText.dispose();
        this.toText.dispose();
        this.startText.dispose();
    }
}
