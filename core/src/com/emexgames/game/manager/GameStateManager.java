package com.emexgames.game.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.emexgames.game.states.GameState;

import java.util.Stack;

public class GameStateManager
{
    /* Stack = pile */
    private Stack<GameState> gameStates;

    public GameStateManager() {
        this.gameStates = new Stack<GameState>();
    }

    /**
     * Met en place le 1er élément dans notre stack<GameState>
     */
    public void push(GameState gameState){
        this.gameStates.push(gameState);
    }

    /**
     * Méthode changeant de GameState, .pop() détruit l'élément de la pile au dessus
     */
    public void set(GameState gameState)
    {
        this.gameStates.pop().dispose();
        this.gameStates.push(gameState);
    }

    /**
     * la méthode peek(), modifie l'élément de la pile et la remet en place
     * @param dt
     */
    public void update(float dt)
    {
        this.gameStates.peek().update(dt);
    }

    public void render(SpriteBatch sb)
    {
        this.gameStates.peek().render(sb);
    }

}
