package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreenBackground extends ActorBeta
{
    String fileName;

    public GameScreenBackground(float x, float y, Stage stage)
    {
        super(x, y, stage);
        fileName = "GameScreenBg.png";
        loadTexture(fileName);
        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 1.05f);
    }
}
