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
import com.emexgames.game.util.Constants;

/**
 * Classe mettant en place la fin de partie selon deux conditions : soit le shuriken touche le personnage,
 * soit le drapeau touche le personnage
 */
public class FinDePartie extends GameState
{
    private Texture background;
    private BitmapFont stringFinDePartie, scoreText, strFdpAl;
    private int score;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private GlyphLayout stringFdpGlyph, scoreGlyph, strFdpAlGlyphe;

    public FinDePartie(GameStateManager gsm)
    {
        super(gsm);

        this.background = new Texture("bg.jpg");

        this.score = PlayState.getScore();

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();


        this.stringFinDePartie  = this.generator.generateFont(this.parameter);
        this.strFdpAl           = this.generator.generateFont(this.parameter);
        this.parameter.size = 60;


        this.scoreText = this.generator.generateFont(this.parameter);

        this.stringFdpGlyph = new GlyphLayout();
        this.scoreGlyph = new GlyphLayout();
        this.strFdpAlGlyphe = new GlyphLayout();

        this.stringFdpGlyph.setText(this.stringFinDePartie, Constants.FIN_DE_PARTIE);
        this.scoreGlyph.setText(this.scoreText, "Pièces : " + score);

        this.cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.isTouched())
        {
            this.gsm.set(new PlayState(this.gsm));
            PlayState.setScore(0);
        }
    }

    @Override
    public void update(float dt)
    {
        this.handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();

        sb.draw(this.background, PlayState.getBgPos1().x, 0);
        sb.draw(this.background, PlayState.getBgPos2().x,0);

        this.scoreText.draw(sb, this.scoreGlyph, Constants.VIEWPORT_WIDTH / 2 - this.scoreGlyph.width / 2, 500);
        this.stringFinDePartie.draw(sb, this.stringFdpGlyph, Constants.VIEWPORT_WIDTH / 2 - this.stringFdpGlyph.width / 2, 250);

        sb.end();
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.stringFinDePartie.dispose();
        this.scoreText.dispose();
    }
}
