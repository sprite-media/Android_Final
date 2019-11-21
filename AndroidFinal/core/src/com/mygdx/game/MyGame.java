package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class MyGame extends GameBeta {

    @Override
    public void create() {
        super.create();

       setScreen(new SplashScreen());
    }

    @Override
    public void render() {
        super.render();

    }
}
