package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Snake extends ActorBeta
{
    final static float SPEED = -200;
    Vector2 OriginalPos;

    public Snake(float x, float y, Stage stage)
    {
        super(x, y, stage);

        loadTexture("snakeTemp.png");
        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.02f);

        OriginalPos = new Vector2(x,y);

        setPosition(x, y);

        setBoundaryPolygon(4);
    }


    public void SnakeMove(float dt, Character character)
    {
        setPosition(0, getY() + SPEED * dt);

        if(character.overlaps(this))
        {
            character.isDead = true;
            character.remove();
        }

        if(getTop() < 0)
        {
            setPosition(OriginalPos.x, OriginalPos.y);
        }
    }
}
