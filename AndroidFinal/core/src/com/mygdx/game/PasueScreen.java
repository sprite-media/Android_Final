package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class PasueScreen extends ActorBeta
{
    Table pauseTable;
    Label pauseLabel;
    public TextButton restartButton;
    TextButton backToMainButton;
    boolean isBackToMainButtonPressed = false;
    public boolean isRestartButtonPressed = false;

    public PasueScreen(float x, float y, float sizeX, float sizeY, Stage stage, Skin skin)
    {
        super(x, y, stage);

        loadTexture("PauseScreenBg.png");
        setSize(sizeX, sizeY);

        //Ratio
        float ratio = Gdx.graphics.getWidth() / 1080;
        int heightSegment = 7;

        pauseTable = new Table();
        pauseTable.setSize(sizeX, sizeY);
        pauseTable.setPosition(0,0);
        pauseTable.center();


        pauseLabel = new Label("PAUSE", skin);
        pauseLabel.setFontScale(10 * ratio);
        restartButton = new TextButton("RESTART", skin);
        restartButton.getLabel().setFontScale(5 * ratio);
        backToMainButton = new TextButton("BACK TO MAIN MENU", skin);
        backToMainButton.getLabel().setFontScale(5 * ratio);

        pauseTable.add(pauseLabel).minHeight(Gdx.graphics.getWidth()/10).minWidth(Gdx.graphics.getWidth()/2).padBottom(Gdx.graphics.getHeight()/10).align(0).row();
        pauseTable.add(restartButton).minHeight(Gdx.graphics.getWidth()/10).minWidth(Gdx.graphics.getWidth()/2).padBottom(Gdx.graphics.getHeight()/25).row();
        pauseTable.add(backToMainButton).minHeight(Gdx.graphics.getWidth()/10).minWidth(Gdx.graphics.getWidth()/2).padBottom(Gdx.graphics.getHeight()/25).row();
        //pauseTable.add(pauseLabel).fill().expand().padBottom(sizeY/heightSegment*3).row();
        //pauseTable.add(restartButton).fill().padBottom(sizeY/heightSegment).row();
        //pauseTable.add(backToMainButton).fill().padBottom(sizeY/heightSegment).row();
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
            isRestartButtonPressed = true;
            remove();
            pauseTable.remove();
            return true;
        }
        return false;
    }

    public void ReactivatePasueScreen(Stage stage, float sizeX, float sizeY)
    {
        int heightSegment = 10;

        pauseTable = new Table();
        pauseTable.setSize(sizeX, sizeY);
        pauseTable.setPosition(0,0);

        pauseTable.add(pauseLabel).fill().expand().padBottom(sizeY/heightSegment*3).row();
        pauseTable.add(restartButton).fill().padBottom(sizeY/heightSegment).row();
        pauseTable.add(backToMainButton).fill().padBottom(sizeY/heightSegment).row();
        pauseTable.setColor(1, 1, 1,0);
        pauseTable.addAction(Actions.fadeIn(1.0f));

        isRestartButtonPressed = false;

        stage.addActor(pauseTable);
    }

}
