package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class GameScreen extends ScreenBeta
{
    final static float WINDOW_WIDTH     = Gdx.graphics.getWidth();
    final static float WINDOW_HEIGHT    = Gdx.graphics.getHeight();
    public static float SPEED;
    public static float curSpeed;
    public static float speedLevel;
    float originSpeed;
    public static int curScore;
    public static int scoreCount;
    boolean isColliding;
    float hitTime;
    float curHitTime;

    PasueScreen pauseScreen;

    Skin skin;
    Label scoreLabel;
    ImageButton pauseButton;

    boolean isPuaseScreenOn = false;
    boolean isPasueScreenCreated = false;

    //Player
    Character character;

    //Snake
    int snakeNum;
    float gap;
    Snake[] snakes;
    float speed;

    Sound buttonClick;
    Sound hitSound;

    //PowerUp
    PowerUp powerUp;

    MovingBackground movingBackground;

    @Override
    public void initialize()
    {
        buttonClick = Gdx.audio.newSound(Gdx.files.internal("Audios/ButtonPressed.mp3"));
        buttonClick.setVolume(0, volumeMultiplier);

        hitSound = Gdx.audio.newSound(Gdx.files.internal("Audios/ButtonPressed.mp3"));
        hitSound.setVolume(0, volumeMultiplier);

        SPEED = 200;
        originSpeed = 200;
        curSpeed = 200;
        speedLevel = 1;
        scoreCount = 1;
        isColliding = false;
        hitTime = 0.01f;
        curHitTime = 0.0f;
        //Ratio
        float ratio = fullWidth / 1080;
        speed = fullWidth * 0.05f;

        skin = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));

        movingBackground = new MovingBackground(2, mainStage);

        Texture pauseTexture = new Texture("Textures/PauseButton.png");
        TextureRegion pauseTextureRegion = new TextureRegion(pauseTexture);
        TextureRegionDrawable pauseTextureRegionDrawable = new TextureRegionDrawable(pauseTextureRegion);
        pauseTextureRegionDrawable.setMinWidth(WINDOW_WIDTH * 0.07f);
        pauseTextureRegionDrawable.setMinHeight(WINDOW_WIDTH * 0.07f);
        pauseButton = new ImageButton(pauseTextureRegionDrawable);
        pauseButton.setSize(WINDOW_WIDTH * 0.1f, WINDOW_WIDTH * 0.1f);
        pauseButton.setPosition(0, WINDOW_HEIGHT - pauseButton.getHeight());

        curScore = 0;
        scoreLabel = new Label("" + curScore, skin);
        scoreLabel.setFontScale(5.0f * ratio);
        scoreLabel.setSize(WINDOW_WIDTH * 0.4f, WINDOW_WIDTH * 0.1f);
        scoreLabel.setPosition(pauseButton.getRight() + 30, WINDOW_HEIGHT - scoreLabel.getHeight());


        character = new Character(WINDOW_WIDTH/2, WINDOW_HEIGHT/2.5f, mainStage, this);

        snakeNum = 3;
        gap = WINDOW_HEIGHT / snakeNum;
        snakes = new Snake[snakeNum];
        for(int i = 0; i < snakeNum; i++)
        {
            snakes[i] = new Snake(WINDOW_HEIGHT + (i * gap), mainStage, character);
        }

        mainStage.addActor(pauseButton);
        mainStage.addActor(scoreLabel);
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
        movingBackground.BackgroundMovement(SPEED * dt);
        for(int i = 0; i < snakeNum; i++)
        {
            snakes[i].SnakeMovement(SPEED * dt, WINDOW_HEIGHT - (WINDOW_WIDTH /6), mainStage);
        }
        character.Movement(speed * dt);
        UpdateSpeed();
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
            buttonClick.play();
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
                    hitSound.play();
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
        if(!isColliding) SPEED = curSpeed;
    }

    public void UpdateSpeed()
    {
        if(curScore >= 150 * scoreCount)
        {
            scoreCount++;

            if (speedLevel >= 5) return;

            int temp = (int)(curScore / 100);
            speedLevel = 1 + (0.1f * (float) temp);

            curSpeed = originSpeed * speedLevel;
        }
    }
}
