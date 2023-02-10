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
 * Classe permettant de placer un obstacle
 */
public class Obstacle
{
    private Texture texture;
    private Vector2 position;

    private Random rdm;

    public Obstacle(int i)
    {
        this.texture = new Texture("shuriken.png");
        this.rdm = new Random();

        int x = rdm.nextInt((800 - 200) + 200) * (i + 1);
        int y = rdm.nextInt(550 - 210) + 210;

        this.position = new Vector2(x, y);
    }

    /**
     * @return la texture de l'obstacle
     */
    public Texture getTexture()
    {
        return this.texture;
    }

    /**
     *
     * @return la position de l'obstacle
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
