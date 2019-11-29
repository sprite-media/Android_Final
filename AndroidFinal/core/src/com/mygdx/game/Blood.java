package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

public class Blood extends ActorBeta
{
    String[] bloodFiles = null;
    float frameRate = 0.01f;
    float curframe = 0.0f;
    int maxNum = 4;
    int curNum = 0;
    float size;

    public boolean justKilled = false;

    public boolean isActivated = false;

    public Blood(float x, float y, Stage stage)
    {
        super(x, y, stage);

        bloodFiles = new String[maxNum];
        for(int i = 0; i < maxNum; i++)
        {
            bloodFiles[i] = "Effect/Blood/Blood_" + i + ".png";
        }
        size = Gdx.graphics.getWidth()/Snake.nodeNum;
        Random random = new Random();
        switch (random.nextInt(3))
        {
            case 0:
                size *= 1.0f;
                break;
            case 1:
                size *= 1.25f;
                break;
            case 2:
                size *= 1.5f;
                break;
        }
        setSize(size, size);
    }

    public void PlayAnimation(float x, float y, float dt)
    {
        if(!isActivated) return;
        Random random = new Random();
        switch (random.nextInt(3))
        {
            case 0:
                frameRate = 0.2f;
                break;
            case 1:
                frameRate = 0.25f;
                break;
            case 2:
                frameRate = 0.3f;
                break;
        }

        curframe += dt;
        if(curframe >= frameRate)
        {
            curframe = 0.0f;
            curNum ++;
            if(curNum >= maxNum)
            {
                isActivated = false;
                loadTexture("Alpha.png");
                justKilled = false;
                curNum = 0;
                isActivated = false;
                return;
            }
            else
            {
                setPosition(x, y);
                loadTexture(bloodFiles[curNum]);
                setSize(size, size);
            }
        }
        return;
    }
}
