package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class MainScreen extends ScreenBeta
{
    final static float WINDOW_WIDTH    = Gdx.graphics.getWidth();
    final static float WINDOW_HEIGHT   = Gdx.graphics.getHeight();

    PasueScreen pauseScreen;

    Skin skin;
    Table HUDTable;
    Label scoreLabel;
    TextButton pauseButton;

    int curScore;

    boolean isPuaseScreenOn = false;
    boolean isPasueScreenCreated = false;

    Character character;

    @Override
    public void initialize()
    {
        skin = new Skin(Gdx.files.internal("Skin/flat-earth/skin/flat-earth-ui.json"));
        HUDTable = new Table();
        HUDTable.setSize(WINDOW_WIDTH  , WINDOW_HEIGHT * 0.06f);
        HUDTable.setPosition(0, WINDOW_HEIGHT * 0.93f);

        scoreLabel = new Label("0", skin);
        curScore = 0;

        pauseButton = new TextButton("Pause", skin);

        HUDTable.add(pauseButton).fill().expandY().padRight(WINDOW_WIDTH * 0.55f);
        HUDTable.add(scoreLabel).fill().expandY();
        mainStage.addActor(HUDTable);

        character = new Character(WINDOW_WIDTH/2, WINDOW_HEIGHT/10, mainStage);

    }

    @Override
    public void update(float dt)
    {
        ScreenInteraction();
        character.CharacterMove();
    }

    void ScreenInteraction()
    {
        if(pauseButton.isPressed() && !isPuaseScreenOn)
        {
            if(!isPasueScreenCreated)
            {
                pauseScreen = new PasueScreen(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, mainStage, skin);
                mainStage.addActor(pauseScreen);
            }
            else
            {
                mainStage.addActor(pauseScreen);
                pauseScreen.ReactivatePasueScreen(mainStage, WINDOW_WIDTH, WINDOW_HEIGHT);
            }
            isPuaseScreenOn = true;
            isPasueScreenCreated = true;
        }

        if(isPuaseScreenOn)
        {
           if(pauseScreen.RestartGame())
               isPuaseScreenOn = false;
        }

    }

}
