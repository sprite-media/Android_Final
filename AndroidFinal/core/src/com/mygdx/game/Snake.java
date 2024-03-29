package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

public class Snake
{
    public static int nodeNum;
    public float nodeSize;
    public boolean isBelow;

    float curHeight;
    float spawnHeight;
    SnakeNode[] snakeNodes;

    Random random = new Random();

    float doDestroyDelay = 0.35f;
    float curDestroyDelay = 0.0f;


    //
    PowerUp powerUp;

    Character player;
    public Snake(float y, Stage stage, Character _player)
    {
        player = _player;

        float mapWidth = Gdx.graphics.getWidth();
        nodeNum = 6;
        nodeSize = mapWidth / nodeNum;
        snakeNodes = new SnakeNode[nodeNum];

        spawnHeight = y;
        curHeight = spawnHeight;
        for(int i = 0; i < nodeNum; i++)
        {
            snakeNodes[i] = new SnakeNode(i * nodeSize, spawnHeight, stage);
        }

        SetShape(stage);
    }

    public void SetShape(Stage stage)
    {
        isBelow = false;
        for(int i = 0; i < nodeNum; i++)
        {
            snakeNodes[i].Reset();
        }

        int tempNodeNum = nodeNum;
        int idx = 0;
        while(tempNodeNum != 0)
        {
            int ranNum = random.nextInt(tempNodeNum) + 1;
            tempNodeNum -= ranNum;

            for(int i = 0; i < ranNum; i++)
            {
                if(random.nextInt(10) < 2) //if( 0,1,2) 33% (draw empty)
                {
                    snakeNodes[idx].isEmpty = true;
                    snakeNodes[idx].imgFileName = "Alpha.png";
                }
                else
                {
                    snakeNodes[idx].fileNumber = GetFileNum();
                    snakeNodes[idx].life = PassBeginLife(snakeNodes[idx].fileNumber);
                    snakeNodes[idx].killDmg = (int)snakeNodes[idx].life/5;
                    snakeNodes[idx].lifeLabel.setText(snakeNodes[idx].life);
                    snakeNodes[idx].lifeLabel.setPosition(snakeNodes[idx].getX() + snakeNodes[idx].getWidth()/2,
                            snakeNodes[idx].getY() + snakeNodes[idx].getHeight()/2);
                    snakeNodes[idx].imgFileName = "Character/Snake/Body/Snake_Body_" + snakeNodes[idx].fileNumber + ".png";
                }
                idx++;
            }
        }


        boolean isEmpty = false;
        for(int i = nodeNum - 1; i >= 0 ; i--)
        {
            if(i == nodeNum -1)
            {
               if(!snakeNodes[i].isEmpty)
               {
                   snakeNodes[i].imgFileName = "Character/Snake/Head/Snake_Head_" + snakeNodes[i].fileNumber + ".png";
                   snakeNodes[i].isHead = true;
                   continue;
               }
            }
            if(snakeNodes[i].isEmpty)
            {
                isEmpty = true;
                continue;
            }

            if(isEmpty)
            {
                snakeNodes[i].imgFileName = "Character/Snake/Head/Snake_Head_" + snakeNodes[i].fileNumber + ".png";
                snakeNodes[i].isHead = true;
                isEmpty = false;
            }
        }

        for(int i = 0; i  < nodeNum; i++)
        {
            snakeNodes[i].loadTexture(snakeNodes[i].imgFileName);
            stage.addActor(snakeNodes[i]);
            snakeNodes[i].setSize(nodeSize, nodeSize);
            snakeNodes[i].setBoundaryRectangle();
        }

        powerUp = new PowerUp(stage, player, spawnHeight);
        stage.addActor(powerUp);
    }

    private int GetFileNum()
    {
        if((random.nextInt(100) + 1) <= 5) //img 4 (21~30)
        {
            return 4;
        }
        else if((random.nextInt(100) + 1) <= 10) //img 3 (16~20)
        {
            return 3;
        }
        else if((random.nextInt(100) + 1) <= 20) //img 2 (11~15)
        {
            return 2;
        }
        else if((random.nextInt(100) + 1) <= 25) //img 1 (6~10)
        {
            return 1;
        }
        else //img 0 (1~5)
        {
            return 0;
        }
    }

    private int PassBeginLife(int fileNum)
    {
        int tempLife = 0;
        switch (fileNum)
        {
            case 0:
                tempLife = random.nextInt(5) + 1;
                break;
            case 1:
                tempLife = random.nextInt(5) + 6;
                break;
            case 2:
                tempLife = random.nextInt(5) + 11;
                break;
            case 3:
                tempLife = random.nextInt(5) + 16;
                break;
            case 4:
                tempLife = random.nextInt(10) + 21;
                break;
        }
        return tempLife;
    }

    public void SnakeMovement(float speed, float y, Stage stage)
    {
        curHeight -= speed;
        for(int i = 0; i < nodeNum; i++)
        {
            snakeNodes[i].setPosition(snakeNodes[i].getX(), curHeight);
            snakeNodes[i].lifeLabel.setPosition(snakeNodes[i].getX() + snakeNodes[i].getWidth()/3,
                    snakeNodes[i].getY() + snakeNodes[i].getHeight()/4);
        }
        if(snakeNodes[0].getY() < Character.yPos)
        {
            for(int i = 0; i < nodeNum; i++)
            {
                //snakeNodes[i].isEmpty = true;
                isBelow = true;
            }
        }
        if(curHeight < -nodeSize)
        {
            curHeight = y;
            SetShape(stage);

        }
    }


    public void HitSnake(int snakeNum, int dmg)
    {
        if(snakeNodes[snakeNum].isHead && !snakeNodes[snakeNum].isEmpty)
        {
            snakeNodes[snakeNum].imgFileName = "Character/Snake/Head_hurt/Snake_Head_Hurt_" + snakeNodes[snakeNum].fileNumber + ".png";
            snakeNodes[snakeNum].loadTexture(snakeNodes[snakeNum].imgFileName);
            snakeNodes[snakeNum].setSize(nodeSize, nodeSize);
        }
        if(snakeNodes[snakeNum].GetHit(dmg))
        {
            if(snakeNodes[snakeNum].isHead)
            {
                if(snakeNum > 0)
                {
                    for(int i = snakeNum - 1; i >= 0; i--)
                    {
                        if(snakeNodes[i].isEmpty || snakeNodes[i].isHead)
                        {
                            break;
                        }
                        else
                        {
                            snakeNodes[i].doDestory = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void DestoryInDelay(float dt)
    {
        for(int i = nodeNum - 1; i >= 0; i--)
        {
            if(snakeNodes[i].doDestory && snakeNodes[i].life > 0)
            {
                curDestroyDelay += dt;
                if(curDestroyDelay >= doDestroyDelay)
                {
                    curDestroyDelay = 0;
                    snakeNodes[i].GetHit(snakeNodes[i].life);
                    if(i > 0)
                    {
                        if(!snakeNodes[i - 1].isEmpty && !snakeNodes[i-1].isHead)
                            snakeNodes[i - 1].doDestory = true;
                    }
                }
            }
        }
    }

}
