package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MovingBackground
{
    GameScreenBackground[] backgrounds;
    int imageNum;
    float respawnY;

    public MovingBackground(int num, Stage stage)
    {
        imageNum = num;
        backgrounds = new GameScreenBackground[imageNum];
        respawnY = Gdx.graphics.getHeight() * 1.05f;
        for(int i = imageNum - 1; i >= 0; i--)
        {
            backgrounds[i] = new GameScreenBackground(0,respawnY * i, stage);
        }
    }

    public void BackgroundMovement(float speed)
    {
        for(int i = imageNum - 1; i >= 0; i--)
        {
            float xPos = backgrounds[i].getX();
            backgrounds[i].setPosition(xPos, backgrounds[i].getY() - speed);
            if(backgrounds[i].getTop() <= 0)
            {
                backgrounds[i].setPosition(xPos, backgrounds[((i + 1) % imageNum)].getTop() - speed);
            }
        }
    }
}
