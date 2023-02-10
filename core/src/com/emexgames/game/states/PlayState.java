/**
 * @author Adrien Decharrois
 * @version 1.0
 */

package com.emexgames.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.emexgames.game.manager.GameStateManager;
import com.emexgames.game.sprites.Drapeau;
import com.emexgames.game.sprites.Obstacle;

import com.emexgames.game.sprites.Character;
import com.emexgames.game.sprites.Piece;
import com.emexgames.game.util.Constants;

import java.sql.Time;

/**
 * Classe répertoriant toutes les fonctionnalités du jeu
 */
public class PlayState extends GameState
{
    private static Texture background;
    private static int score = 0;
    private static Character character;
    private static Array<Obstacle> obstaclesShuriken;
    private static Array<Piece> allPieces;
    private static Vector2 bgPos1, bgPos2;
    private static GameStateManager gsm;
    private static Drapeau drapeau;
    private final Time time;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont scoreText, timeText;
    private GlyphLayout scoreGlyph, timeGlyph;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        this.gsm = gsm;

        // Initialiser le temps de jeu
        this.time               = new Time(0,0, 0);
        this.background         = new Texture("bg.jpg");
        this.drapeau            = new Drapeau(5500, 210);
        this.character          = new Character(0, 210);
        this.obstaclesShuriken  = new Array<Obstacle>();
        this.allPieces          = new Array<Piece>();

        for(int i = 0; i < Constants.NB_OBSTACLES; i++)
            this.obstaclesShuriken.add(new Obstacle(i));

        for(int i = 0; i < Constants.NB_PIECES; i++)
            this.allPieces.add(new Piece(i));

        this.bgPos1 = new Vector2(this.cam.position.x - Constants.VIEWPORT_WIDTH/2,0);
        this.bgPos2 = new Vector2(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 + this.background.getWidth(), 0  );

        this.cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 60;

        this.scoreText = this.generator.generateFont(this.parameter);
        this.timeText = this.generator.generateFont(this.parameter);

        this.scoreGlyph = new GlyphLayout();
        this.timeGlyph = new GlyphLayout();

