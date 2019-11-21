package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameoverScreen extends ScreenBeta
{
	Skin orange;
	Table uiTable;


    @Override
    public void initialize()
    {
		orange = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));
		uiTable = new Table(orange);

    }

    @Override
    public void update(float dt)
    {

    }
}
