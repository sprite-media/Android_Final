package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

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
    public boolean isDead = false;
    public Vector2 finalPos;

    float frame = 0.25f;
    float curFrame = 0.0f;

    Blood blood;

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
        lifeLabel.setPosition(x + getWidth()/2, y + getHeight()/2);

        finalPos = new Vector2(0,0);

        blood = new Blood(x, y, stage);

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
            isDead = true;
            blood.justKilled = true;
            finalPos.x = getX();
            finalPos.y = getY();
            lifeLabel.setText("");
            return true;
        }
        return false;
    }

    public void Reset()
    {
        lifeLabel.setText("");
        isHead = false;
        isEmpty = false;
        doDestory = false;
        imgFileName = null;
    }

    public void SetOffImg(float dt)
    {
        if(isDead)
        {
            curFrame += dt;
            if(curFrame >= frame)
            {
                curFrame = 0;
                loadTexture("Alpha.png");
                isDead=  false;
            }
        }
    }

}
