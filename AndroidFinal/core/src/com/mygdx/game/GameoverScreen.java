package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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


    @Override
    public void initialize()
    {
    	// Loading skin
		orange = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));

		// Initializing table
		table = new Table(orange);
		table.setSize(Width, Height);
		table.setFillParent(true);
		table.setPosition(HalfWidth-table.getWidth()*0.5f, HalfHeight-table.getHeight()*0.4f);


		float ratio = Width/400.0f;

		// Label
	    Label gameoverText = new Label("GAME OVER", orange);
	    gameoverText.setFontScale(3.5f * ratio);

	    // Buttons
	    restartButton = new TextButton("Restart", orange);
	    restartButton.getLabel().setFontScale(2 * ratio);

	    mainmenuButton = new TextButton("To Main Menu", orange);
	    mainmenuButton.getLabel().setFontScale(1.8f * ratio);

	    InitButton();

	    table.add(gameoverText);
	    table.row();
		table.add(restartButton).minSize(HalfWidth, Height*0.08f).padTop(HalfHeight*0.6f);
		table.row();
	    table.add(mainmenuButton).minSize(HalfWidth, Height*0.08f).padTop(HalfHeight*0.1f);

		mainStage.addActor(table);
    }

    public void InitButton()
    {
    	restartButton.addListener(new EventListener()
	    {
		    @Override
		    public boolean handle(Event event)
		    {
			    //Add screen transition
			    return false;
		    }
	    });

    	mainmenuButton.addListener(new EventListener()
	    {
		    @Override
		    public boolean handle(Event event)
		    {
			    //Add screen transition
			    return false;
		    }
	    });
    }

    @Override
    public void update(float dt)
    {

    }
}
