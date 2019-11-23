package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

public class PowerUp extends ActorBeta {

    Vector2 size;

    //Player
    Character player;

    //Position
    Random randPosX;

    int powerUp;

    PowerUp(Stage s, Character _player)
    {
        loadTexture("Character/Powerup.png");

        player = _player;
        //Set Position
        setPosition(randPosX.nextInt(Gdx.graphics.getWidth()), Gdx.graphics.getHeight());

        //Set Size
        size = new Vector2(6, 6);
        size.x = Gdx.graphics.getWidth()/size.x;
        size.y = Gdx.graphics.getWidth()/size.y;
        setSize(size.x, size.y);

        s.addActor(this);
    }


    @Override
    public void act(float dt)
    {
        super.act(dt);

        if(this == null)
            return;

        if(getY() <= 0)
        {
            this.remove();
        }

        //TODO Chagne position of this obj Y here

        if(this.overlaps(player))
        {
            Random random = new Random();
            powerUp = random.nextInt(10) + 1;
            player.GetPowerUp(powerUp);
            this.remove();
        }
    }
}
