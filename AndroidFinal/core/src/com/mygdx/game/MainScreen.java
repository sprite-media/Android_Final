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
    Snake snake;

    float speed;

    @Override
    public void initialize()
    {
        speed = fullWidth * 0.05f;

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
        snake = new Snake(0, WINDOW_HEIGHT  * 0.9f, mainStage);

    }

    @Override
    public void update(float dt)
    {
        //character.setPosition(character.getX()+(Gdx.input.getDeltaX()*dt*speed), character.getY());
        character.moveBy((Gdx.input.getDeltaX()*dt*speed), 0);

        ScreenInteraction();
        character.CharacterMove();
        snake.SnakeMove(dt, character);
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
        if(isPasueScreenCreated)
        {
            pauseScreen.BackToMainMenu();
        }
    }

}
