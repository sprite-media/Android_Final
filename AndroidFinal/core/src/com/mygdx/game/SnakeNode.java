package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import javax.swing.GroupLayout;

public class SnakeNode extends ActorBeta
{
    public String imgFileName = null;
    public int fileNumber = 0;
    public boolean isHead = false;
    public boolean isEmpty = false;
    public Label lifeLabel;
    public int life = 0;
    public int killDmg = 0;
    public boolean doDestory = false;

    float frame = 0.05f;
    float curFrame = 0.0f;

    Skin skin;

    public SnakeNode(float x, float y, Stage stage)
    {
        super(x, y, stage);
        skin = new Skin(Gdx.files.internal("Skin/holo/skin/dark-hdpi/Holo-dark-hdpi.json"));
        float ratio = Gdx.graphics.getWidth() / 320;
        lifeLabel = new Label(" ", skin);
        lifeLabel.setColor(0,0,0, 1);
        lifeLabel.setFontScale(0.75f * ratio);
        lifeLabel.setAlignment(Align.center);
        lifeLabel.setPosition(x, y);

        stage.addActor(lifeLabel);
    }

    public boolean Hit(Character character)
    {
        if(character.overlaps(this) && !isEmpty)
        {
            return true;
        }
        else return false;
    }

    public boolean GetHit(int _dmg)
    {
        life -= _dmg;
        lifeLabel.setText(life);
        if(life <= 0)
        {
            life = 0;
            isEmpty = true;
            loadTexture("Alpha.png");
            lifeLabel.setText("");
            return true;
        }
        return false;
    }

    public void DestroyAtivated(float dt)
    {
        if(doDestory && life > 0)
        {
            curFrame += dt;
            if(curFrame >= frame)
            {
                curFrame = 0;
                GetHit(life);
            }
        }
    }

}
