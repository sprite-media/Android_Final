package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Character extends ActorBeta
{
    float frameRate = 0.05f;

    Label life;

    int attackImgNum = 9;
    String[] attackFileNames;

    Vector2 size;

    public boolean isDead = false;

    public Character(float x, float y, Stage stage)
    {
        super(x, y, stage);
        float ratio = Gdx.graphics.getWidth() / 400.0f;
        Skin skin = new Skin(Gdx.files.internal("Skin/holo/skin/dark-hdpi/Holo-dark-hdpi.json"));
        life = new Label("0", skin);
        life.setFontScale(2 * ratio);
        life.setPosition(this.getX() - life.getWidth()/2, this.getY() - life.getHeight()/2);
        stage.addActor(life);

        attackFileNames = new String[attackImgNum];
        for(int i = 0; i < attackImgNum; i++)
        {
            attackFileNames[i] = "Character/Attack/files/attack000" + (i + 1) + ".png";
        }


        size = new Vector2(7.5f, 10); //lower the number-> img size will become bigger ****
        size.x = Gdx.graphics.getWidth()/size.x;
        size.y = Gdx.graphics.getWidth()/size.y;
        setSize(size.x, size.y);

        setBoundaryPolygon(4);
    }

    public void DisplayHud()
    {
        //Player life
        life.setPosition(this.getX() - life.getWidth()/2, this.getY() - life.getHeight()/2);
    }

    public void CharacterMove()
    {
        loadAnimationFromFiles(attackFileNames, frameRate, true);
        setSize(size.x, size.y);
    }

    @Override
    public void act(float dt)
    {
        DisplayHud();
    }

}
