package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Character extends ActorBeta
{
    //y
    public static float yPos;

    //Life
    Label lifeLabel;
    int life;

    //Effect
    HitEffect hitEffect;
    Vector2 size;

    //Death
    public boolean isDead = false;

    //Main screen;
    GameScreen gameScreen;

    //Player IdleAnims
    String[] wood;
    String[] brick;
    String[] steel;
    String[] diamond;

    Animation<TextureRegion> anim_wood;
    Animation<TextureRegion> anim_brick;
    Animation<TextureRegion> anim_steel;
    Animation<TextureRegion> anim_diamond;

    //player center
    Vector2 center;

    public Character(float x, float y, Stage stage, GameScreen _gameScreen)
    {
        super(x, y, stage);
        yPos = y;
        //Ratio
        float ratio = Gdx.graphics.getWidth() / 400.0f;

        //Main screen
        gameScreen = _gameScreen;

        //Player Animations
        SetAnim();

        //Life
        life = 20;
        Skin skin = new Skin(Gdx.files.internal("Skin/holo/skin/dark-hdpi/Holo-dark-hdpi.json"));
        lifeLabel = new Label("" + life, skin);
        lifeLabel.setFontScale(1 * ratio);
        lifeLabel.setPosition(this.getX() - lifeLabel.getWidth()/2, this.getY() - lifeLabel.getHeight()/2);

        //Player center and size
        size = new Vector2(18, 18);
        size.x = Gdx.graphics.getWidth()/size.x;
        size.y = Gdx.graphics.getWidth()/size.y;
        setSize(size.x, size.y);

        center = new Vector2(getX() + (getWidth()/2), getY() + (getHeight()/2));

        //Add Actors in the screen
        stage.addActor(lifeLabel);

        hitEffect = new HitEffect(this);

        ChangeAnim();
        //Set boundary
        setBoundaryRectangle();
    }

    public void SetAnim()
    {
        wood = new String [] {"Character/Player/Player_Wood.png"};
        anim_wood = this.loadAnimationFromFiles(wood, 1/10f, false);
        brick = new String [] {"Character/Player/Player_Brick.png"};
        anim_brick = this.loadAnimationFromFiles(brick, 1/10f, false);
        steel = new String[] {"Character/Player/Player_Steel.png"};
        anim_steel = this.loadAnimationFromFiles(steel, 1/10, false);
        diamond = new String[] {"Character/Player/Player_Diamond.png"};
        anim_diamond = this.loadAnimationFromFiles(diamond, 1/10f, false);
    }

    public Vector2 GetPlayerCenter()
    {
        center = new Vector2(getX() + (getWidth()/2), getY() + (getHeight()/2));
        return center;
    }


    public void ChangeAnim()
    {
        if(life >= 30)
        {
            setAnimation(anim_diamond);
        }
        else if(life >= 20)
        {
            setAnimation(anim_steel);
        }
        else if(life >= 10)
        {
            setAnimation(anim_brick);
        }
        else
        {
            setAnimation(anim_wood);
        }

        hitEffect.SetEffect();
        size = new Vector2(18, 18);
        size.x = Gdx.graphics.getWidth()/size.x;
        size.y = Gdx.graphics.getWidth()/size.y;
        setSize(size.x, size.y);
    }

    public void GetHit(float _dmg)
    {
        life -= _dmg;
        ChangeAnim();

        getStage().addActor(hitEffect);
        if(life <= 0)
        {
            GameOver();
            return;
        }

    }

    public void GameOver()
    {
        CheckScore();
        MyGame.setActiveScreen(new GameoverScreen());
    }

    public void CheckScore()
    {
        Preferences pref;
        pref = Gdx.app.getPreferences("MyPref");
        int score[];
        score = new int[10];

        for(int i = 0; i < score.length; i++)
        {
            score[i] = pref.getInteger(Integer.toString(i));
        }

        for(int i = 0; i < score.length; i++)
        {
            if(GameScreen.curScore > score[i])
            {
                for(int j = 9; j > i; j--)
                {
                    score[j] = score[j-1];
                }
                score[i] = GameScreen.curScore;
                break;
            }
        }

        for(int i = 0; i < score.length; i++)
        {
            pref.putInteger(Integer.toString(i), score[i]);
            pref.flush();
        }
    }

    public void GetPowerUp(int _ups)
    {
        life += _ups;
        ChangeAnim();
    }


    public void DisplayHud()
    {
        //Player life
        lifeLabel.setText("" + life);
        lifeLabel.setPosition(this.getX() - lifeLabel.getWidth()/3, this.getY() - lifeLabel.getHeight());
    }

    @Override
    public void act(float dt)
    {
        super.act(dt);

        DisplayHud();
    }

    public void Movement(float speed)
    {
        moveBy((Gdx.input.getDeltaX() *speed), 0);
        if(getX() < 0)
            setPosition(0, getY());
        else if(getRight() > Gdx.graphics.getWidth())
            setPosition(Gdx.graphics.getWidth() - getWidth(), getY());
    }
}
