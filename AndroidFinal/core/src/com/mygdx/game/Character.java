package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Character extends ActorBeta
{
    //Life
    Label lifeLabel;
    int life;

    //Effect
    HitEffect hitEffect;
    Vector2 size;

    //Death
    public boolean isDead = false;

    //Main screen;
    MainScreen mainScreen;

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

    public Character(float x, float y, Stage stage, MainScreen _mainScreen)
    {
        super(x, y, stage);

        //Ratio
        float ratio = Gdx.graphics.getWidth() / 400.0f;

        //Main screen
        mainScreen = _mainScreen;

        //Player Animations
        SetAnim();

        //Life
        life = 1;
        Skin skin = new Skin(Gdx.files.internal("Skin/holo/skin/dark-hdpi/Holo-dark-hdpi.json"));
        lifeLabel = new Label("" + life, skin);
        lifeLabel.setFontScale(1 * ratio);
        lifeLabel.setPosition(this.getX() - lifeLabel.getWidth()/2, this.getY() - lifeLabel.getHeight()/2);

        //Player center and size
        size = new Vector2(6, 6);
        size.x = Gdx.graphics.getWidth()/size.x;
        size.y = Gdx.graphics.getWidth()/size.y;
        setSize(size.x, size.y);

        center = new Vector2(getX() + (getWidth()/2), getY() + (getHeight()/2));

        //Add Actors in the screen
        stage.addActor(lifeLabel);

        hitEffect = new HitEffect(this);

        //Set boundary
        setBoundaryPolygon(4);
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

        setAnimation(anim_diamond);
        b_animating = true;
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
    }

    public void GetHit(float _dmg)
    {
        life -= _dmg;
        ChangeAnim();

        if(life <= 0)
        {
            MyGame.setActiveScreen(new GameoverScreen());
            return;
        }
        mainScreen.curScore++;
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
        lifeLabel.setPosition(this.getX() + lifeLabel.getWidth(), this.getY() + lifeLabel.getHeight());
    }

    @Override
    public void act(float dt)
    {
        super.act(dt);

        DisplayHud();
    }

}
