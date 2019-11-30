package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingScreen extends ScreenBeta {
    //Skin
    Skin orangeSkin;

    //Volume Icon
    Image volumeIcon;

    //Buttons
    TextButton backBtn;

    //Table
    Table table;

    //Label
    Label title;

    //Slider
    Slider volumeSlider;

    Sound buttonClick;

    @Override
    public void initialize() {
        //Aspect
        float ratio = fullWidth / 1080;
        //Skin
        orangeSkin = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));

        buttonClick = Gdx.audio.newSound(Gdx.files.internal("Audios/ButtonPressed.mp3"));
        buttonClick.setVolume(0, volumeMultiplier);

        //Volume
        volumeIcon = new Image(new Texture(Gdx.files.internal("Textures/VolumeIcon.png")));
        volumeSlider = new Slider(0, 1, 0.05f, false, orangeSkin);
        volumeSlider.getStyle().background.setMinHeight(25);
        volumeSlider.getStyle().knob.setMinHeight(50);
        volumeSlider.getStyle().knob.setMinWidth(50);
        volumeSlider.setValue(volumeMultiplier);

        //Title
        title = new Label("SETTING", orangeSkin);
        title.setFontScale(5 * ratio);

        //Back Button
        backBtn = new TextButton("BACK", orangeSkin);
        backBtn.getLabel().setFontScale(4*ratio);

        //Table
        table = new Table();
        table.setSize(fullWidth, fullHeight);
        table.top();
        table.add(title).minWidth(fullWidth/2).minHeight(fullHeight/10).padBottom(fullHeight/5).padTop(fullHeight/5).padLeft(fullWidth/5).colspan(2).align(0);
        table.row();
        table.add(volumeIcon).minWidth(fullWidth/20).minHeight(fullHeight/20).padBottom(fullHeight/50).width(100);
        table.add(volumeSlider).minWidth(fullWidth/2).minHeight(fullHeight/5).padBottom(fullHeight/50);
        table.row();
        table.add(backBtn).minWidth(fullWidth/2).minHeight(fullHeight/10).padTop(fullHeight/20).colspan(2);
        table.row();

        uiStage.addActor(table);


        InitBtnListners();
    }

    @Override
    public void update(float dt) {

    }


    public void InitBtnListners()
    {
        backBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                buttonClick.play();
                MyGame.setActiveScreen(new MainmenuScreen());
                return false;
            }
        });

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClick.play();
                volumeMultiplier = volumeSlider.getValue();
                defaultBackgroundMusic.setVolume(volumeMultiplier);
            }
        });
    }
}
