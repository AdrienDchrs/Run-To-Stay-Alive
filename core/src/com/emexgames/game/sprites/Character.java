/**
 * @author Adrien Decharrois
 * @version 1.0
 */

package com.emexgames.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.emexgames.game.util.Constants;


/**
 * Classe du personnage principal
 */
public class Character
{
    private Texture texture;
    private Vector2 position, fall;

    private AnimationRight walkingRight;
    private AnimationLeft  walkingLeft;

    public Character(float x, float y)
    {
        this.texture = new Texture("Characters/WalkRight/p1_walkRight1.png");
        this.position = new Vector2(x,y);
        this.fall = new Vector2(0,0);

        this.walkingRight = new AnimationRight(8, 0.5f);
        this.walkingLeft  = new AnimationLeft(8, 0.5f);

    }

    public void update(float dt)
    {
        this.fall.add(0, Constants.GRAVITY);
        this.fall.scl(dt);
        this.position.add(this.fall);
        this.fall.scl(1/dt);

        // Pour ne pas qu'il tombe du sol == gestion de collision
        if(this.position.y < 210)
        {
            this.position.y = 200;
            this.fall.y   = 0;
        }
        if(this.position.y > 700)
        {
            this.position.y = 700;
        }

        if(this.position.x < 0)
        {
            this.position.x = 0;
        }
    }

    /**
     * Permet de défiler l'animation à droite de l'écran
     * Mise à jour de texture
     */
    public void moveRight()
    {
        this.position.x += Constants.CHAR_SPEED;
        this.texture = this.walkingRight.getTexture();
        this.walkingRight.update(Gdx.graphics.getDeltaTime());
    }

    /**
     * Permet de défiler l'animation à gauche de l'écran
     * Mise à jour de texture
     */
    public void moveLeft()
    {
        this.position.x -= Constants.CHAR_SPEED;
        this.texture = this.walkingLeft.getTexture();
        this.walkingLeft.update(Gdx.graphics.getDeltaTime());
    }

    /**
     * Méthode permettant de faire sauter le personnage
     * Gestion de collision avec le sol
     */
    public void jump()
    {
        if(this.fall.y == 0)
        {
            this.fall.y = 875;
        }

        if(this.fall.y > 0 || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            this.texture = new Texture("Characters/JumpRight/p1_jump3.png");
        }
    }

    /**
     * @return la texture du personnage
     */
    public Texture getTexture()
    {
        return this.texture;
    }

    /**
     * @return la position du personnage
     */
    public Vector2 getPosition()
    {
        return this.position;
    }

    public void dispose()
    {
        this.texture.dispose();
    }
}
