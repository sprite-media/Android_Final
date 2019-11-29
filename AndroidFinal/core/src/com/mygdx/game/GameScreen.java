package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
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
    public static int curScore;
    boolean isColliding;
    float hitTime;
    float curHitTime;

    PasueScreen pauseScreen;

    Skin skin;
    Table HUDTable;
    Label scoreLabel;
    TextButton pauseButton;



    boolean isPuaseScreenOn = false;
    boolean isPasueScreenCreated = false;

    //Player
    Character character;

    //Snake
    int snakeNum;
    float gap;
    Snake[] snakes;
    float speed;


    //PowerUp
    PowerUp powerUp;
    @Override
    public void initialize()
    {
        SPEED = 500;
        isColliding = false;
        hitTime = 0.01f;
        curHitTime = 0.0f;
        //Ratio
        float ratio = fullWidth / 1080;
        speed = fullWidth * 0.05f;

        skin = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));
        HUDTable = new Table();
        HUDTable.setSize(WINDOW_WIDTH  , WINDOW_HEIGHT * 0.1f);
        HUDTable.setPosition(0, WINDOW_HEIGHT * 0.93f);

        curScore = 0;
        scoreLabel = new Label("" + curScore, skin);
        scoreLabel.setFontScale(5 * ratio);



        pauseButton = new TextButton("Pause", skin);
        pauseButton.getLabel().setFontScale(5 * ratio);

        HUDTable.add(pauseButton).fill().expand().padRight(WINDOW_WIDTH * 0.55f);
        HUDTable.add(scoreLabel).fill().expandY();
        mainStage.addActor(HUDTable);

        character = new Character(WINDOW_WIDTH/2, WINDOW_HEIGHT/2, mainStage, this);

        snakeNum = 3;
        gap = WINDOW_HEIGHT / snakeNum;
        snakes = new Snake[snakeNum];
        for(int i = 0; i < snakeNum; i++)
        {
            snakes[i] = new Snake(WINDOW_HEIGHT + (i * gap), mainStage, character);
        }


    }

    public void HUD()
    {
        scoreLabel.setText("" + curScore);
    }

    @Override
    public void update(float dt)
    {
        HUD();
        ScreenInteraction();
        if(isPuaseScreenOn) return;
        CollistionCheck(dt);
        for(int i = 0; i < snakeNum; i++)
        {
            snakes[i].SnakeMovement(SPEED * dt, WINDOW_HEIGHT - (WINDOW_WIDTH /6), mainStage);
        }
        character.Movement(speed * dt);
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
                snakes[i].snakeNodes[j].SetOffImg(dt);
                if(snakes[i].snakeNodes[j].blood.justKilled)
                {
                    snakes[i].snakeNodes[j].blood.isActivated = true;
                    snakes[i].snakeNodes[j].blood.justKilled = false;
                }
                snakes[i].DestoryInDelay(dt);
                snakes[i].snakeNodes[j].blood.PlayAnimation(snakes[i].snakeNodes[j].getX(),
                        snakes[i].snakeNodes[j].getY(), dt);
                if(!snakes[i].isBelow && snakes[i].snakeNodes[j].Hit(character))
                {
                    curHitTime += dt;
                    if(curHitTime >= hitTime)
                    {
                        curHitTime = 0;
                        character.GetHit(1);
                        snakes[i].HitSnake(j, 1);
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
        if(!isColliding) SPEED = 500;
    }
}
