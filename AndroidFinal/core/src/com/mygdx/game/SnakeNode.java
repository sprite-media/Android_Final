package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SnakeNode extends ActorBeta
{
    public String imgFileName;
    public int fileNumber;
    public boolean isHead;
    public Label lifeLabel;
    public int life;

    public SnakeNode(float x, float y, Stage stage, Skin skin)
    {
        super(x, y, stage);
        imgFileName = null;
        isHead = false;
        lifeLabel = new Label("0", skin);
        int life = 0;

        setBoundaryPolygon(4);
    }
}
