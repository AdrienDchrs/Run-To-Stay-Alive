/**
 * @author Adrien Decharrois
 * @version 1.0
 */

package com.emexgames.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.emexgames.game.util.Constants;

import java.util.Random;

/**
 * Classe permettant de créer une pièce
 */
public class Piece
{
    private Texture texture;
    private Vector2 position;

    private Random rdm;

    public Piece(int i)
    {
        this.texture = new Texture("piece.png");
        this.rdm = new Random();

        int y = rdm.nextInt(400 - 210) + 210;

        this.position = new Vector2(this.rdm.nextInt(Constants.VIEWPORT_WIDTH - 0)  * (i + 1), y);
    }

    /**
     * @return la texture de la pièce
     */
    public Texture getTexture()
    {
        return this.texture;
    }

    /**
     * @return la position de la pièce
     */
    public Vector2 getPosition()
    {
        return this.position;
    }

    /**
     * Met en place une position aléatoire de la pièce
     */
    public void setPosition()
    {
        this.position.set(this.rdm.nextInt(Constants.VIEWPORT_WIDTH - 0),
                this.rdm.nextInt(400 - 210) + 210);
    }

    public void dispose()
    {
        this.texture.dispose();
    }

}
