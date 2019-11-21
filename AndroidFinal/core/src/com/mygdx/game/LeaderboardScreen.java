package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

	@Override
	public void initialize()
	{
		float ratio = Width / 400.0f;

		pref = Gdx.app.getPreferences("MyPref");

				// Skin
		holo = new Skin(Gdx.files.internal("Skin/holo/skin/dark-hdpi/Holo-dark-hdpi.json"));

		// Table
		table = new Table(holo);
		table.setSize(Width, Height);
		table.setFillParent(true);
		table.setPosition(HalfWidth-table.getWidth()*0.5f, HalfHeight-table.getHeight()*0.5f);

		Label title = new Label("Leaderboard", holo);
		title.setFontScale(1.4f * ratio);

		back = new TextButton("Back", holo);
		back.getLabel().setFontScale(ratio);

		table.add(back).left().top().pad(Width*0.01f).colspan(2).row();
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

		mainStage.addActor(table);
	}

	public void InitButton()
	{
		back.addListener(new EventListener()
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
