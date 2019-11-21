package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


public class SplashScreen extends ScreenBeta {
    Image spriteMediaSplashImg;
    Image libGdxSplashImg;

    //Timer
    float timer;

    @Override
    public void initialize() {
        //Timer
        timer = 0.0f;

        //Images
        spriteMediaSplashImg = new Image(new Texture(Gdx.files.internal("Textures/SpriteMedia.PNG")));
        spriteMediaSplashImg.addAction(Actions.sequence(Actions.fadeIn(2.0f), Actions.fadeOut(2.0f)));

        libGdxSplashImg = new Image(new Texture(Gdx.files.internal("Textures/libgdx.png")));
        libGdxSplashImg.addAction(Actions.sequence(Actions.fadeIn(2.0f), Actions.fadeOut(2.0f)));

        //Table
        Table table = new Table();
        table.setSize(fullWidth, fullHeight);
        table.center();
        table.add(spriteMediaSplashImg);
        table.row();
        table.add(libGdxSplashImg);
        uiStage.addActor(table);
    }

    @Override
    public void update(float dt) {
        timer += dt;

        if(timer >= 5.0f)
            MyGame.setActiveScreen(new MainmenuScreen());
    }
}
