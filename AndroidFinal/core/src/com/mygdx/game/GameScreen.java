package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class GameScreen extends ScreenBeta
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

    Character player;
    Snake snake;
    float speed;

    @Override
    public void initialize()
    {
        //Ratio
        float ratio = fullWidth / 1080;
        speed = fullWidth * 0.05f;

        skin = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));
        HUDTable = new Table();
        HUDTable.setSize(WINDOW_WIDTH  , WINDOW_HEIGHT * 0.1f);
        HUDTable.setPosition(0, WINDOW_HEIGHT * 0.93f);

        scoreLabel = new Label("0", skin);
        scoreLabel.setFontScale(5 * ratio);

        curScore = 0;

        pauseButton = new TextButton("Pause", skin);
        pauseButton.getLabel().setFontScale(5 * ratio);

        HUDTable.add(pauseButton).fill().expand().padRight(WINDOW_WIDTH * 0.55f);
        HUDTable.add(scoreLabel).fill().expandY();
        mainStage.addActor(HUDTable);

        snake = new Snake(WINDOW_HEIGHT, mainStage);
        player = new Character(WINDOW_WIDTH/2, WINDOW_HEIGHT/10, mainStage, this);
    }

    @Override
    public void update(float dt)
    {
        //character.setPosition(character.getX()+(Gdx.input.getDeltaX()*dt*speed), character.getY());
        player.moveBy((Gdx.input.getDeltaX()*dt*speed), 0);

        snake.SnakeMovement(200 * dt, WINDOW_HEIGHT, mainStage);

        ScreenInteraction();
        snake.SnakeMove(dt, player);
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
