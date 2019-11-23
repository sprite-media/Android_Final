package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Random;

public class Snake
{
    int nodeNum;
    float nodeSize;

    Table snakeTable;
    SnakeNode[] snakeNodes;

    public Snake(float x, float y, Stage stage)
    {
        float mapWidth = Gdx.graphics.getWidth();
        nodeNum = 6;
        nodeSize = mapWidth / nodeNum;

        snakeTable = new Table();
        snakeTable.setSize(mapWidth, nodeSize);
        snakeTable.setPosition(x, y);

        int tempNodeNum = nodeNum;
        Random random = new Random();

        while(tempNodeNum != 0)
        {
            int ranNum = random.nextInt(tempNodeNum) + 1;
            tempNodeNum -= ranNum;

            snakeNodes = new SnakeNode[tempNodeNum];
            for(int i = 0; i < tempNodeNum; i++)
            {
                snakeNodes

                    if((random.nextInt(100) + 1) <= 5) //img 4 (21~30)
                    {
                        snakeNodes[i].fileNumber = 4;
                    }
                    else if((random.nextInt(100) + 1) <= 10) //img 3 (16~20)
                    {

                    }
                    else if((random.nextInt(100) + 1) <= 20) //img 2 (11~15)
                    {

                    }
                    else if((random.nextInt(100) + 1) <= 25) //img 1 (6~10)
                    {

                    }
                    else //img 0 (1~5)
                    {

                    }

                    snakeNodes[i].imgFileName = ""
                }
            }
        }
    }
}
