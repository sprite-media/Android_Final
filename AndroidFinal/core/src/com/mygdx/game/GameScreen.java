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
    final static float WINDOW_WIDTH     = Gdx.graphics.getWidth();
    final static float WINDOW_HEIGHT    = Gdx.graphics.getHeight();
    public static float SPEED;

    boolean isColliding;
    float hitTime;
    float curHitTime;

    PasueScreen pauseScreen;

    Skin skin;
    Table HUDTable;
    Label scoreLabel;
    TextButton pauseButton;

    int curScore;

    boolean isPuaseScreenOn = false;
    boolean isPasueScreenCreated = false;

    //Player
    Character character;

    //Snake
    int snakeNum;
    float gap;
    Snake[] snakes;
    //Snake snake;
    float speed;


    @Override
    public void initialize()
    {
        SPEED = 200;
        isColliding = false;
        hitTime = 0.1f;
        curHitTime = 0.0f;
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

        //Snake
        snakeNum = 3;
        gap = WINDOW_HEIGHT / snakeNum;
        snakes = new Snake[snakeNum];
        for(int i = 0; i < snakeNum; i++)
        {
            snakes[i] = new Snake(WINDOW_HEIGHT + (i * gap), mainStage);
        }

        //Character
        character = new Character(WINDOW_WIDTH/2, WINDOW_HEIGHT/10, mainStage, this);
    }

    @Override
    public void update(float dt)
    {
        CollistionCheck(dt);
        character.moveBy((Gdx.input.getDeltaX()*dt*speed), 0);

       // snake.SnakeMovement(200 * dt, WINDOW_HEIGHT, mainStage);
        for(int i = 0; i < snakeNum; i++)
        {
            snakes[i].SnakeMovement(SPEED * dt, WINDOW_HEIGHT - (WINDOW_WIDTH /6), mainStage);
        }
        ScreenInteraction();
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

    void CollistionCheck(float dt)
    {
        isColliding = false;
        for(int i = 0; i < snakeNum; i++)
        {
            for(int j = 0; j < Snake.nodeNum; j++)
            {
                if(!snakes[i].isBelow && snakes[i].snakeNodes[j].Hit(character))
                {
                    curHitTime += dt;
                    if(curHitTime >= hitTime)
                    {
                        curHitTime = 0;
                        character.GetHit(1);
                        snakes[i].snakeNodes[j].GetHit(1);
                    }
                    isColliding = true;
                    SPEED = 0;
                    break;
                }
                else if(snakes[i].isBelow)
                {
                    if(snakes[i].snakeNodes[j].Hit(character))
                    {
                        if(character.getX() >= snakes[i].snakeNodes[j].getX()) //snake left player right
                        {
                            if(character.getX() < snakes[i].snakeNodes[j].getRight() && character.getRight() > snakes[i].snakeNodes[j].getRight())
                            {
                                character.setPosition(snakes[i].snakeNodes[j].getRight() + 1, character.getY());
                            }
                        }
                        if(character.getRight() <= snakes[i].snakeNodes[j].getRight()) //snake right player left
                        {
                           if(character.getRight() > snakes[i].snakeNodes[j].getX())
                           {
                               character.setPosition(snakes[i].snakeNodes[j].getX() - character.getWidth() - 1, character.getY());
                           }
                        }
                    }
                }
            }
        }
        if(!isColliding) SPEED = 200;
    }
}
