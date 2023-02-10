/**
 * @author Adrien Decharrois
 * @version 1.0
 */

package com.emexgames.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Classe drapeau permettant de valider le niveau actuel
 */
public class Drapeau
{
    private Texture texture;
    private Vector2 position;

    public Drapeau(float x, float y)
    {
        this.texture = new Texture("flag.png");
        this.position = new Vector2(x, y);
    }

    /**
     * @return la texture du drapeau
     */
    public Texture getTexture()
    {
        return this.texture;
    }

    /**
     * @return la position du drapeau
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
