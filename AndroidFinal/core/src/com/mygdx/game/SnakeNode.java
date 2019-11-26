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
    Character character;

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

        setBoundaryRectangle();
    }

    public boolean Hit(ActorBeta actorBeta)
    {
        if(this.overlaps(actorBeta))
        {
            return true;
        }
        else return false;
    }

}
