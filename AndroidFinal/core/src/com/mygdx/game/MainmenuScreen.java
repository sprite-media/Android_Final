package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainmenuScreen extends ScreenBeta {

    //Skin
    Skin orangeSkin;

    //Buttons
    TextButton startBtn;
    TextButton settingBtn;
    TextButton leaderboardBtn;
    TextButton quitBtn;

    //Table
    Table table;

    //Title
    Label title;

    @Override
    public void initialize() {
        //Ratio
        float ratio = fullWidth / 1080;

        orangeSkin = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));


        //Title
        title = new Label("BLOCK VS SNAKE", orangeSkin);
        title.setFontScale(5 * ratio);

        //Start Button
        startBtn = new TextButton("START", orangeSkin);
        startBtn.getLabel().setFontScale(5 *ratio);

        //Setting Button
        settingBtn = new TextButton("SETTING", orangeSkin);
        settingBtn.getLabel().setFontScale(5 *ratio);

        //Leaderboard Button
        leaderboardBtn = new TextButton("LEADERBOARD", orangeSkin);
        leaderboardBtn.getLabel().setFontScale(5*ratio);

        //Quit Button
        quitBtn = new TextButton("QUIT", orangeSkin);
        quitBtn.getLabel().setFontScale(5*ratio);

        //Table
        Table table = new Table();
        table.setSize(fullWidth, fullHeight);
        table.bottom();
        table.add(title).minWidth(fullWidth/5).minHeight(fullHeight/10).padBottom(fullHeight/5);
        table.row();
        table.add(startBtn).minWidth(fullWidth/5).minHeight(fullHeight/10).padBottom(fullHeight/50);
        table.row();
        table.add(settingBtn).minWidth(fullWidth/5).minHeight(fullHeight/10).padBottom(fullHeight/50);
        table.row();
        table.add(leaderboardBtn).minWidth(fullWidth/5).minHeight(fullHeight/10).padBottom(fullHeight/50);
        table.row();
        table.add(quitBtn).minWidth(fullWidth/5).minHeight(fullHeight/10).padBottom(fullHeight/25);
        mainStage.addActor(table);

        //BGM
        if(defaultBackgroundMusic == null)
        {
            defaultBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Audios/Bgm.mp3"));
            defaultBackgroundMusic.setVolume(volumeMultiplier);
            defaultBackgroundMusic.play();
            defaultBackgroundMusic.setLooping(true);
        }

        InitBtnListners();
    }

    @Override
    public void update(float dt) {

    }

    public void InitBtnListners()
    {
        startBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                //TODO Implement GameplayScreen
                MyGame.setActiveScreen(new MainScreen());
                return false;
            }
        });

        settingBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                MyGame.setActiveScreen(new SettingScreen());
                return false;
            }
        });


        leaderboardBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                //TODO Implement LeaderboardScreen
                MyGame.setActiveScreen(new LeaderboardScreen());
                return false;
            }
        });

        quitBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                System.exit(0);
                return false;
            }
        });
    }
}
