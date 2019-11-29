package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Random;

public class PowerUp extends ActorBeta {
    //Skin
    Skin skin;
    Vector2 size;

    //Player
    Character player;

    //Position
    Vector2 spawnPoint;
    Random randPosX;
    float posY;

    //Label
    Label label;

    //Power UP
    int powerUp;
    Random randPowerUp;

    //Snakes
    Snake[] snakes;

    PowerUp(float y, Stage s, Character _player, Snake[] _snakes)
    {
        loadTexture("Character/Powerup.png");

        //Ratio
        float ratio = Gdx.graphics.getWidth() / 400.0f;

        //Random Power UP
        randPowerUp = new Random();
        powerUp = randPowerUp.nextInt(5) + 1;

        //Skin
        skin = new Skin(Gdx.files.internal("Skin/holo/skin/dark-hdpi/Holo-dark-hdpi.json"));

        //Label
        label = new Label("" + powerUp, skin);
        label.setFontScale(1 * ratio);
        label.setPosition(getX() - label.getWidth()/2, getY() - label.getHeight()/2);

        //Player
        player = _player;

        //Snakes
        snakes = _snakes;

        //Set Size
        size = new Vector2(15, 15);
        size.x = Gdx.graphics.getWidth()/size.x;
        size.y = Gdx.graphics.getWidth()/size.y;
        setSize(size.x, size.y);

        //Set Position
        randPosX = new Random();
        spawnPoint = new Vector2(randPosX.nextInt(Gdx.graphics.getWidth() - (int)getWidth()), y);
        setPosition(spawnPoint.x, spawnPoint.y);
        posY = spawnPoint.y;

        //Set boundary
        setBoundaryRectangle();
        s.addActor(this);
        s.addActor(label);
    }

    public void Movement(float dt)
    {
        posY -= GameScreen.SPEED * dt;
        setPosition(spawnPoint.x,posY);
        label.setPosition(getX(), getY());

        if(posY + getHeight() < 0)
        {
            Reset(findHighestSnake());
        }

    }

    public float findHighestSnake()
    {
        float highest = 0.0f;
        for(int i = 0; i < snakes.length; i++)
        {
            for(int j = 0; j < snakes[i].snakeNodes.length; j++)
            {
                if(highest < snakes[i].snakeNodes[j].getY())
                    highest = snakes[i].snakeNodes[j].getY();
            }
        }
        return highest;
    }


    public void Reset(float snakeHeight)
    {
        posY = spawnPoint.y;
        powerUp = randPowerUp.nextInt(5) + 1;
        label.setText(""+powerUp);
        setPosition(randPosX.nextInt(Gdx.graphics.getWidth()), snakeHeight);
        label.setPosition(getX(), getY());
    }

    @Override
    public void act(float dt)
    {
        super.act(dt);

        Movement(dt);

        if(this.overlaps(player))
        {
            player.GetPowerUp(powerUp);
            this.remove();
            label.remove();
        }


    }

}
