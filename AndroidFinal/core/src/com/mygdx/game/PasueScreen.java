package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PasueScreen extends ActorBeta
{
    public Table pauseTable;
    Label pauseLabel;
    public TextButton restartButton;
    TextButton backToMainButton;
    boolean isBackToMainButtonPressed = false;
    public boolean isRestartButtonPressed = false;

    Sound buttonClick;

    int curButton;
    float buttonTimer;

    public PasueScreen(float x, float y, float sizeX, float sizeY, Stage stage, Skin skin)
    {
        super(x, y, stage);

        loadTexture("PauseScreenBg.png");
        setSize(sizeX, sizeY);

        curButton = 0;
        buttonTimer = 0.0f;

        //Ratio
        float ratio = Gdx.graphics.getWidth() / 1080.0f;

        pauseTable = new Table();
        pauseTable.setSize(sizeX, sizeY);
        pauseTable.setPosition(0,0);
        //pauseTable.center();

        buttonClick = Gdx.audio.newSound(Gdx.files.internal("Audios/ButtonPressed.mp3"));
        buttonClick.setVolume(0, ScreenBeta.volumeMultiplier);

        pauseLabel = new Label("PAUSE", skin);
        pauseLabel.setFontScale(5 * ratio);
        restartButton = new TextButton("RESTART", skin);
        restartButton.getLabel().setFontScale(4 * ratio);
        backToMainButton = new TextButton("BACK TO MAIN MENU", skin);
        backToMainButton.getLabel().setFontScale(4 * ratio);

        pauseTable.add(pauseLabel).minHeight(Gdx.graphics.getWidth()/10.0f).minWidth(Gdx.graphics.getWidth()/5).padBottom(Gdx.graphics.getHeight()/10.0f).row();
        pauseTable.add(restartButton).minHeight(Gdx.graphics.getWidth()/10.0f).minWidth(Gdx.graphics.getWidth()/5).padBottom(Gdx.graphics.getHeight()/25.0f).row();
        pauseTable.add(backToMainButton).minHeight(Gdx.graphics.getWidth()/10.0f).minWidth(Gdx.graphics.getWidth()/5).padBottom(Gdx.graphics.getHeight()/25.0f).row();
        pauseTable.setColor(1, 1, 1,0);
        pauseTable.addAction(Actions.fadeIn(1.0f));

        stage.addActor(pauseTable);
    }

    public void BackToMainMenu()
    {
        if(backToMainButton.isPressed() && !isBackToMainButtonPressed)
        {
            isBackToMainButtonPressed = true;
            MyGame.setActiveScreen(new MainmenuScreen());
        }
    }

    public boolean RestartGame()
    {
        if(restartButton.isPressed() && !isRestartButtonPressed)
        {
            buttonClick.play();
            isRestartButtonPressed = true;
            remove();
            pauseTable.remove();
            return true;
        }
        return false;
    }

    public void ReactivatePasueScreen(Stage stage, float sizeX, float sizeY)
    {
        buttonClick.play();

        pauseTable = new Table();
        pauseTable.setSize(sizeX, sizeY);
        pauseTable.setPosition(0,0);

        //Ratio
        float ratio = Gdx.graphics.getWidth() / 1080.0f;
        pauseLabel.setFontScale(5 * ratio);
        restartButton.getLabel().setFontScale(4 * ratio);
        backToMainButton.getLabel().setFontScale(4 * ratio);

        pauseTable.add(pauseLabel).minHeight(Gdx.graphics.getWidth()/10.0f).minWidth(Gdx.graphics.getWidth()/5).padBottom(Gdx.graphics.getHeight()/10.0f).row();
        pauseTable.add(restartButton).fill().minHeight(Gdx.graphics.getWidth()/10.0f).minWidth(Gdx.graphics.getWidth()/5).padBottom(Gdx.graphics.getHeight()/25.0f).row();
        pauseTable.add(backToMainButton).minHeight(Gdx.graphics.getWidth()/10.0f).minWidth(Gdx.graphics.getWidth()/5).padBottom(Gdx.graphics.getHeight()/25.0f).row();
        pauseTable.setColor(1, 1, 1,0);
        pauseTable.addAction(Actions.fadeIn(1.0f));

        isRestartButtonPressed = false;

        stage.addActor(pauseTable);
    }

}
