package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Character extends ActorBeta
{
    int walkImgNum = 8;
    String[] walkFileNames;
    float frameRate = 0.05f;

    int attackImgNum = 9;
    String[] attackFileNames;

    Vector2 size;

    public boolean isDead = false;

    public Character(float x, float y, Stage stage)
    {
        super(x, y, stage);

        walkFileNames = new String[walkImgNum];
        for(int i = 0; i < walkImgNum; i++)
        {
            walkFileNames[i] = "Character/Walk/files/Walk"+ (i + 1) +".png";
        }

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

    public void CharacterMove()
    {
        loadAnimationFromFiles(attackFileNames, frameRate, true);
        setSize(size.x, size.y);
    }

}