        this.scoreGlyph.setText(this.scoreText, "Score : " + this.score);
        this.timeGlyph.setText(this.timeText, "Time : " + this.time);
    }


    @Override
    protected void handleInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            this.character.moveRight();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            this.character.moveLeft();

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            this.character.jump();
    }

    @Override
    public void update(float dt)
    {
        this.handleInput();
        this.updateBackground();
        this.character.update(dt);
        this.time.setSeconds(this.time.getSeconds() + 1);

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            this.cam.position.x += 3.9;
            this.cam.update();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.cam.position.x -= 3.9;
            this.cam.update();
        }

        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 < 0)
        {
            this.cam.position.x = Constants.VIEWPORT_WIDTH/2;
            this.cam.update();
        }

        if(this.time.getSeconds() == 60)
        {
            this.time.setMinutes(this.time.getMinutes() + 1);
            this.time.setSeconds(0);
        }

        //Quand je touche une pièce, elle disparait et je gagne 1 point
        for(int i = 0; i < this.allPieces.size; i++)
        {
            Piece piece = this.allPieces.get(i);

            if(this.character.getPosition().x < piece.getPosition().x + piece.getTexture().getWidth()                   &&
                    this.character.getPosition().x + (this.character.getTexture().getWidth()) > piece.getPosition().x   &&
                    this.character.getPosition().y < piece.getPosition().y + piece.getTexture().getHeight()             &&
                    this.character.getPosition().y + (this.character.getTexture().getHeight()) > piece.getPosition().y)
            {
                this.allPieces.removeIndex(i);
                this.score++;
            }
        }

        // Vérification si les pièces se superposent avec les shurikens et les replacer avec un random
        for(int i = 0; i < this.allPieces.size; i++)
        {
            Piece piece = this.allPieces.get(i);

            for(int j = 0; j < this.obstaclesShuriken.size; j++)
            {
                Obstacle obs = this.obstaclesShuriken.get(j);

                if(piece.getPosition().x < obs.getPosition().x + obs.getTexture().getWidth() &&
                        piece.getPosition().x + piece.getTexture().getWidth() > obs.getPosition().x &&
                        piece.getPosition().y < obs.getPosition().y + obs.getTexture().getHeight() &&
                        piece.getPosition().y + piece.getTexture().getHeight() > obs.getPosition().y)
                {
                    piece.setPosition();
                }
            }
        }
        this.shurikenTouche();
        this.drapeauTouche();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();

        sb.draw(this.background, this.bgPos1.x,0);
        sb.draw(this.background, this.bgPos2.x, 0);
        sb.draw(this.character.getTexture(), this.character.getPosition().x, this.character.getPosition().y);

        //Placer en tout temps mon score et mon temps en haut de l'écran
        this.scoreText.draw(sb, "Pièces : " + this.score, this.cam.position.x + 125, this.cam.position.y + 340);
        this.timeText.draw(sb, "Time : " + this.time, this.cam.position.x - 425, this.cam.position.y + 340);

        for(Obstacle obstacle : this.obstaclesShuriken)
            sb.draw(obstacle.getTexture(), obstacle.getPosition().x, obstacle.getPosition().y);

        for(Piece piece : this.allPieces)
            sb.draw(piece.getTexture(), piece.getPosition().x, piece.getPosition().y);

        sb.draw(this.drapeau.getTexture(), this.drapeau.getPosition().x, this.drapeau.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose()
    {
        this.background.dispose();
        this.character.dispose();
        this.drapeau.dispose();
        this.scoreText.dispose();
        this.timeText.dispose();

        for(Obstacle obstacle : this.obstaclesShuriken)
            obstacle.dispose();

        for(Piece piece : this.allPieces)
            piece.dispose();
    }

    /**
     * @return le score compté par les pièces
     */
    public static int getScore() {return score;}

    /**
     * @return la position 1 du background
     */
    public static Vector2 getBgPos1() {return bgPos1;}

    /**
     * @return la position 2 du background
     */
    public static Vector2 getBgPos2() {return bgPos2;}

    /**
     * @param i
     * Met en place le score passé en paramètre
     */
    public static void setScore(int i) {score = i; }

    private void updateBackground()
    {
        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 > this.bgPos1.x + this.background.getWidth())
            this.bgPos1.add(this.background.getWidth() * 2,0);

        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 > this.bgPos2.x + this.background.getWidth())
            this.bgPos2.add(this.background.getWidth() * 2, 0);

        //Si je me déplace vers la gauche, le background s'adapte
        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 < this.bgPos1.x - this.background.getWidth())
            this.bgPos1.sub(this.background.getWidth() * 2,0);

        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 < this.bgPos2.x - this.background.getWidth())
            this.bgPos2.sub(this.background.getWidth() * 2, 0);
    }

    /**
     * @return vrai si les positions x et y du shuriken correspondent aux positions x et y du personnage
     * Lance la fin de partie également
     */
    public static boolean shurikenTouche()
    {
        float xCharacter = character.getPosition().x + (character.getTexture().getWidth() - 20);
        float yCharacter = character.getPosition().y + (character.getTexture().getHeight() - 20);

        // Si je touche un shuriken je meurs
        for(int i = 0; i < obstaclesShuriken.size; i++)
        {
            Obstacle obs = obstaclesShuriken.get(i);
            float xObs = obs.getPosition().x + (obs.getTexture().getWidth() - 20);
            float yObs = obs.getPosition().y + (obs.getTexture().getHeight() - 20);

            if(character.getPosition().x < xObs  && xCharacter > obs.getPosition().x &&
                    character.getPosition().y < yObs  && yCharacter > obs.getPosition().y)
            {
                obstaclesShuriken.clear();
                allPieces.clear();
                bgPos1.set(0,0);
                bgPos2.set(0,0);
                gsm.set(new FinDePartie(gsm));
                return true;
            }
        }
        return false;
    }

    /**
     * @return vrai si les positions x et y du drapeau correspondent aux positions x et y du personnage
     * Lance la fin de partie également
     */
    public static boolean drapeauTouche()
    {
        float xDrapeau = drapeau.getPosition().x + (drapeau.getTexture().getWidth() - 20);
        float yDrapeau = drapeau.getPosition().y + (drapeau.getTexture().getHeight() - 20);
        float xCharacter = character.getPosition().x + (character.getTexture().getWidth() - 20);
        float yCharacter = character.getPosition().y + (character.getTexture().getHeight() - 20);

        // Si je touche le drapeau je gagne
        if(character.getPosition().x < xDrapeau  && xCharacter > drapeau.getPosition().x &&
                character.getPosition().y < yDrapeau  && yCharacter > drapeau.getPosition().y)
        {
            obstaclesShuriken.clear();
            allPieces.clear();
            bgPos1.set(0,0);
            bgPos2.set(0,0);
            gsm.set(new FinDePartie(gsm));
            return true;
        }
        return false;
    }
}
