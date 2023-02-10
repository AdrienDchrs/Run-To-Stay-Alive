/**
 * @author Adrien Decharrois
 * @version 1.0
 */

package com.emexgames.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.emexgames.game.manager.GameStateManager;

public abstract class GameState
{
    protected OrthographicCamera cam;
    public GameStateManager gsm;

    protected GameState(GameStateManager gsm)
    {
        this.cam = new OrthographicCamera();
        this.gsm = gsm;
    }

    /**
     * Surveille les entrées (Si on appuye sur l'écran etc..) les contrôles du jeu
     */
    protected abstract void handleInput();
    /**
     * Permet de mettre à jour l'écran selon ce que l'on fait
     * @param dt
     */
    public abstract void update(float dt);
    /**
     * Dessine notre écran, SpriteBatch permet de dessiner sur l'écran
     * @param sb
     */
    public abstract void render(SpriteBatch sb);

    /**
     * Libère l'espace mémoire utilisée
     */
    public abstract void dispose();
}
