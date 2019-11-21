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

        float ratio = Gdx.graphics.getWidth()/400;
        int heightSegment = 10;

        pauseTable = new Table();
        pauseTable.setSize(sizeX, sizeY);
        pauseTable.setPosition(0,0);

        pauseLabel = new Label("            PAUSE", skin);
        pauseLabel.setFontScale(3 * ratio);
        restartButton = new TextButton("RESTART", skin);
        backToMainButton = new TextButton("BACK TO MAIN MENU", skin);

        pauseTable.add(pauseLabel).fill().expand().padBottom(sizeY/heightSegment*3).row();
        pauseTable.add(restartButton).fill().padBottom(sizeY/heightSegment).row();
        pauseTable.add(backToMainButton).fill().padBottom(sizeY/heightSegment).row();
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
