/**
 * @author Adrien Decharrois
 * @version 1.0
 */

package com.emexgames.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * Classe utilisée pour faire animer à droite mon personnage principal
 */
public class AnimationRight
{
    private Array<Texture> alTextures;
    private float maxFrameTime, currentFrameTime;
    private int frameCount, frame;

    public AnimationRight(int frameCount, float cycleTime)
    {
        this.frameCount = frameCount;
        this.alTextures = new Array<Texture>();

        // Ligne pour ajouter tous les mouvements du personnage
        for(int i = 1; i <= this.frameCount; i++)
        {
            this.alTextures.add(new Texture("Characters/WalkRight/p1_walkRight" + String.valueOf(i) + ".png"));
        }

        this.maxFrameTime = cycleTime / this.frameCount;
        this.frame = 0;
    }

    public void update(float dt)
    {
        this.currentFrameTime += dt;

        if (this.currentFrameTime > this.maxFrameTime)
        {
            this.frame++;
            this.currentFrameTime = 0;
        }

        if (this.frame >= this.frameCount) {
            this.frame = 0;
        }
    }

    public Texture getTexture()
    {
        return this.alTextures.get(this.frame);
    }
}
