package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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

    Sound buttonClick;

    MovingBackground movingBackground;

    @Override
    public void initialize() {
        //Ratio
        float ratio = fullWidth / 1080;

        orangeSkin = new Skin(Gdx.files.internal("Skin/orange/skin/uiskin.json"));

        movingBackground = new MovingBackground(2, mainStage);
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
        buttonClick = Gdx.audio.newSound(Gdx.files.internal("Audios/ButtonPressed.mp3"));
        buttonClick.setVolume(0, volumeMultiplier);

        InitBtnListners();
    }

    @Override
    public void update(float dt) {
        movingBackground.BackgroundMovement(300 * dt);
    }

    public void InitBtnListners()
    {
        startBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                //TODO Implement GameplayScreen
                buttonClick.play();
                MyGame.setActiveScreen(new GameScreen());
                return false;
            }
        });

        settingBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                buttonClick.play();
                MyGame.setActiveScreen(new SettingScreen());
                return false;
            }
        });


        leaderboardBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                //TODO Implement LeaderboardScreen
                buttonClick.play();
                MyGame.setActiveScreen(new LeaderboardScreen());
                return false;
            }
        });

        quitBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                buttonClick.play();
                System.exit(0);
                return false;
            }
        });
    }
}
