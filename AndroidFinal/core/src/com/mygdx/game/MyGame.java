package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class MyGame extends GameBeta
{
	@Override
	public void create()
	{
		super.create();
		setActiveScreen(new GameoverScreen());
	}
}
