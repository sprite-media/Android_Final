package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LeaderboardScreen extends ScreenBeta
{
	Skin holo;
	Table table;

	Label[] rank;
	Label[] scores;

	TextButton back;

	Preferences pref;

	Sound buttonClick;

	int curButton;
	float buttonTimer;

	@Override
	public void initialize()
	{
		float ratio = fullWidth / 400.0f;

		buttonClick = Gdx.audio.newSound(Gdx.files.internal("Audios/ButtonPressed.mp3"));
		buttonClick.setVolume(0, volumeMultiplier);

		pref = Gdx.app.getPreferences("MyPref");

				// Skin
		holo = new Skin(Gdx.files.internal("Skin/holo/skin/dark-hdpi/Holo-dark-hdpi.json"));

		// Table
		table = new Table(holo);
		table.setSize(fullWidth, fullHeight);
		table.setFillParent(true);
		table.setPosition(halfWidth-table.getWidth()*0.5f, halfHeight-table.getHeight()*0.5f);

		Label title = new Label("Leaderboard", holo);
		title.setFontScale(1.4f * ratio);

		back = new TextButton("Back", holo);
		back.getLabel().setFontScale(ratio);

		table.add(back).left().top().pad(fullWidth*0.01f).colspan(2).row();
		table.add(title).expand().center().top().colspan(2).row();

		rank = new Label[10];
		scores = new Label[10];

		for(int i = 0; i < 10; i++)
		{
			rank[i] = new Label(Integer.toString(i+1), holo);
			rank[i].setFontScale(ratio);
			int score = pref.getInteger(Integer.toString(i), 0);
			scores[i] = new Label(Integer.toString(score), holo);
			scores[i].setFontScale(ratio);

			table.add(rank[i]).left();
			table.add(scores[i]).expand().left();
			table.row();
		}

		curButton = 0;
		buttonTimer = 0.0f;

		mainStage.addActor(table);
	}

	@Override
	public void update(float dt)
	{
		ButtonPressed(dt);
	}

	void ButtonPressed(float dt)
	{
		if(back.isPressed())
		{
			buttonTimer = 0.0f;
			curButton = 1;
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
					MyGame.setActiveScreen(new MainmenuScreen());
					break;
			}
		}
	}
}
