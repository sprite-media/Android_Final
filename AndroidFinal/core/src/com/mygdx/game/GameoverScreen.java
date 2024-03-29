package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameoverScreen extends ScreenBeta
{
	Skin orange;
	Table table;
	TextButton restartButton;
	TextButton mainmenuButton;
	Label scoreLabel;

	Sound buttonClick;

	int curButton;
	float buttonTimer;

    @Override
    public void initialize()
    {
		float ratio = fullWidth / 400.0f;

		curButton = 0;
		buttonTimer = 0.0f;

		// Loading skin
		orange = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));

		// Initializing table
		table = new Table(orange);
		table.setSize(fullWidth, fullHeight);
		table.setFillParent(true);
		table.setPosition(halfWidth-table.getWidth()*0.5f, halfHeight-table.getHeight()*0.4f);

		scoreLabel = new Label("YOUR SCORE: " + GameScreen.curScore, orange);
		scoreLabel.setFontScale(2*ratio);


		// Label
	    Label gameoverText = new Label("GAME OVER", orange);
	    gameoverText.setFontScale(3.5f * ratio);

	    // Buttons
	    restartButton = new TextButton("Restart", orange);
	    restartButton.getLabel().setFontScale(2 * ratio);

	    mainmenuButton = new TextButton("To Main Menu", orange);
	    mainmenuButton.getLabel().setFontScale(1.8f * ratio);

	    //InitButton();

		buttonClick = Gdx.audio.newSound(Gdx.files.internal("Audios/ButtonPressed.mp3"));
		buttonClick.setVolume(0, volumeMultiplier);

	    table.add(gameoverText);
	    table.row();
	    table.add(scoreLabel).center().expand();
	    table.row();
		table.add(restartButton).minSize(halfWidth, fullHeight*0.08f).padTop(halfHeight*0.6f);
		table.row();
	    table.add(mainmenuButton).minSize(halfWidth, fullHeight*0.08f).padTop(halfHeight*0.1f);
		table.setColor(1,1,1,0);
	    table.addAction(Actions.fadeIn(1.0f));

		mainStage.addActor(table);
    }

    //public void InitButton()
    //{
    //	restartButton.addListener(new EventListener()
	//    {
	//	    @Override
	//	    public boolean handle(Event event)
	//	    {
	//		    //Add screen transition
	//			buttonClick.play();
	//		    MyGame.setActiveScreen(new GameScreen());
	//		    return false;
	//	    }
	//    });
//
    //	mainmenuButton.addListener(new EventListener()
	//    {
	//	    @Override
	//	    public boolean handle(Event event)
	//	    {
	//		    //Add screen transition
	//			buttonClick.play();
	//		    MyGame.setActiveScreen(new MainmenuScreen());
	//		    return false;
	//	    }
	//    });
    //}

    @Override
    public void update(float dt)
    {
		ButtonPressed(dt);
    }

	void ButtonPressed(float dt)
	{
		if(restartButton.isPressed())
		{
			buttonTimer = 0.0f;
			curButton = 1;
		}
		else if(mainmenuButton.isPressed())
		{
			buttonTimer = 0.0f;
			curButton = 2;
		}

		if(curButton != 0 ) buttonTimer += dt;

		if(buttonTimer >= 0.2f)
		{
			buttonClick.play();
			switch (curButton)
			{
				case 0:
					buttonTimer = 0.0f;
					break;
				case 1:
					MyGame.setActiveScreen(new GameScreen());
					break;
				case 2:
					MyGame.setActiveScreen(new MainmenuScreen());
					break;
			}
		}
	}
}
